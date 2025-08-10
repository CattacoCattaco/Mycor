package io.github.cattacocattaco.mycor.misc;

public class MutableInt {
    private int value;

    public MutableInt(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void increment() {
        this.value++;
    }

    public void decrement() {
        this.value--;
    }
}
