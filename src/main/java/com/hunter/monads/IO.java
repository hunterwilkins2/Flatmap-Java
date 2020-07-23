package src.main.java.com.hunter.monads;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class IO<A> implements Monad<A> {
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

    public Monad<Void> mapToVoid(Consumer<A> f) {
        return flatmap(result -> IO.apply(() -> {
            f.accept(result);
            return null;
        }));
    }

    @Override
    public <B> Monad<B> map(Function<A, B> f) {
        return (Monad<B>) flatmap(result -> IO.apply(() -> f.apply(result)));
    }

    @Override
    public <B, C> Monad<C> liftA2(Monad<B> b, BiFunction<A, B, Monad<C>> biFunction) {
        return b.flatmap(bVal -> biFunction.apply(effect.run(), bVal));
    }

    @Override
    public <B> Monad<B> flatmap(Function<A, Monad<B>> f) {
        return (Monad<B>) IO.apply(() -> {
            IO<B> io = (IO<B>) f.apply(effect.run());
            return io.runUnsafe();
        });
    }

    @Override
    public String toString() {
        return "IO()";
    }
}