package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Sonnet {

    private static final List<String> LINES = List.of(
            "Shall I compare thee to a summer’s day?",
            "Thou art more lovely and more temperate:",
            "Rough winds do shake the darling buds of May,",
            "And summer’s lease hath all too short a date;",
            "Sometime too hot the eye of heaven shines,",
            "And often is his gold complexion dimm’d;",
            "And every fair from fair sometime declines,",
            "By chance or nature’s changing course untrimm'd;",
            "But thy eternal summer shall not fade,",
            "Nor lose possession of that fair thou ow’st;",
            "Nor shall death brag thou wander’st in his shade,",
            "When in eternal lines to time thou grow’st:",
            "   So long as men can breathe or eyes can see,",
            "   So long lives this, and this gives life to thee."
    );

    private final List<String> lines;
    private final List<Word> words;

    public Sonnet() {
        this(LINES);
    }

    Sonnet(List<String> lines) {
        this.lines = List.copyOf(lines);
        this.words = Collections.unmodifiableList(extractWords(this.lines));
    }

    public List<String> lines() {
        return lines;
    }

    public List<Word> words() {
        return words;
    }

    private static List<Word> extractWords(List<String> lines) {
        List<Word> result = new ArrayList<>();
        for (int lineIndex = 0; lineIndex < lines.size(); lineIndex++) {
            String line = lines.get(lineIndex);
            int i = 0;
            while (i < line.length()) {
                if (isWordChar(line.charAt(i))) {
                    int start = i;
                    while (i < line.length() && isWordChar(line.charAt(i))) {
                        i++;
                    }
                    result.add(new Word(lineIndex, start, line.substring(start, i)));
                } else {
                    i++;
                }
            }
        }
        return result;
    }

    private static boolean isWordChar(char c) {
        return Character.isLetter(c) || c == '\'' || c == '’';
    }

    public record Word(int lineIndex, int startColumn, String text) {
        public int endColumn() {
            return startColumn + text.length();
        }
    }
}
