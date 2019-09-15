package xyz.lius.andy.interpreter.tokenizer;

import xyz.lius.andy.expression.Context;
import xyz.lius.andy.expression.Expression;
import xyz.lius.andy.expression.Name;

public interface Token extends Expression {
    Constant EOF = new Constant();
    Constant HOF = new Constant();

    // end-of-line
    Constant EOL = new Constant();

    Constant SPACE = new Constant();

    Constant COMMA = new Constant();
    Constant SEMICOLON = new Constant();
    Constant POINT = new Constant();
    Constant COLON = new Constant() {
        @Override
        public String toString() {
            return ":";
        }
    };

    // FREE 表示括号是独立的表达式
    // e.g. (...), [1 2 3 4], def max {...}
    // 区别于 max(a, b), a[1], loop{...}
    Constant ROUND_BRACKET_LEFT_FREE = new Constant();
    Constant CURLY_BRACKET_LEFT_FREE = new Constant();
    Constant SQUARE_BRACKET_LEFT_FREE = new Constant();

    Constant ROUND_BRACKET_LEFT = new Constant();
    Constant ROUND_BRACKET_RIGHT = new Constant();

    Constant CURLY_BRACKET_LEFT = new Constant();
    Constant CURLY_BRACKET_RIGHT = new Constant();

    Constant SQUARE_BRACKET_LEFT = new Constant();
    Constant SQUARE_BRACKET_RIGHT = new Constant();

    Constant ANGLE_BRACKET = new Constant();
    Constant SLASH_LEFT = new Constant();

    Constant QUOTE_MARK_DOUBLE = new Constant();
    Constant QUOTE_MARK_SINGLE = new Constant();
    Constant BACK_QUOTE = new Constant();

    class Constant implements Token {
        @Override
        public Expression eval(Context<Name, Expression> context) {
            return this;
        }
    }
}
