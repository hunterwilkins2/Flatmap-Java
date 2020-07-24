package dev.hunterwilkins.monads;

public class Pair<A, B> {
    private final A item1;
    private final B item2;

    public Pair(A item1, B item2) {
        this.item1 = item1; 
        this.item2 = item2;
    }

    public A Item1() {
        return item1;
    }

    public B Item2() {
        return item2;
    }

    @Override
    public String toString() {
        return String.format("Pair(%s, %s)", item1, item2);
    }
}