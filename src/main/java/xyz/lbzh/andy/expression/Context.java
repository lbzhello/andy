package xyz.lbzh.andy.expression;

import java.io.Serializable;

public interface Context<K,V> extends Serializable {
    V lookup(K key);
    V bind(K key, V value);
}
