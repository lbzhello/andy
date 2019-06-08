package xyz.lius.andy.io.support;

import xyz.lius.andy.io.CharIterator;

import java.text.StringCharacterIterator;

public class StringCharIterator implements CharIterator {
    private StringCharacterIterator iterator;

    public StringCharIterator(String text) {
        iterator = new StringCharacterIterator(text);
    }

    @Override
    public boolean hasNext() {
        return current() == DONE;
    }

    @Override
    public char current() {
        return iterator.current();
    }

    @Override
    public char next() {
        return iterator.next();
    }

    @Override
    public char previous() {
        return iterator.previous();
    }
}
