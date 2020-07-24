package dev.hunterwilkins.monads;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Identity<A> {
    private final A value;

    private Identity(A value) { this.value = value; }

    public static <A> Identity<A> pure(A x) { return new Identity<>(x); }

    public <B> Identity<B> map(Function<A, B> f) {
        return Identity.pure(f.apply(value));
    }

    public <B, C> Identity<C> liftA2(Identity<B> b, BiFunction<A, B, Identity<C>> biFunction) {
        return flatmap(value -> b.flatmap(bVal -> biFunction.apply(value, bVal)));
    }

    public <B> Identity<B> flatmap(Function<A, Identity<B>> f) {
        return f.apply(value);
    }

    @Override
    public String toString() {
        return String.format("Identity(%s)", value);
    }
}