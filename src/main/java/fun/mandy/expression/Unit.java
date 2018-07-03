package fun.mandy.expression;

import java.util.Map;

/**
 * 一个执行单元,节点,上下文环境等,所有的执行体都会解析
 * 为一个unit,多个unit组成一个更复杂得unit.相当于传统语言的
 * function,class,value,namespace
 * module,file等
 * @param <K> name of expression in context
 * @param <V> value of expression in Contest
 */
public interface Unit<K,V> extends Expression, Context<K,V> {
    void setParent(Context<K, V> parent);

    Context<K, V> getParent();

    void setParameter(Map<K,V> parameter);

    void setChild(K k, V v);

    void addEvalStream(V v);

    void addBuildStream(V v);

}
