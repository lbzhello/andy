package xyz.lius.andy.expression.context;

import xyz.lius.andy.expression.Context;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractContext<K, V> implements Context<K, V> {
    private Map<K, V> container = new HashMap<>();
    private Context<K, V> parent;

    public AbstractContext() {}

    public AbstractContext(Context<K, V> parent) {
        this.parent = parent;
    }

    @Override
    public V lookup(K key) {
        V o = container.get(key);
        if (o == null && this.parent != null) {
            o = parent.lookup(key);
        }
        return o;
    }

    @Override
    public void bind(K key, V value) {
        if (!this.update(key, value)) {
            this.add(key, value);
        }
    }

    @Override
    public boolean update(K key, V value) {
        if (container.containsKey(key)) { //update key
            container.put(key, value);
            return true;
        } else if (parent != null) {
            return parent.update(key, value);
        } else {
            return false;
        }
    }

    @Override
    public V add(K key, V value) {
        return container.put(key, value);
    }

    @Override
    public boolean contains(K key) {
        return container.containsKey(key) || parent != null && parent.contains(key);
    }

    @Override
    public void setParent(Context<K, V> parent) {
        this.parent = parent;
    }

}
