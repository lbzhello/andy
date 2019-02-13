package xyz.lius.andy.io.support;

import xyz.lius.andy.io.CharIter;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class StringCharIter implements CharIter {
    private StringCharacterIterator iterator;

    public StringCharIter(String text) {
        iterator = new StringCharacterIterator(text);
    }

    private StringCharIter(StringCharacterIterator iterator) {
        this.iterator = iterator;
    }

    @Override
    public boolean hasNext() {
        return current() == DONE;
    }

    @Override
    public char first() {
        return iterator.first();
    }

    @Override
    public char last() {
        return iterator.last();
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

    @Override
    public char setIndex(int position) {
        return iterator.setIndex(position);
    }

    @Override
    public int getBeginIndex() {
        return iterator.getBeginIndex();
    }

    @Override
    public int getEndIndex() {
        return iterator.getEndIndex();
    }

    @Override
    public int getIndex() {
        return getIndex();
    }

    @Override
    public Object clone() {
        return new StringCharIter(iterator);
    }
}
