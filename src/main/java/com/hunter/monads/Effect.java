package src.main.java.com.hunter.monads;

public interface Effect<T> {
   T run(); 
}