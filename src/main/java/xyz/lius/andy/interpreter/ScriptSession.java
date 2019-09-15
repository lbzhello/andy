package xyz.lius.andy.interpreter;

import xyz.lius.andy.expression.Complex;

public interface ScriptSession {
    Complex parse() throws Exception;
}
