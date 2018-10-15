package xyz.lbzh.andy.tokenizer;

import xyz.lbzh.andy.expression.Expression;

public interface LineNumberToken extends Expression {
    default int getLineNumber() {
        return 0;
    }
}
