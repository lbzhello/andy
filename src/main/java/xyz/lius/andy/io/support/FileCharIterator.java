package xyz.lius.andy.io.support;

import xyz.lius.andy.io.CharIterator;

import java.io.*;
import java.text.StringCharacterIterator;
import java.util.Arrays;

/**
 * @see StringCharacterIterator
 */
public class FileCharIterator implements CharIterator {
    private File file;
    private StringCharacterIterator iterator;
    private BufferedReader reader;
    private char[] buf = new char[1024]; //缓冲

    public FileCharIterator() {
    }

    /**
     * generate iter from reader
     * @param fileName
     */
    public FileCharIterator(String fileName) {
        setFile(fileName);
        refresh();
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setFile(String fileName) {
        setFile(new File(fileName));
    }

    @Override
    public void refresh() {
        try {
            this.reader = new BufferedReader(new FileReader(file));
            int capacity = this.reader.read(buf);
            this.iterator = new StringCharacterIterator(String.valueOf(Arrays.copyOf(buf, capacity)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean hasNext() {
        return this.current() != DONE;
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

}
