package src.main.java.com.hunter.monads;

import java.util.function.BiFunction;
import java.util.function.Function;


public class Maybe<A> implements Monad<A> {
    private final A value;

    private Maybe(A value) { this.value = value; }

    public static <A> Maybe<A> Just(A value) {
        return new Maybe<>(value);
    }

    public static <A> Maybe<A> Nothing() {
        return new Maybe<>(null);
    }

    @Override
    public <B> Monad<B> map(Function<A, B> f) {
        return flatmap(val -> Maybe.Just(f.apply(val)));
    }

    @Override
    public <B, C> Monad<C> liftA2(Monad<B> b, BiFunction<A, B, Monad<C>> biFunction) {
        return flatmap(value -> b.flatmap(bVal -> biFunction.apply(value, bVal)));
    }

    @Override
    public <B> Monad<B> flatmap(Function<A, Monad<B>> f) {
        return value != null ? f.apply(value) : Maybe.Nothing();
    }
    
    @Override
    public String toString() {
        return value != null ? String.format("Maybe(%s)", value) : "Nothing";
    }
}