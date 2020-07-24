package dev.hunterwilkins.monads;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Maybe<A> {
    private final A value;

    private Maybe(A value) { this.value = value; }

    public static <A> Maybe<A> Just(A value) {
        return new Maybe<>(value);
    }

    public static <A> Maybe<A> Nothing() {
        return new Maybe<>(null);
    }

    public <B> Maybe<B> map(Function<A, B> f) {
        return flatmap(val -> Maybe.Just(f.apply(val)));
    }

    public <B, C> Maybe<C> liftA2(Maybe<B> b, BiFunction<A, B, Maybe<C>> biFunction) {
        return flatmap(value -> b.flatmap(bVal -> biFunction.apply(value, bVal)));
    }

    public <B> Maybe<B> flatmap(Function<A, Maybe<B>> f) {
        return value != null ? f.apply(value) : Maybe.Nothing();
    }
    
    @Override
    public String toString() {
        return value != null ? String.format("Maybe(%s)", value) : "Nothing()";
    }
}