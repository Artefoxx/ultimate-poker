package validation

import model.*
import command.*

class StatusValidator(next: Option[BetValidator]) extends BetValidator(next):
  override protected def check(
      game: GameState,
      command: GameCommand
  ): Either[String, Unit] =
    if game.betting.folded then Left("Player has already folded")
    else if game.betting.playBet > 0 then
      Left("Play bet has already been placed")
    else if game.phase == GamePhase.Showdown then
      Left("Game is already finished")
    else Right(())

class ActionValidator(next: Option[BetValidator]) extends BetValidator(next):
  override protected def check(
      game: GameState,
      command: GameCommand
  ): Either[String, Unit] =

    (game.phase, command) match
      case (GamePhase.PreFlop, CheckCommand) =>
        Right(())

      case (GamePhase.PreFlop, PlayCommand(3)) =>
        Right(())

      case (GamePhase.PreFlop, PlayCommand(4)) =>
        Right(())

      case (GamePhase.Flop, CheckCommand) =>
        Right(())

      case (GamePhase.Flop, PlayCommand(2)) =>
        Right(())

      case (GamePhase.River, PlayCommand(1)) =>
        Right(())

      case (GamePhase.River, FoldCommand) =>
        Right(())
      case _ =>
        Left("Action is not allowed in this phase")

class BalanceValidator(next: Option[BetValidator]) extends BetValidator(next):
  override protected def check(
      game: GameState,
      command: GameCommand
  ): Either[String, Unit] =

    command match
      case PlayCommand(multiplier) =>
        val amount = game.betting.ante.toLong * multiplier

        if amount <= 0 then Left("Invalid bet amount")
        else if amount > game.betting.playerBalance then
          Left("Not enough chips")
        else Right(())

      case _ =>
        Right(())
