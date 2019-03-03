package xyz.lius.andy.expression.core;

import xyz.lius.andy.expression.Context;
import xyz.lius.andy.expression.Expression;
import xyz.lius.andy.expression.ExpressionFactory;
import xyz.lius.andy.expression.Name;
import xyz.lius.andy.expression.ast.RoundBracketExpression;
import xyz.lius.andy.expression.runtime.NativeExpression;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FileExpression extends NativeExpression {
    @Override
    public Expression parameters(List<Expression> list) {
        this.list(list);
        return this;
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        String fileName = first().eval(context).toString();
        File f = new File(fileName);
        if (f.exists() && f.isFile()) {
            try (FileReader fileReader = new FileReader(f)) {
                char[] buf = new char[1024];
                int capacity = fileReader.read(buf);
                return ExpressionFactory.string(String.valueOf(Arrays.copyOf(buf, capacity)));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return super.eval(context);
    }
}
