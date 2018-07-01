import java.util.Map;
import java.util.HashMap;
import java.util.Random;

public class Set <T> {

    private Map <T, Integer> container;
    private ArrayList<T> arrayOfElements;
    private Random rand;

    public Set() {
        container = new HashMap<>();
        arrayOfElements = new ArrayList<>();
        rand = new Random(42);
    }

    public void insert(T value) {
        arrayOfElements.add(value);
        container.put(value, arrayOfElements.size()-1);
    }

    public void remove(T value) {
        int index = container.get(value);
        container.remove(value);
        arrayOfElements.remove(index);
    }

    public T getRandom() {
        return arrayOfElements.get(rand.nextInt(arrayOfElements.size()));
    }

} 