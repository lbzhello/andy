package xyz.lius.andy.interpreter;

import xyz.lius.andy.expression.Complex;

public interface ScriptSession {

    void setSource(Object source);

    void refresh();

    Complex parse() throws Exception;
}
