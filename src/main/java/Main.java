import java.util.Random;

import dev.hunterwilkins.monads.IO;
import dev.hunterwilkins.monads.Maybe;

// The three choices in Rock, Paper, Scissors
enum Choice {
    ROCK, PAPER, SCISSORS
}

// The different win states that are possible
enum WinState {
    COMPUTER_WIN, PLAYER_WIN, TIE;
}

public class Main {
    public static void main(String[] args) {
        RockPaperScissors(0, 0);
    }

    /*  
        A game of rock, paper, scissors vs the computer, played best two out of three
        This is an example of doing functional programming in Java using Monads
    */
    public static void RockPaperScissors(int playerScore, int computerScore) {
        // Displays the score of the user and the computer
        IO.apply(() -> String.format("Rock, Paper, Scissors! Best two out of three:\nPlayer score: %s\nComputer score:%s", playerScore, computerScore))
                .mapToVoid(System.out::println)
                .runUnsafe(); // Runs the IO

        // Stops recursively running once the player or the computer wins best two of of three
        if (playerScore == 2 || computerScore == 2)
            return;

        getUserChoice() // Gets the users Choice as an IO<Choice>
            .liftA2(    // LiftA2 takes two IOs, and applies them to a binary function
                IO.apply(() -> computerChoice()) // Creates a IO<Choice> of the computer's choice
                    .map(s -> { // Prints out the computer's choice
                        System.out.println("Computer choose " + s + "\n");
                        return s;
                    })
                // This binary function winner maps the two IO<Choice>'s to a IO<WinState>
                , (player, computer) -> IO.apply(() -> winner(player, computer)))
            .mapToVoid(winner -> { // We then print the winner of this round and recursively call RockPaperScissors with the new scores
                if (winner == WinState.COMPUTER_WIN) {
                    System.out.println("Computer won that round!\n");
                    RockPaperScissors(playerScore, computerScore + 1);
                } else if (winner == WinState.PLAYER_WIN) {
                    System.out.println("You won that round!\n");
                    RockPaperScissors(playerScore + 1, computerScore);
                } else {
                    System.out.println("Tied!\n");
                    RockPaperScissors(playerScore, computerScore);
                }
            })
            .runUnsafe(); // Finally we actually run the code
    }

    // Uses the IO monad to get the users choice
    public static IO<Choice> getUserChoice() {
        return IO.apply(() -> "Type letter of your choice (R)ock, (P)aper, (S)cissors: ") // The input to prompt the user with
                .mapToVoid(System.out::println) // Print the prompt to the screen
                .map($ -> System.console().readLine()) // Reads the users input from the screen
                .map(String::toLowerCase) // Map the users input to lower case
                .map(Main::parseChoice) // Gets the Maybe<Choice> of the users input
                // Recursively calls getUserChoice() until user inputs correctly
                .flatmap(choice -> !choice.hasValue() ? getUserChoice() : IO.apply(() -> choice.get())); 
    }

    // Parses what the user types in and returns Just(their choice) 
    // or Nothing() if they did not type in the first letter of a choice
    public static Maybe<Choice> parseChoice(String choice) {
        switch (choice) {
            case "r":
                return Maybe.Just(Choice.ROCK);
            case "p":
                return Maybe.Just(Choice.PAPER);
            case "s":
                return Maybe.Just(Choice.SCISSORS);
            default:
                return Maybe.Nothing();
        }
    }

    // Randomly chooses the choice for the computer
    public static Choice computerChoice() {
        Random rng = new Random();

        switch (rng.nextInt(3)) {
            case 0:
                return Choice.ROCK;
            case 1:
                return Choice.PAPER;
            case 2:
                return Choice.SCISSORS;
            default:
                return Choice.ROCK;
        }
    }

    // Returns the result of rock, paper, scissors
    public static WinState winner(Choice player, Choice computer) {
        if (player == Choice.ROCK && computer == Choice.PAPER) {
            return WinState.COMPUTER_WIN;
        } else if (player == Choice.ROCK && computer == Choice.SCISSORS) {
            return WinState.PLAYER_WIN;
        } else if (player == Choice.PAPER && computer == Choice.SCISSORS) {
            return WinState.COMPUTER_WIN;
        } else if (player == Choice.PAPER && computer == Choice.ROCK) {
            return WinState.PLAYER_WIN;
        } else if (player == Choice.SCISSORS && computer == Choice.ROCK) {
            return WinState.COMPUTER_WIN;
        } else if (player == Choice.SCISSORS && computer == Choice.PAPER) {
            return WinState.PLAYER_WIN;
        } else {
            return WinState.TIE;
        }
    }
}