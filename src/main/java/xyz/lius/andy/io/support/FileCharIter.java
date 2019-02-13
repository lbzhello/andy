package xyz.lius.andy.io.support;

import xyz.lius.andy.io.CharIter;

import java.io.*;
import java.text.StringCharacterIterator;
import java.util.Arrays;

/**
 * @see StringCharacterIterator
 */
public class FileCharIter implements CharIter {
    private StringCharacterIterator iterator;
    private BufferedReader reader;
    private char[] buf = new char[1024]; //缓冲

    /**
     * generate iter from reader
     * @param fileName
     */
    public FileCharIter(String fileName) {
        try {
            this.reader = new BufferedReader(new FileReader(fileName));
            int capacity = this.reader.read(buf);
            this.iterator = new StringCharacterIterator(String.valueOf(Arrays.copyOf(buf, capacity)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private FileCharIter(StringCharacterIterator iterator) {
        this.iterator = iterator;
    }

    @Override
    public boolean hasNext() {
        return this.current() != DONE;
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
        iterator.next();
        try {
            if (iterator.current() == DONE) {
                int capacity = this.reader.read(buf);
                if (capacity == -1) { //end
                    this.reader.close();
                    return DONE;
                } else {
                    iterator.setText(String.valueOf(Arrays.copyOf(buf, capacity)));
                    return this.current();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return iterator.current();
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
        return new FileCharIter((StringCharacterIterator) iterator.clone());
    }
}
