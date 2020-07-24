package dev.hunterwilkins.monads;

public interface Effect<T> {
   T run(); 
}