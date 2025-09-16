package Game2048;

public class Pair<U,V> {
    public U first;
    public V second;
    public Pair(U first, V second) {
        this.first = first;
        this.second = second;
    }
    public static <U,V> Pair<U,V> of(U first, V second) {
        return new Pair<U,V>(first, second);
    }
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}
