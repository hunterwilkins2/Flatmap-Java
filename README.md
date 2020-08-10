<!-- omit in toc -->
# Flatmap Java
A library for exploring monads in Java. 

I have added a Rock, Paper, Scissors game as a test class to demonstrate the Maybe and IO monads in action.

To compile:
```bash
make -C ./src/main/java compile
```
To run:
```bash
make -C ./src/main/java run
```

<!-- omit in toc -->
## Table of contents
- [Monads](#monads)
  - [Either](#either)
  - [Identity](#identity)
  - [IO](#io)
  - [Maybe](#maybe)
  - [Reader](#reader)
  - [State](#state)
  - [Writer](#writer)
- [Author](#author)
  - [Contact me!](#contact-me)

## Monads
A monad is an immutable data structure that wrap effects and automates composing functions. Below are the monad I have implemented for this library. 
### Either
`class Either<A, B>`

The Either monad represents a value with two posibilities, `Left<A>` or `Right<B>`. The Left value is used to hold an error and the Right value to hold a correct value. Once a Left value (an error) occurs, that Left value is passed through with map and flatmap.
### Identity
`class Identity<A>`

The Identity monad does not perform an computation; however, it can be useful in monad transformers.
### IO
`class IO<A>`

The IO monad wraps computations that produce side effects, such as printing to the console or reading from a file.

**Note** To map to `Void` type, use the mapToVoid function
### Maybe
`class Maybe<A>`

The Maybe monad is another monad that can represent a value with two possibilites. The Maybe monad is useful over the Either monad when information about the error is not needed. A correct value is represented by `Just<a>` and an error by `Nothing()`.
### Reader
`class Reader<A, B>`

The Reader monad is used to perform computations that read from a shared enviroment. The Reader monad is useful for dependency injection and global read-only values. The enviroment is of type `A` and the resulting calculation of type `B`.
### State
`class State<S, A>`

The State monad is used to chain state though a series of function calls to simulate stateful code. The state being passed through is of type `S` and the resulting computation is of type `A`. The defintion of state is: `Function<S, Pair<A, S>>`. While this looks confusing, is represents a function that takes a state (`S`) and returns a resulting computation (`A`) and the new state (`S`).
### Writer
`class Writer<A, B>`

The Writer monad produces a log of `List<A>` while composing functions that result in a computation of type `B`.
## Author
Hunter Wilkins
### Contact me!
- <https://hunterwilkins.dev>
- <hunter.wilkins2@gmail.com>
