package xyz.lbzh.andy.tokenizer;

import xyz.lbzh.andy.expression.Context;
import xyz.lbzh.andy.expression.Expression;
import xyz.lbzh.andy.expression.Name;

public enum  TokenFlag implements Token, Name {
    NIL,
    EOF, HOF,
    COMMA, SEMICOLON, POINT, COLON,
    ROUND_BRACKET_LEFT, ROUND_BRACKET_RIGHT, ROUND_BRACKET_FREE,
    CURLY_BRACKET_LEFT, CURLY_BRACKET_RIGHT, CURLY_BRACKET_FREE,
    SQUARE_BRACKET_LEFT, SQUARE_BRACKET_RIGHT, SQUARE_BRACKET_FREE,
    ANGLE_BRACKET,
    SLASH_RIGHT, SLASH_LEFT,
    QUOTE_MARK_DOUBLE, QUOTE_MARK_SINGLE,
    BACK_QUOTE;


    @Override
    public Expression eval(Context<Name, Expression> context) {
        return context.lookup(this);
    }
}
