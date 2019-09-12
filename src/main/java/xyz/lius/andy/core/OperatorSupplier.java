package xyz.lius.andy.core;

import xyz.lius.andy.expression.Operator;
import xyz.lius.andy.expression.operator.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Operator expression Supplier
 * 单例模式-枚举实现
 */
public enum  OperatorSupplier implements Function<String, Operator> {
    INSTANCE;
    private Map<String, Supplier<Operator>> operator;
    private Supplier<Operator> defaultSupplier;

    OperatorSupplier() {
        operator = new HashMap<>();
        defaultSupplier = () -> Definition.NIL;

        //---------------- unary -----------------//
        operator.put(OperatorSingleton.NIL, () -> Definition.NIL);
        operator.put(OperatorSingleton.TRUE, () -> Definition.TRUE);
        operator.put(OperatorSingleton.FALSE, () -> Definition.FALSE);

        operator.put(OperatorSingleton.LET, () -> new LetExpression());
        operator.put(OperatorSingleton.RETURN, () -> new ReturnExpression());
        operator.put(OperatorSingleton.NEW, () -> new NewExpression());
        operator.put(OperatorSingleton.IMPORT, () -> new ImportExpression());

        operator.put(OperatorSingleton.AUTO_INC, () -> Definition.NIL);
        operator.put(OperatorSingleton.AUTO_DEC, () -> Definition.NIL);
        operator.put(OperatorSingleton.NOT, () -> Definition.NIL);

        operator.put(OperatorSingleton.IF, () -> new IfExpression());
        operator.put(OperatorSingleton.FOR, () -> new ForExpression());

        //---------------- binary -----------------//
        operator.put(OperatorSingleton.COLON, () -> new ColonExpression());
        operator.put(OperatorSingleton.ARROW, () -> new ArrowExpression());
        operator.put(OperatorSingleton.ASSIGN, () -> new AssignExpression());
        operator.put(OperatorSingleton.OR, () -> new OrExpression());
        operator.put(OperatorSingleton.AND, () -> Definition.NIL);

        operator.put(OperatorSingleton.EQUAL, () -> new EqualExpression());
        operator.put(OperatorSingleton.NOT_EQUAL, () -> new NotEqualExpression());

        operator.put(OperatorSingleton.GT, () -> new GtExpression());
        operator.put(OperatorSingleton.GE, () -> new GeExpression());
        operator.put(OperatorSingleton.LT, () -> new LtExpression());
        operator.put(OperatorSingleton.LE, () -> new LeExpression());

        operator.put(OperatorSingleton.PLUS, () -> new PlusExpression());
        operator.put(OperatorSingleton.MINUS, () -> new MinusExpression());
        operator.put(OperatorSingleton.MUL, () -> new MultiplyExpression());
        operator.put(OperatorSingleton.DIV, () -> new DivideExpression());

        operator.put(OperatorSingleton.ELSE, () -> new ElseExpression());

        //---------------- other -----------------//
        operator.put(OperatorSingleton.DEFINE, () -> new DefineExpression());
        operator.put(OperatorSingleton.COLON, () -> new ColonExpression());
        operator.put(OperatorSingleton.POINT, () -> new PointExpression());
        operator.put(OperatorSingleton.PRINT, () -> new PrintExpression());
        operator.put(OperatorSingleton.FILE, () -> new FileExpression());
    }

    @Override
    public Operator apply(String name) {
        return operator.getOrDefault(name, defaultSupplier).get();
    }

}
