package xyz.lius.andy.expression.strategy;

import xyz.lius.andy.core.Definition;
import xyz.lius.andy.expression.*;
import xyz.lius.andy.expression.ast.CurlyBracketExpression;
import xyz.lius.andy.expression.ast.RoundBracketExpression;
import xyz.lius.andy.expression.context.ExpressionContext;
import xyz.lius.andy.expression.operator.PointExpression;
import xyz.lius.andy.util.Pair;

/**
 * 变量定义策略类
 */
public class LetUtils {
    /**
     * 根据赋值表达式求出上下文变量
     * @param context 上下文
     * @param assign 赋值表达式键值对 e.g. name = "lbz"
     * @param flag true 表示新建一个变量，false 表示可能已经存在变量
     * @return
     */
    public static Expression eval(Context<Name, Expression> context, Pair<Expression, Expression> assign, boolean flag) {
        Context<Name, Expression> bindContext = context;
        Name name;
        Expression value;
        if (assign.getKey() instanceof RoundBracketExpression) { //lambda (f x) = ...
            RoundBracketExpression left = (RoundBracketExpression) assign.getKey();
            if  (assign.getValue() instanceof CurlyBracketExpression) { //define a function. e.g. f(x) = { x }
                CurlyBracketExpression right = (CurlyBracketExpression) assign.getValue();
                name = left.getName();
                Complex complex = right.eval(context);
                complex.setParameters(left.getParameters());
                value = complex;
            } else { //e.g. f(x) = x + 1
                name = left.getName();
                Complex complex = ExpressionFactory.complex(new ExpressionContext(context));
                complex.setParameters(left.getParameters());
                complex.setCodes(new Expression[]{assign.getValue()});
                value = complex;
            }
        } else if (assign.getKey() instanceof PointExpression) { //e.g. (. a b) = ...
            PointExpression left = (PointExpression) assign.getKey();
            Expression parent = left.get(0).eval(context);
            if (parent instanceof Complex) {
                bindContext = ((Complex) parent).getContext();
                name = left.get(1) instanceof RoundBracketExpression ? left.get(1).eval(context).getName() : left.get(1).getName();
                value = assign.getValue().eval(context);
            } else {
                return ExpressionFactory.error(parent, "Left value should be ComplexExpression");
            }
        } else { //e.g. f = x + 1
            name = assign.getKey().getName();
            value = assign.getValue().eval(context);
        }
        if (flag) {
            // 新建一个变量, 效率更高
            bindContext.add(name, value);
        } else {
            bindContext.bind(name, value);
        }
        return Definition.NIL;
    }
    
}
