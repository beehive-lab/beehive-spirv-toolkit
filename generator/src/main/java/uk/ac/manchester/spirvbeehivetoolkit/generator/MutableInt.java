package uk.ac.manchester.spirvbeehivetoolkit.generator;

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

    public int increment() {
        return ++value;
    }
}
