package dev.hunterwilkins.monads;

import java.util.function.Function;

public interface Functor<A> {
    <B> Monad<B> map(Function<A, B> f);
}