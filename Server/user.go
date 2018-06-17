package main

type User struct {
	Email     string `json: email`
	Password  string `json: password`
	PhotoUrl  string `json: photoUrl, omitempty`
	IsTrainer bool   `json: isTrainer`
	UID       string `json: id, omitempty`
}
