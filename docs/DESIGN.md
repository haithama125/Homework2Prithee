# Prithee — Design

## Problem
When a Blackfriar's player forgets a line during a preview, they say
"Prithee" and a fellow player in the wings calls the next word from
the script. This program plays the wing role: it prints Sonnet 18 up
to a random word, replaces that word with underscores, and asks the
user to supply it. It repeats with a new random word until the user
answers three correctly or three incorrectly.

## Structure
The project uses the standard Gradle layout with three source files
and two test files:

```
src/main/java/org/example/
    Sonnet.java        # sonnet text + word index
    PritheeGame.java   # game state + guess checking
    Main.java          # console I/O and round loop
src/test/java/org/example/
    SonnetTest.java
    PritheeGameTest.java
docs/
    DESIGN.md          # this file
    HOW_TO_RUN.md      # build and run instructions
```

The split keeps I/O out of the rules so the rules can be unit-tested.
Only `Main` touches `System.in` / `System.out`; `Sonnet` and
`PritheeGame` are pure data and pure logic.

### Sonnet
`Sonnet` holds the fourteen lines of Sonnet 18 as a `List<String>` and
exposes a parallel `List<Sonnet.Word>`. Each `Word` is a record of
`(lineIndex, startColumn, text)`, which is enough for `Main` to
reprint the sonnet with a specific word blanked out. Word boundaries
treat letters plus both straight and typographic apostrophes as part
of a word, so contractions like `ow'st` and `nature's` remain single
tokens.

A package-private `Sonnet(List<String>)` constructor lets tests build
tiny sonnets and assert word counts and positions without depending
on the full poem.

### PritheeGame
`PritheeGame` owns two counters (`correct`, `incorrect`) and depends
on an injected `Random`. `pickWord()` returns a random `Sonnet.Word`;
`submit(target, guess)` compares the guess to the target, updates the
counter, and returns whether the guess was right. `isOver()` returns
true when either counter reaches `TARGET` (3).

Guess normalization lives in a static `matches` helper: trim
whitespace, fold typographic apostrophe (`'`) to straight (`'`), and
compare case-insensitively. This gives the player a fair shot when
punctuation or apostrophe style differs from the printed text.

Injecting `Random` (instead of `new`ing one internally) means tests
can seed it or construct `Sonnet.Word` values directly and drive the
game deterministically.

### Main
`Main.main` builds a `Sonnet` and `PritheeGame`, then loops until
`game.isOver()`. Each round it picks a target word, prints every full
line before the target's line, prints the target's line up to the
target and replaces the target with `_` characters, and reads a line
from stdin. It reports "Correct!" or "Error — the word was ...", then
prints the running score. When the loop ends it prints a win or lose
message.

## Extension notes
* Different poem — swap the list in `Sonnet` or pass a custom list
  through the package-private constructor.
* Different scoring — change `PritheeGame.TARGET` or extend the class
  with additional counters.
* GUI — reuse `Sonnet` and `PritheeGame` untouched; replace `Main`
  with a UI that calls the same methods.
