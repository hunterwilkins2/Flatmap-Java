package dev.hunterwilkins.monads;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Writer<A, B> {
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

    public <C> Writer<A, C> map(Function<B, C> f) {
        return Writer.of(logs, f.apply(value));
    }

    public <C, D> Writer<A, D> liftA2(Writer<A, C> c, BiFunction<B, C, Writer<A, D>> biFunction) {
        return flatmap(bVal -> c.flatmap(cVal -> biFunction.apply(bVal, cVal)));
    }

    public <C> Writer<A, C> flatmap(Function<B, Writer<A, C>> f) {
        Writer<A, C> mappedWriter = f.apply(value);
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