package src.main.java.com.hunter.monads;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Either<A, B> implements Monad<B> {
    private final A left;
    private final B right;

    private Either(A left, B right) {
        this.left = left;
        this.right = right;
    }

    public static <A, B> Either<A, B> Left(A left){
        return new Either<>(left, null);
    }

    public static <A, B> Either<A, B> Right(B right) {
        return new Either<>(null, right);
    }

    public boolean isLeft() {
        return left != null;
    }

    @Override
    public <C> Monad<C> map(Function<B, C> f) {
        return flatmap(val -> Either.Right(f.apply(val)));
    }

    @Override
    public <C, D> Monad<D> liftA2(Monad<C> c, BiFunction<B, C, Monad<D>> biFunction) {
        return isLeft() ? Either.Left(left) : c.flatmap(bVal -> biFunction.apply(right, bVal));
    }

    @Override
    public <C> Monad<C> flatmap(Function<B, Monad<C>> f) {
        return isLeft() ? Either.Left(left) : f.apply(right);
    }

    @Override
    public String toString() {
        return isLeft() ? String.format("Left(%s)", left) : String.format("Right(%s)", right);
    }    
}