package xyz.lbzh.andy.expression;

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
    boolean rebind(Name key, Expression value);

    /**
     * create a (key, value) pair in this context
     * @param key
     * @param value
     * @return
     */
    Expression newbind(Name key, Expression value);

    boolean contains(K key);

    void setParent(Context<Name, Expression> parent);
}
