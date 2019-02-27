package xyz.lius.andy.expression.runtime;

import xyz.lius.andy.core.ApplicationFactory;
import xyz.lius.andy.core.Definition;
import xyz.lius.andy.expression.*;
import xyz.lius.andy.parser.Parser;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * import file
 * e.g. import some_script.andy
 */
public class ImportExpression extends NativeExpression {
    @Override
    public Expression eval(Context<Name, Expression> context) {
        //默认从父目录里面查找
        String fileParent = context.lookup(Definition.FILE_PARENT).toString();
        String fileName = fileParent + "/" + first().toString();
        try {
            for (File file : new File(fileParent).listFiles()) {
                if (Objects.equals(fileName, file.getCanonicalPath())) {
                    //从当前目录找到文件，导入到命名空间
                    Parser<Expression> parser = ApplicationFactory.get(Parser.class);
                    Expression expression = parser.parseFile(file.getCanonicalPath());
                    String importFileName = file.getName();
                    if (file.getName().contains("")) { //e.g. remove file suffix
                        importFileName = importFileName.split("[.]")[0];
                    }
                    context.newbind(ExpressionFactory.symbol(importFileName), expression.eval(context));
                    return ExpressionType.NIL;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ExpressionType.NIL;
    }
}
