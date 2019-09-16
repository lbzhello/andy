package xyz.lius.andy.expression.operator;

import xyz.lius.andy.core.Definition;
import xyz.lius.andy.core.OperatorSingleton;
import xyz.lius.andy.expression.*;
import xyz.lius.andy.interpreter.FileScriptLoader;
import xyz.lius.andy.interpreter.ScriptLoader;
import xyz.lius.andy.util.AbstractContainer;

import java.io.File;
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
                    String importFileName = file.getName();
                    if (file.getName().contains(".")) { //e.g. remove file suffix
                        importFileName = importFileName.split("[.]")[0];
                    }
                    ScriptLoader scriptLoader = new FileScriptLoader();
                    Complex complex = scriptLoader.loadScript(fileName);
                    context.add(ExpressionFactory.symbol(importFileName), complex);
                    return complex;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Definition.NIL;
    }

    @Override
    public String toString() {
        return show(OperatorSingleton.IMPORT, super.toString());
    }
}
