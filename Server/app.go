package main

import (
	"context"
	"encoding/base64"
	"encoding/json"
	"flag"
	"fmt"
	"io"
	"log"
	"net/http"
	"os"
	"strings"
	"time"

	firebase "firebase.google.com/go"
	"firebase.google.com/go/auth"
	"github.com/gorilla/mux"
)

const (
	hostname = "localhost"
	port     = "8080"
)

func econdeUser(user User, w io.Writer) error {
	encoder := json.NewEncoder(w)

	return encoder.Encode(user)

}

func decode(reader io.Reader) User {
	var user User
	decoder := json.NewDecoder(reader)

	decoder.Decode(&user)

	return user
}

// authContext is a middleware that will check request
// if this is a loging request or register request the request will proceed
// if this is any other request then a token verification will take place
func authContext(ds *firebase.App) func(http.Handler) http.Handler {

	return func(next http.Handler) http.Handler {

		return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
			log.Println(r.Method, "-", r.RequestURI)

			if strings.Contains(r.RequestURI, "/user/register") || strings.Contains(r.RequestURI, "/user/login") {
				next.ServeHTTP(w, r)
				return
			}

			ctx := r.Context()
			client, err := ds.Auth(ctx)

			if err != nil {
				w.WriteHeader(http.StatusInternalServerError)
				return
			}

			token := r.Header.Get("Token")

			_, err = client.VerifyIDTokenAndCheckRevoked(ctx, token)

			if err != nil {
				http.Error(w, fmt.Sprintf("%v", err), http.StatusUnauthorized)
				return
			}

			next.ServeHTTP(w, r)
		})

	}

}

// createCustomToken will use the userEmail to create a token
func createCustomToken(ctx context.Context, userEmail string, client *auth.Client) (string, error) {

	user, err := client.GetUserByEmail(ctx, userEmail)

	if err != nil {
		return "", err
	}

	return client.CustomToken(ctx, user.UID)
}

// registerUser will create a user and return a token that the client will use to later authenticate
func registerUser(w http.ResponseWriter, r *http.Request, ds *firebase.App) {
	var user User
	var claims map[string]interface{}
	ctx := r.Context()

	user = decode(r.Body)

	authenticator, err := ds.Auth(ctx)

	if err != nil {
		http.Error(w, "Could not authenticate user", http.StatusInternalServerError)
		return
	}

	params := (&auth.UserToCreate{}).
		Email(user.Email).
		Password(user.Password).
		PhotoURL(user.PhotoUrl)

	u, err := authenticator.CreateUser(ctx, params)

	if err != nil {
		http.Error(w, "Unable to create user", http.StatusInternalServerError)
		return
	}

	claims = make(map[string]interface{})

	claims["isTrainer"] = false

	authenticator.SetCustomUserClaims(ctx, u.UID, claims)

	user.UID = u.UID

	customToken, err := authenticator.CustomToken(ctx, user.UID)

	if err != nil {
		http.Error(w, "Could not create authentication token", http.StatusInternalServerError)
		return
	}

	w.Write([]byte(customToken))
	w.WriteHeader(http.StatusOK)
}

// loginUser will create a custom token that the client will use in order for the client to authenticate
func loginUser(w http.ResponseWriter, r *http.Request, ds *firebase.App) {
	ctx := r.Context()
	client, err := ds.Auth(ctx)

	if err != nil {
		http.Error(w, "Could not connect to datasource", http.StatusInternalServerError)
		return
	}

	authorizationBase64 := strings.SplitN(r.Header.Get("Authorization"), " ", 2)

	if len(authorizationBase64) != 2 || authorizationBase64[0] != "Basic" {
		http.Error(w, "Authorization payload was not in the right format", http.StatusBadRequest)
		return
	}

	codedCredentials, err := base64.StdEncoding.DecodeString(authorizationBase64[1])

	if err != nil {
		w.WriteHeader(http.StatusInternalServerError)
		return
	}

	decriptedEmailPassword := strings.SplitN(string(codedCredentials[:]), ":", 2)

	if len(decriptedEmailPassword) != 2 {
		w.WriteHeader(http.StatusInternalServerError)
		return
	}

	token, err := createCustomToken(ctx, decriptedEmailPassword[0], client)

	if err != nil {
		http.Error(w, "Could not create authentication token", http.StatusInternalServerError)
		return
	}

	w.WriteHeader(http.StatusOK)
	w.Write([]byte(token))
}

// makeHandle will create handle functions
func makeHandle(fun func(http.ResponseWriter, *http.Request, *firebase.App), ds *firebase.App) func(http.ResponseWriter, *http.Request) {

	return func(w http.ResponseWriter, r *http.Request) {
		fun(w, r, ds)
	}

}

func main() {
	var duration time.Duration
	var killSignal chan os.Signal

	flag.DurationVar(&duration, "graceful-timeout", duration*15, "The duration for which the server gracefully shutsdown")
	flag.Parse()

	mx := mux.NewRouter()

	ds, err := firebase.NewApp(context.Background(), nil)

	if err != nil {
		log.Fatalf("Error connecting to datasource %v\n", err)
		os.Exit(1)
		return
	}

	// Set Middleware to authentication
	mx.Use(mux.MiddlewareFunc(authContext(ds)))

	// Set server context
	serv := http.Server{
		Addr:         hostname + ":" + port,
		WriteTimeout: time.Second * 15,
		ReadTimeout:  time.Second * 15,
		IdleTimeout:  time.Second * 60,
		Handler:      mx,
	}

	mx.HandleFunc("/user/register", makeHandle(registerUser, ds)).Methods("POST")
	mx.HandleFunc("/user/login", makeHandle(loginUser, ds)).Methods("POST")

	go func() {
		if err := serv.ListenAndServe(); err != nil {
			log.Fatalf("Error starting server. Error is as follows: %v\n", err)
		}
	}()

	// We build a chanel that will wait until a kill signal is sent by an admin
	killSignal = make(chan os.Signal, 1)

	// Wait for kill signal. After kill signal is sent, we would then start graceful shutdown of the server
	<-killSignal

	// Builds a shutdown context for the graceful shutdown of the server
	ctx, cancel := context.WithTimeout(context.Background(), duration)

	defer cancel()

	serv.Shutdown(ctx)

	os.Exit(0)

}
