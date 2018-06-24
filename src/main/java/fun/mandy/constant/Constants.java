package fun.mandy.constant;

/**
 * 词法分析器相关常量
 */
public interface Constants {
    int NIL = -1;

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
    int WITH_LEFT_BRACKET = -36; //'['

    /**
     * SPACE_* 前缀表示表达式已经结束
     */
    int SPACE_LEFT_PAREN = -37; //'('
    int SPACE_LEFT_BRACE = -38; //'{'
    int SPACE_LEFT_BRACKET = -39; //'['

    //分隔符
    int DELIMITER = -40;

}

