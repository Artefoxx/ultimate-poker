package state

import model.*
import command.*

case object PreFlopState extends PhaseState:
  override def afterAction(
      game: GameState,
      command: GameCommand
  ): GameState =
    command match
      case CheckCommand =>
        game.copy(phase = GamePhase.Flop)

      case PlayCommand(_) =>
        game.copy(phase = GamePhase.Showdown)

      case _ =>
        throw new IllegalArgumentException(
          "Invalid action during PreFlop"
        )

case object FlopState extends PhaseState:
  override def afterAction(
      game: GameState,
      command: GameCommand
  ): GameState =
    command match
      case CheckCommand =>
        game.copy(phase = GamePhase.River)

      case PlayCommand(_) =>
        game.copy(phase = GamePhase.Showdown)

      case _ =>
        throw new IllegalArgumentException(
          "Invalid action during Flop"
        )

case object RiverState extends PhaseState:

  override def afterAction(
      game: GameState,
      command: GameCommand
  ): GameState =
    command match
      case PlayCommand(_) | FoldCommand =>
        game.copy(phase = GamePhase.Showdown)

      case _ =>
        throw new IllegalArgumentException(
          "Invalid action during River"
        )

case object ShowdownState extends PhaseState:

  override def afterAction(
      game: GameState,
      command: GameCommand
  ): GameState =
    throw new IllegalArgumentException(
      "The game is already finished"
    )
