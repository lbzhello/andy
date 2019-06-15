package xyz.lius.andy.expression.operator;

import xyz.lius.andy.expression.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class FileExpression extends AbstractContainer implements Operator {
    public FileExpression() {
        super(1);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        String fileName = get(0).eval(context).toString();
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
        return ExpressionFactory.error(this, "File not found!");
    }
}
