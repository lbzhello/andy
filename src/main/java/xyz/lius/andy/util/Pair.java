package xyz.lius.andy.util;

public class Pair<K, V> {
    private K first;
    private V second;

    public Pair() {}

    public Pair(K first, V second) {
        this.first = first;
        this.second = second;
    }

    public static <K, V> Pair<K, V> of(K first, V second) {
        return new Pair<>(first, second);
    }

    public K first() {
        return first;
    }

    public V second() {
        return second;
    }

    public Pair<K, V> first(K first) {
        this.first = first;
        return this;
    }

    public Pair<K, V> second(V second) {
        this.second = second;
        return this;
    }

}