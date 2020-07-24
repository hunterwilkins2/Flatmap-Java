package dev.hunterwilkins.monads;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Writer<A, B> implements Monad<B> {
    private final List<A> logs;
    private final B value;

    private Writer(List<A> logs, B value){
        this.logs = logs;
        this.value = value;
    }

    public static <A, B> Writer<A, B> of(List<A> logs, B value) {
        return new Writer<>(logs, value);
    }

    public List<A> getLogs() {
        return logs;
    }

    public B getValue() {
        return value;
    }

    @Override
    public <C> Monad<C> map(Function<B, C> f) {
        return Writer.of(logs, f.apply(value));
    }

    @Override
    public <C, D> Monad<D> liftA2(Monad<C> c, BiFunction<B, C, Monad<D>> biFunction) {
        return flatmap(bVal -> c.flatmap(cVal -> biFunction.apply(bVal, cVal)));
    }

    @Override
    public <C> Monad<C> flatmap(Function<B, Monad<C>> f) {
        Writer<A, C> mappedWriter = (Writer<A, C>) f.apply(value);
        return Writer.of(FunctionalListHelper.append(logs, mappedWriter.getLogs()), mappedWriter.getValue());
    }

    @Override
    public String toString() {
        return String.format("Writer(%s, %s)", logs.toString(), value);
    }
} 

class FunctionalListHelper {
    public static <T> List<T> append(List<T> headList, List<T> tailList) {
        List<T> list = new ArrayList<T>();

        for(T h : headList) list.add(h);
        for(T t : tailList) list.add(t);

        return list;
    }
}