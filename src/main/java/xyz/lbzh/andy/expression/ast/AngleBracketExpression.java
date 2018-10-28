package xyz.lbzh.andy.expression.ast;

import xyz.lbzh.andy.expression.Expression;

import java.util.*;

/**
 * e.g.
 *   line
 *   line
 *   line
 *   ...
 */
public class AngleBracketExpression extends TemplateExpression {
    private List<TemplateExpression> lines = new LinkedList<>();

}
