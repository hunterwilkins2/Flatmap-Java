package src.main.java.com.hunter.monads;

import java.util.function.Function;

public interface Functor<A> {
    <B> Monad<B> map(Function<A, B> f);
}