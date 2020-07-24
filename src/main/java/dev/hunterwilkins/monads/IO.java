package dev.hunterwilkins.monads;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class IO<A> {
    private final Effect<A> effect;

    private IO(Effect<A> effect) {
        this.effect = effect;
    }

    public static <A> IO<A> apply(Effect<A> effect) {
        return new IO<>(effect);
    }

    public A runUnsafe() {
        return effect.run();
    }

    public Either<Exception, A> runSafe() {
        try {
            return Either.Right(effect.run());
        } catch (Exception ex) {
            return Either.Left(ex);
        }
    }

    public IO<Void> mapToVoid(Consumer<A> f) {
        return flatmap(result -> IO.apply(() -> {
            f.accept(result);
            return null;
        }));
    }

    public <B> IO<B> map(Function<A, B> f) {
        return flatmap(result -> IO.apply(() -> f.apply(result)));
    }

    public <B, C> IO<C> liftA2(IO<B> b, BiFunction<A, B, IO<C>> biFunction) {
        return flatmap(aVal -> b.flatmap(bVal -> biFunction.apply(aVal, bVal)));
    }

    public <B> IO<B> flatmap(Function<A, IO<B>> f) {
        return IO.apply(() -> {
            IO<B> io = f.apply(effect.run());
            return io.runUnsafe();
        });
    }

    @Override
    public String toString() {
        return "IO()";
    }
}