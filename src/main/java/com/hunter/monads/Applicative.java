package src.main.java.com.hunter.monads;

import java.util.function.BiFunction;

public interface Applicative<A> {
   <B, C> Monad<C> liftA2(Monad<B> b, BiFunction<A, B, Monad<C>> biFunction);
}