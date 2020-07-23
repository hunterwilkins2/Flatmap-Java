package src.main.java.com.hunter.monads;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Identity<A> implements Monad<A> {
    private final A value;

    private Identity(A value) { this.value = value; }

    public static <A> Identity<A> pure(A x) { return new Identity<>(x); }

    @Override
    public <B> Monad<B> map(Function<A, B> f) {
        return Identity.pure(f.apply(value));
    }

    @Override
    public <B, C> Monad<C> liftA2(Monad<B> b, BiFunction<A, B, Monad<C>> biFunction) {
        return flatmap(value -> b.flatmap(bVal -> biFunction.apply(value, bVal)));
    }

    @Override
    public <B> Monad<B> flatmap(Function<A, Monad<B>> f) {
        return f.apply(value);
    }

    @Override
    public String toString() {
        return String.format("Identity(%s)", value);
    }
}