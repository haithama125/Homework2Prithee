package org.example;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PritheeGameTest {

    private static PritheeGame newGame() {
        return new PritheeGame(new Sonnet(), new Random(0));
    }

    @Test
    void freshGameIsNotOver() {
        PritheeGame game = newGame();
        assertFalse(game.isOver());
        assertEquals(0, game.correct());
        assertEquals(0, game.incorrect());
    }

    @Test
    void submitCountsCorrectAnswers() {
        PritheeGame game = newGame();
        Sonnet.Word target = new Sonnet.Word(0, 0, "Shall");
        assertTrue(game.submit(target, "shall"));
        assertEquals(1, game.correct());
        assertEquals(0, game.incorrect());
    }

    @Test
    void submitCountsIncorrectAnswers() {
        PritheeGame game = newGame();
        Sonnet.Word target = new Sonnet.Word(0, 0, "Shall");
        assertFalse(game.submit(target, "compare"));
        assertEquals(0, game.correct());
        assertEquals(1, game.incorrect());
    }

    @Test
    void threeCorrectEndsTheGame() {
        PritheeGame game = newGame();
        Sonnet.Word target = new Sonnet.Word(0, 0, "Shall");
        game.submit(target, "Shall");
        game.submit(target, "Shall");
        assertFalse(game.isOver());
        game.submit(target, "Shall");
        assertTrue(game.isOver());
        assertEquals(3, game.correct());
    }

    @Test
    void threeIncorrectEndsTheGame() {
        PritheeGame game = newGame();
        Sonnet.Word target = new Sonnet.Word(0, 0, "Shall");
        game.submit(target, "x");
        game.submit(target, "x");
        game.submit(target, "x");
        assertTrue(game.isOver());
        assertEquals(3, game.incorrect());
    }

    @Test
    void matchesIsCaseInsensitiveAndTrims() {
        assertTrue(PritheeGame.matches("Shall", "  shall "));
    }

    @Test
    void matchesFoldsTypographicApostrophe() {
        assertTrue(PritheeGame.matches("ow’st", "ow'st"));
        assertTrue(PritheeGame.matches("ow'st", "ow’st"));
    }

    @Test
    void matchesRejectsNullAndDifferentWords() {
        assertFalse(PritheeGame.matches("Shall", null));
        assertFalse(PritheeGame.matches("Shall", "compare"));
    }

    @Test
    void pickWordReturnsAWordFromTheSonnet() {
        PritheeGame game = newGame();
        Sonnet.Word picked = game.pickWord();
        assertTrue(game.sonnet().words().contains(picked));
    }
}
