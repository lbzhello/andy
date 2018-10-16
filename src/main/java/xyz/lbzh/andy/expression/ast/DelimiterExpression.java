package xyz.lbzh.andy.expression.ast;

public class DelimiterExpression extends SymbolExpression{
    public DelimiterExpression(String value) {
        super(value);
    }

    public DelimiterExpression(String value, int lineNumber) {
        super(value, lineNumber);
    }
}
