package xyz.lius.andy.core;

public interface OperatorDefinition {
    /**
     * if it is an unary operator
     * @param name
     * @return
     */
    boolean isUnary(String name);

    /**
     * if it is a binary operator
     * @param name
     * @return
     */
    boolean isBinary(String name);

    /**
     * Comparing Operator Priority
     * @param op1
     * @param op2
     * @return
     * if op1 = op2 return = 0
     * if op1 > op2 return > 0
     * if op1 < op2 return < 0
     */
    int compare(String op1, String op2);

    /**
     * Get the number of operator operands
     * @param op
     * @return
     */
    int getOperands(String op);
}
