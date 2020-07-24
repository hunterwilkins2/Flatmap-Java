package dev.hunterwilkins.monads;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Either<A, B> {
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

    public <C> Either<A, C> map(Function<B, C> f) {
        return flatmap(val -> Either.Right(f.apply(val)));
    }

    public <C, D> Either<A, D> liftA2(Either<A, C> c, BiFunction<B, C, Either<A, D>> biFunction) {
        return flatmap(right -> c.flatmap(bVal -> biFunction.apply(right, bVal)));
    }

    public <C> Either<A, C> flatmap(Function<B, Either<A, C>> f) {
        return isLeft() ? Either.Left(left) : f.apply(right);
    }

    @Override
    public String toString() {
        return isLeft() ? String.format("Left(%s)", left) : String.format("Right(%s)", right);
    }    
}