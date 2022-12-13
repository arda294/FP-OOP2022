package Util;

public abstract class Pair<T> {
    private T key;
    private T value;

    public void setKey(T key) {
        this.key = key;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getKey() {
        return key;
    }

    public T getValue() {
        return value;
    }
}
