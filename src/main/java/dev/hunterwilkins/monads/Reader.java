package dev.hunterwilkins.monads;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Reader<A, B> {
    private final Function<A, B> runner;

    private Reader(Function<A, B> runner) { this.runner = runner; }

    public static <A, B> Reader<A, B> of(Function<A, B> runner) {
        return new Reader<>(runner);
    }

    public static <A, B> Reader<A, B> pure(B b) {
        return new Reader<>(env -> b);
    }

    public B run(A env) {
        return runner.apply(env);
    }

    public <C> Reader<A, C> map(Function<B, C> f) {
        return Reader.of(env -> f.apply(run(env)));
    }

    public <C, D> Reader<A, D> liftA2(Reader<A, C> c, BiFunction<B, C, Reader<A, D>> biFunction) {
        return flatmap(bVal -> c.flatmap(cVal -> biFunction.apply(bVal, cVal)));
    }

    public <C> Reader<A, C> flatmap(Function<B, Reader<A, C>> f) {
        return Reader.of(env -> {
            Reader<A, C> reader = f.apply(run(env));
            return reader.run(env);
        });
    }

    @Override
    public String toString() {
        return "Reader()";
    }
}