package fun.mandy.context;

public interface Context<K,V> {
    V lookup(K key);
    V bind(K key,V value);
}
