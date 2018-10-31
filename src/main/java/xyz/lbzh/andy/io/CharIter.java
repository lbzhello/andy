package xyz.lbzh.andy.io;

import xyz.lbzh.andy.util.Iter;

import java.text.StringCharacterIterator;

/**
 * @see StringCharacterIterator
 */
public class CharIter implements Iter<char> {
    private StringCharacterIterator iterator;


    public CharIter(String text) {
        this.iterator = new StringCharacterIterator(text);
    }

    private CharIter(StringCharacterIterator iterator) {
        this.iterator = iterator;
    }

    /**
     * reuse current iterator
     * @param text
     * @return
     */
    @Override
    public Iter<char> from(String text) {
        iterator.setText(text);
        return this;
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
        return iterator.getIndex();
    }

    @Override
    public Object clone() {
        return new CharIter((StringCharacterIterator) iterator.clone());
    }
}
