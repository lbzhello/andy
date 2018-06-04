package fun.mandy.constant;

/**
 * 词法分析器相关常量
 */
public interface Constants {
    int NUMBER = -22;
    int STRING = -23;
    int SYMBOL = -24;

    int WHITESPACE = -33;

    /**
     * WITH_* 前缀表示表达式未结束，应继续解析
     * e.g. name(...){...} 表示一个表达式
     * e.g. name () {...} 表示三个表达式
     */
    int WITH_LEFT_PAREN = -34; //'('
    int WITH_LEFT_BRACE =  -35; //'{'
    int WITH_LEFT_BRACKET = -35; //'['

}

