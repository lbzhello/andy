package fun.mandy.expression.support;

import fun.mandy.tokenizer.support.Delimiter;

public class DelimiterExpression extends ObjectExpression {
    public DelimiterExpression(){}

    public DelimiterExpression(Object value) {
        this.value = value;
    }
}
