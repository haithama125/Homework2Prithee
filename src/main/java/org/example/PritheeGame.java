package org.example;

import java.util.List;
import java.util.Random;

public final class PritheeGame {

    public static final int TARGET = 3;

    private final Sonnet sonnet;
    private final Random random;

    private int correct = 0;
    private int incorrect = 0;

    public PritheeGame(Sonnet sonnet, Random random) {
        this.sonnet = sonnet;
        this.random = random;
    }

    public Sonnet sonnet() {
        return sonnet;
    }

    public int correct() {
        return correct;
    }

    public int incorrect() {
        return incorrect;
    }

    public boolean isOver() {
        return correct >= TARGET || incorrect >= TARGET;
    }

    public Sonnet.Word pickWord() {
        List<Sonnet.Word> words = sonnet.words();
        return words.get(random.nextInt(words.size()));
    }

    public boolean submit(Sonnet.Word target, String guess) {
        boolean isMatch = matches(target.text(), guess);
        if (isMatch) {
            correct++;
        } else {
            incorrect++;
        }
        return isMatch;
    }

    static boolean matches(String expected, String guess) {
        if (guess == null) {
            return false;
        }
        return normalize(expected).equalsIgnoreCase(normalize(guess));
    }

    private static String normalize(String raw) {
        return raw.trim().replace('’', '\'');
    }
}
