package dev.hunterwilkins.monads;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Reader<A, B> implements Monad<B> {
    private final Function<A, B> runner;

    private Reader(Function<A, B> runner) { this.runner = runner; }

    public static <A, B> Reader<A, B> of(Function<A, B> runner) {
        return new Reader<>(runner);
    }

    public B run(A env) {
        return runner.apply(env);
    }

    @Override
    public <C> Monad<C> map(Function<B, C> f) {
        return Reader.of(env -> f.apply(run((A) env)));
    }

    @Override
    public <C, D> Monad<D> liftA2(Monad<C> c, BiFunction<B, C, Monad<D>> biFunction) {
        return flatmap(bVal -> c.flatmap(cVal -> biFunction.apply(bVal, cVal)));
    }

    @Override
    public <C> Monad<C> flatmap(Function<B, Monad<C>> f) {
        return Reader.of(env -> {
            Reader<A, C> reader = (Reader<A, C>) f.apply(run((A) env));
            return reader.run((A) env);
        });
    }

    @Override
    public String toString() {
        return "Reader()";
    }
}