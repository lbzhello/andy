package xyz.lbzh.andy.tokenizer;

public interface Token {
    default String token() {
        return "";
    }
}
