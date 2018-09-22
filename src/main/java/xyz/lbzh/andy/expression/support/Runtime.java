package xyz.lbzh.andy.expression.support;

import xyz.lbzh.andy.expression.Expression;

import java.util.HashSet;
import java.util.Set;

/**
 * Runtime
 */
public class Runtime {
    private static Set<String> operatorSet = new HashSet<>();
    static {
        operatorSet.add("println");
    }
    public Expression eval(){
        return null;
    }

    public static boolean isOperator(String name){
        return operatorSet.contains(name);
    };
}
