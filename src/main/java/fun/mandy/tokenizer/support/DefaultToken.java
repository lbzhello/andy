package fun.mandy.tokenizer.support;

import fun.mandy.tokenizer.Token;

public class DefaultToken implements Token<Integer,String> {
    private Integer type;
    private String value;

    public DefaultToken(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
    @Override
    public Integer type() {
        return type;
    }

    @Override
    public String value() {
        return value;
    }
}
