package xyz.lius.andy.expression;

public abstract class AbstructAddable implements Addable<Expression> {
    private static final Expression[] EMPTY_ELEMENT_DATA = {};

    private static final int DEFAULT_INITIAL_CAPACITY = 8;

    //元素总数
    int count;

    //剩余空间
    int free;

    private Expression[] elementData;

    public AbstructAddable() {
        count = 0;
        free = 0;
        elementData = EMPTY_ELEMENT_DATA;
    }

    @Override
    public Addable add(Expression element) {
        if (free == 0) {
            Expression[] oldElementData = elementData;
            int len = count == 0 ? 8 : count * 2;
            elementData = new Expression[len];
            System.arraycopy(oldElementData, 0, elementData, 0, count);
            free = len - count;
        }
        elementData[count] = element;
        count++; free--;
        return this;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < count; i++) {
            sb.append(elementData[i] + " ");
        }
        sb.replace(sb.length()-1, sb.length(), "");
        return sb.toString();
    }

    public Expression get(int i) {
        return i < count ? elementData[i] : ExpressionType.NIL;
    }
}
