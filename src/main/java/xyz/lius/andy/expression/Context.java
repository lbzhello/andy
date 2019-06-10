package xyz.lius.andy.expression;

import java.io.Serializable;

public interface Context<K,V> extends Serializable {
    V lookup(K key);

    /**
     * bind a (key, value) pair in the whole context
     * @param key
     * @param value
     * @return
     */
    void bind(K key, V value);

    /**
     * bind a (key, value) pair if exist
     * @param key
     * @param value
     * @return
     */
    boolean update(K key, V value);

    /**
     * add a (key, value) pair in this context
     * @param key
     * @param value
     * @return
     */
    default V add(K key, V value) {
        bind(key, value);
        return null;
    }

    boolean contains(K key);

    void setParent(Context<K, V> parent);
}
