package xyz.lius.andy.interpreter;

import xyz.lius.andy.expression.Complex;

/**
 * 脚本加载器
 */
public interface ScriptLoader {

    /**
     * 加载源文件
     * @return
     * @throws Exception
     */
    Complex loadScript(String path) throws Exception;

    /**
     * 定义一个表达式
     * @param name 表达式名字
     * @param chars 表达式字符流
     * @return
     * @throws Exception
     */
    default Complex defineScript(String name, char[] chars) throws Exception {
        throw new Exception("Operation Not Support!");
    }
}
