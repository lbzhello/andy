package xyz.lius.andy.expression.ast;

import xyz.lius.andy.expression.Expression;
import xyz.lius.andy.expression.ExpressionFactory;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class StringExpression extends TokenExpression {
    public StringExpression(String value){
        super(value);
    }

    public StringExpression(String value, int lineNumber) {
        super(value, lineNumber);
    }

//    @Override
//    public String toString() {
//        return "\"" + this.value.toString() + "\"";
//    }

    /************func****************/

    public Expression toArray() {
        BracketExpression squareBracket = ExpressionFactory.squareBracket();
        CharacterIterator iterator = new StringCharacterIterator(value.toString());
        while (iterator.current() != CharacterIterator.DONE) {
            squareBracket.add(ExpressionFactory.string(String.valueOf(iterator.current())));
            iterator.next();
        }
        return squareBracket;
    }

    public Expression split() {
        BracketExpression squareBracket = ExpressionFactory.squareBracket();
        String[] strArr = value.toString().split(" ");
        for (String element : strArr) {
            if (!element.isEmpty()) {
                squareBracket.add(ExpressionFactory.string(element));
            }
        }
        return squareBracket;
    }

}
