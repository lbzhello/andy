package xyz.lius.andy.expression.operator;

import xyz.lius.andy.compiler.Compiler;
import xyz.lius.andy.compiler.parser.Parser;
import xyz.lius.andy.core.Definition;
import xyz.lius.andy.core.OperatorSingleton;
import xyz.lius.andy.expression.*;
import xyz.lius.andy.util.AbstractContainer;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * import file
 * e.g. import some_script.andy
 */
public class ImportExpression extends AbstractContainer implements Operator {
    public ImportExpression() {
        super(1);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        //默认从父目录里面查找
        String fileParent = context.lookup(Definition.FILE_DIRECTORY).toString();
        String fileName = fileParent + File.separator + get(0).toString() + ".andy";
        try {
            for (File file : new File(fileParent).listFiles()) {
                if (Objects.equals(fileName, file.getCanonicalPath())) {
                    //从当前目录找到文件，导入到命名空间
                    Parser<Expression> parser = Compiler.parser();
                    Expression expression = parser.parseFile(file.getCanonicalPath());
                    String importFileName = file.getName();
                    if (file.getName().contains(".")) { //e.g. remove file suffix
                        importFileName = importFileName.split("[.]")[0];
                    }
                    Expression value = expression.eval(context);
                    context.add(ExpressionFactory.symbol(importFileName), value);
                    return value;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Definition.NIL;
    }

    @Override
    public String toString() {
        return show(OperatorSingleton.IMPORT, super.toString());
    }
}
