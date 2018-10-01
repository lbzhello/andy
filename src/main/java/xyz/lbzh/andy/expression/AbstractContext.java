package xyz.lbzh.andy.expression;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractContext<K, V> implements Context<K, V> {
    private Map<K, V> container = new HashMap<>();
    private Context<K, V> parent = null;

    public AbstractContext(Context<K, V> parent) {
        this.parent = parent;
    }

    @Override
    public V lookup(K key) {
        V o = container.getOrDefault(key, null);
        if (o == null && this.parent != null) {
            o = parent.lookup(key);
        }
        return o;
    }

    @Override
    public AbstractContext<K, V> bind(K key, V value) {
        this.container.put(key, value);
        return this;
    }
}
