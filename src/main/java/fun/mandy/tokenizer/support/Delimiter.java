package fun.mandy.tokenizer.support;

import java.util.HashSet;
import java.util.Set;

public final class Delimiter {
    private Delimiter(){}
    private static final Set<Character> delimiters = new HashSet<>();
    static {
        delimiters.add('.');
        delimiters.add(':');
        delimiters.add('(');
        delimiters.add(')');
        delimiters.add('{');
        delimiters.add('}');
        delimiters.add('"');
        delimiters.add('/');
        delimiters.add('\'');
        delimiters.add('\\');
    }

    public static final boolean isDelimiter(Character c){
        return delimiters.contains(c);
    }

    public static final void addDelimiter(Character c){
        delimiters.add(c);
    }

    public static final void removeDelimiter(Character c){
        delimiters.remove(c);
    }
}
