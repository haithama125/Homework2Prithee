package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws IOException {
        Sonnet sonnet = new Sonnet();
        PritheeGame game = new PritheeGame(sonnet, new Random());
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Prithee — Blackfriar's rehearsal aide");
        System.out.println("Fill in the missing word. First to " + PritheeGame.TARGET
                + " correct wins; " + PritheeGame.TARGET + " wrong ends the show.");
        System.out.println();

        while (!game.isOver()) {
            Sonnet.Word target = game.pickWord();
            printSonnetUpTo(sonnet, target);
            System.out.print("Prithee, the next word? ");
            String guess = in.readLine();
            if (guess == null) {
                System.out.println();
                System.out.println("(no input — ending)");
                return;
            }
            boolean isCorrect = game.submit(target, guess);
            System.out.println(isCorrect
                    ? "Correct!"
                    : "Error — the word was \"" + target.text() + "\".");
            System.out.println("Score: " + game.correct() + " correct, "
                    + game.incorrect() + " incorrect.");
            System.out.println();
        }

        if (game.correct() >= PritheeGame.TARGET) {
            System.out.println("Bravo! Three correct — the show goes on.");
        } else {
            System.out.println("Alas — three misses. Back to rehearsal.");
        }
    }

    static void printSonnetUpTo(Sonnet sonnet, Sonnet.Word target) {
        var lines = sonnet.lines();
        for (int i = 0; i < target.lineIndex(); i++) {
            System.out.println(lines.get(i));
        }
        String currentLine = lines.get(target.lineIndex());
        String prefix = currentLine.substring(0, target.startColumn());
        System.out.println(prefix + "_".repeat(target.text().length()));
    }
}

