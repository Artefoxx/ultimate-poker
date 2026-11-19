package state

import model.*
import command.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class PhaseStateSpec extends AnyWordSpec with Matchers:

  "PhaseState" should:

    "return the correct state for each game phase" in:
      PhaseState.from(GamePhase.PreFlop) shouldBe PreFlopState
      PhaseState.from(GamePhase.Flop) shouldBe FlopState
      PhaseState.from(GamePhase.River) shouldBe RiverState
      PhaseState.from(GamePhase.Showdown) shouldBe ShowdownState

    "advance from PreFlop to Flop after Check" in:
      val game = Game.newGame()

      val result = PreFlopState.afterAction(game, CheckCommand)

      result.phase shouldBe GamePhase.Flop
      game.phase shouldBe GamePhase.PreFlop

    "advance from Flop to River after Check" in:
      val game = Game.newGame().copy(phase = GamePhase.Flop)

      val result = FlopState.afterAction(game, CheckCommand)

      result.phase shouldBe GamePhase.River

    "advance from PreFlop to Showdown after Play" in:
      val game = Game.newGame()

      for multiplier <- Seq(3, 4) do
        val result = PreFlopState.afterAction(game, PlayCommand(multiplier))
        result.phase shouldBe GamePhase.Showdown

    "advance from Flop to Showdown after Play" in:
      val game = Game.newGame().copy(phase = GamePhase.Flop)

      val result = FlopState.afterAction(game, PlayCommand(2))

      result.phase shouldBe GamePhase.Showdown

    "advance from River to Showdown after Play" in:
      val game = Game.newGame().copy(phase = GamePhase.River)

      val result = RiverState.afterAction(game, PlayCommand(1))

      result.phase shouldBe GamePhase.Showdown

    "advance from River to Showdown after Fold" in:
      val game = Game.newGame().copy(phase = GamePhase.River)

      val result = RiverState.afterAction(game, FoldCommand)

      result.phase shouldBe GamePhase.Showdown

    "reject Fold during PreFlop and Flop" in:
      val preFlop = Game.newGame()
      val flop    = preFlop.copy(phase = GamePhase.Flop)

      val preFlopError = intercept[IllegalArgumentException]:
        PreFlopState.afterAction(preFlop, FoldCommand)

      preFlopError.getMessage shouldBe "Invalid action during PreFlop"

      val flopError = intercept[IllegalArgumentException]:
        FlopState.afterAction(flop, FoldCommand)

      flopError.getMessage shouldBe "Invalid action during Flop"

    "reject Check during River" in:
      val game = Game.newGame().copy(phase = GamePhase.River)

      val error = intercept[IllegalArgumentException]:
        RiverState.afterAction(game, CheckCommand)

      error.getMessage shouldBe "Invalid action during River"

  "reject all actions after Showdown" in:
    val game = Game.newGame().copy(phase = GamePhase.Showdown)

    for command <- Seq(CheckCommand, PlayCommand(1), FoldCommand) do
      val error = intercept[IllegalArgumentException]:
        ShowdownState.afterAction(game, command)

      error.getMessage shouldBe "The game is already finished"

  "preserve betting and cards during phase transitions" in:
    val game = Game.newGame()

    val result = PreFlopState.afterAction(game, CheckCommand)

    result.betting shouldBe game.betting
    result.playerCards shouldBe game.playerCards
    result.dealerCards shouldBe game.dealerCards
    result.communityCards shouldBe game.communityCards
