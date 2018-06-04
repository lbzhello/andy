package fun.mandy.exception;


public abstract class Exceptions{
    private static class BaseException extends Exception {
        protected String message = "";
        protected BaseException(){}
        protected BaseException(String message){
            this.message = message;
        }
    }
    public static final class ParserException extends BaseException {}
    public static final class ExpressionNotSurpportException extends BaseException {}
    public static final class NumberFormatException extends BaseException {
        public NumberFormatException(String message) {
            super(message);
        }
    }
}
