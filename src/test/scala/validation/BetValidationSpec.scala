package validation

import model.*
import command.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class BetValidationSpec extends AnyWordSpec with Matchers:

  private val validator = BetValidationChain.validator

  private def gameAt(phase: GamePhase): GameState =
    Game.newGame().copy(phase = phase)

  "BetValidationChain" should:

    "accept valid actions during PreFlop" in:
      val game = gameAt(GamePhase.PreFlop)

      validator.validate(game, CheckCommand) shouldBe Right(())
      validator.validate(game, PlayCommand(3)) shouldBe Right(())
      validator.validate(game, PlayCommand(4)) shouldBe Right(())

    "accept valid actions during Flop" in:
      val game = gameAt(GamePhase.Flop)

      validator.validate(game, CheckCommand) shouldBe Right(())
      validator.validate(game, PlayCommand(2)) shouldBe Right(())

    "accept valid actions during River" in:
      val game = gameAt(GamePhase.River)

      validator.validate(game, PlayCommand(1)) shouldBe Right(())
      validator.validate(game, FoldCommand) shouldBe Right(())

    "reject invalid actions during PreFlop" in:
      val game = gameAt(GamePhase.PreFlop)

      validator.validate(game, PlayCommand(1)) shouldBe
        Left("Action is not allowed in this phase")

      validator.validate(game, PlayCommand(2)) shouldBe
        Left("Action is not allowed in this phase")

      validator.validate(game, FoldCommand) shouldBe
        Left("Action is not allowed in this phase")

    "reject invalid actions during Flop" in:
      val game = gameAt(GamePhase.Flop)

      validator.validate(game, PlayCommand(1)) shouldBe
        Left("Action is not allowed in this phase")

      validator.validate(game, PlayCommand(3)) shouldBe
        Left("Action is not allowed in this phase")

      validator.validate(game, PlayCommand(4)) shouldBe
        Left("Action is not allowed in this phase")

      validator.validate(game, FoldCommand) shouldBe
        Left("Action is not allowed in this phase")

    "reject invalid actions during River" in:
      val game = gameAt(GamePhase.River)

      validator.validate(game, CheckCommand) shouldBe
        Left("Action is not allowed in this phase")

      validator.validate(game, PlayCommand(2)) shouldBe
        Left("Action is not allowed in this phase")

      validator.validate(game, PlayCommand(3)) shouldBe
        Left("Action is not allowed in this phase")

      validator.validate(game, PlayCommand(4)) shouldBe
        Left("Action is not allowed in this phase")

    "reject actions after Showdown" in:
      val game = gameAt(GamePhase.Showdown)

      validator.validate(game, CheckCommand) shouldBe
        Left("Game is already finished")

      validator.validate(game, PlayCommand(1)) shouldBe
        Left("Game is already finished")

      validator.validate(game, FoldCommand) shouldBe
        Left("Game is already finished")

    "reject actions after folding" in:
      val game = gameAt(GamePhase.River).copy(
        betting = BettingState.initial.copy(folded = true)
      )

      validator.validate(game, PlayCommand(1)) shouldBe
        Left("Player has already folded")

    "reject another action after placing a Play bet" in:
      val game = gameAt(GamePhase.PreFlop).copy(
        betting = BettingState.initial.copy(playBet = 30)
      )

      validator.validate(game, CheckCommand) shouldBe
        Left("Play bet has already been placed")

    "reject a bet when the player has insufficient balance" in:
      val game = gameAt(GamePhase.PreFlop).copy(
        betting = BettingState.initial.copy(playerBalance = 20)
      )

      validator.validate(game, PlayCommand(4)) shouldBe
        Left("Not enough chips")

    "accept a bet when the balance is exactly sufficient" in:
      val game = gameAt(GamePhase.PreFlop).copy(
        betting = BettingState.initial.copy(playerBalance = 40)
      )

      validator.validate(game, PlayCommand(4)) shouldBe Right(())

    "reject a non-positive bet amount" in:
      val game = gameAt(GamePhase.PreFlop).copy(
        betting = BettingState.initial.copy(ante = 0)
      )

      validator.validate(game, PlayCommand(3)) shouldBe
        Left("Invalid bet amount")

    "stop validation at the first error" in:
      val game = gameAt(GamePhase.Showdown).copy(
        betting = BettingState.initial.copy(folded = true, playerBalance = 0)
      )

      validator.validate(game, PlayCommand(4)) shouldBe
        Left("Player has already folded")

    "reject a negative bet amount" in:
      val game = gameAt(GamePhase.PreFlop).copy(
        betting = BettingState.initial.copy(ante = -10)
      )

      validator.validate(game, PlayCommand(3)) shouldBe
        Left("Invalid bet amount")
