# Ultimate Poker

A university software engineering project implementing the foundations of **Texas Hold'em poker** in Scala 3. The project focuses on game logic, modular design, and automated testing.

> **Status:** Work in progress. The project is not yet a finished, playable poker game.

## Project overview

The goal is to build a Texas Hold'em application, starting with the card and game-state logic and extending it toward complete betting rounds and a user interface.

## Implemented foundations

- Representation of playing cards and a standard 52-card deck
- Deck shuffling
- Initial game logic and controller components
- Automated unit tests using ScalaTest

Further gameplay functionality is under development; see the source code for the current implementation.

## Tech stack

- **Language:** Scala 3.9.0
- **Build tool:** sbt
- **Testing:** ScalaTest 3.2.19

## Getting started

Requirements: a JDK compatible with Scala 3 and **sbt**.

```bash
git clone https://github.com/Artefoxx/ultimate-poker.git
cd ultimate-poker
sbt compile
sbt test
```

To explore the implementation, see `src/main/scala`; test cases are in `src/test/scala`.

## Development roadmap

- Complete the flow of a Texas Hold'em hand (pre-flop, flop, turn, river, showdown)
- Finalize betting actions and game rules
- Expand automated tests
- Add an interactive user interface

These are planned features, not claims of completed functionality.

## Contributors

A collaborative university project developed by two students. Contributions and responsibilities are documented through the Git commit history.

## License

MIT — see [LICENSE](LICENSE).