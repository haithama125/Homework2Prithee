# How to run Prithee

## Prerequisites
* JDK 26 (the build declares `languageVersion = 26`; Gradle will
  download a matching toolchain if one is not already installed).
* The Gradle wrapper checked in with this project — no separate
  Gradle install is required.

## Run the game
From the project root:

```
./gradlew run
```

On Windows:

```
gradlew.bat run
```

The program prints the sonnet with a random word blanked out and asks
`Prithee, the next word?`. Type the missing word and press Enter. The
program reports Correct or Error, then restarts with a different
word. The show ends after three correct answers (win) or three
incorrect answers (loss).

## Run the tests

```
./gradlew test
```

An HTML report is written to `build/reports/tests/test/index.html`.

## Build a runnable distribution
```
./gradlew installDist
build/install/Homework2Prithee/bin/Homework2Prithee
```

## Project layout
```
build.gradle.kts         # Gradle build (Java 26 toolchain, JUnit 5)
settings.gradle.kts
src/main/java/org/example/
    Main.java            # entry point + console loop
    Sonnet.java          # sonnet text + word extraction
    PritheeGame.java     # game state and rules
src/test/java/org/example/
    SonnetTest.java
    PritheeGameTest.java
docs/
    DESIGN.md
    HOW_TO_RUN.md
```
