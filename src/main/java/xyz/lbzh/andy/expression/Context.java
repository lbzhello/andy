package xyz.lbzh.andy.expression;

import java.io.Serializable;

public interface Context<K,V> extends Serializable {
    V lookup(K key);

    Context<K, V> bind(K key, V value);

    Context<K, V> parent();

    Context<K, V> parent(Context<K, V> parent);
}
