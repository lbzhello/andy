package xyz.lbzh.andy.expression.ast;

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

}
