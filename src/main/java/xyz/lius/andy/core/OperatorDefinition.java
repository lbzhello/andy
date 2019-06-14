package xyz.lius.andy.core;

public interface OperatorDefinition {
    boolean isUnary(String name);

    boolean isBinary(String name);

    int compare(String op1, String op2);

    int getOperands(String op);
}
