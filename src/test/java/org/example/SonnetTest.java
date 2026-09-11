package org.example;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SonnetTest {

    @Test
    void defaultSonnetHasFourteenLines() {
        assertEquals(14, new Sonnet().lines().size());
    }

    @Test
    void extractsExpectedWordCount() {
        Sonnet twoLines = new Sonnet(List.of(
                "Rough winds do shake the darling buds of May,",
                "And summer’s lease hath all too short a date;"
        ));
        assertEquals(18, twoLines.words().size());
    }

    @Test
    void keepsApostropheAsPartOfWord() {
        Sonnet sonnet = new Sonnet(List.of("Nor lose possession of that fair thou ow’st;"));
        List<String> texts = sonnet.words().stream().map(Sonnet.Word::text).toList();
        assertTrue(texts.contains("ow’st"), "typographic apostrophe should stay in word");
    }

    @Test
    void wordPositionsPointBackAtSource() {
        Sonnet sonnet = new Sonnet(List.of("Rough winds do shake"));
        Sonnet.Word winds = sonnet.words().get(1);
        assertEquals("winds", winds.text());
        assertEquals(0, winds.lineIndex());
        assertEquals(6, winds.startColumn());
        assertEquals(11, winds.endColumn());
    }

    @Test
    void punctuationIsNotAWord() {
        Sonnet sonnet = new Sonnet(List.of("hello, world!"));
        List<String> texts = sonnet.words().stream().map(Sonnet.Word::text).toList();
        assertEquals(List.of("hello", "world"), texts);
        assertFalse(texts.contains(","));
    }
}
