package command

import model.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class GameCommandSpec extends AnyWordSpec with Matchers:

  "GameCommand" should:

    "leave the game unchanged when checking" in:
      val game                 = Game.newGame()
      val command: GameCommand = CheckCommand

      val result = command.execute(game)

      result shouldBe game
      result.phase shouldBe GamePhase.PreFlop

    "place a Play bet with multiplier 4" in:
      val game                 = Game.newGame()
      val command: GameCommand = PlayCommand(4)

      val result = command.execute(game)

      result.betting.playBet shouldBe 40
      result.betting.playerBalance shouldBe 940
      result.phase shouldBe GamePhase.PreFlop

    "place a Play bet with multiplier 3" in:
      val game = Game.newGame()

      val result = PlayCommand(3).execute(game)

      result.betting.playBet shouldBe 30
      result.betting.playerBalance shouldBe 950

    "place a Play bet with multiplier 2" in:
      val game = Game.newGame()

      val result = PlayCommand(2).execute(game)

      result.betting.playBet shouldBe 20
      result.betting.playerBalance shouldBe 960

    "place a Play bet with multiplier 1" in:
      val game = Game.newGame()

      val result = PlayCommand(1).execute(game)

      result.betting.playBet shouldBe 10
      result.betting.playerBalance shouldBe 970

    "mark the player as folded" in:
      val game                 = Game.newGame()
      val command: GameCommand = FoldCommand

      val result = command.execute(game)

      result.betting.folded shouldBe true
      result.phase shouldBe GamePhase.PreFlop

    "leave the original game state unchanged after Play" in:
      val game = Game.newGame()

      val result = PlayCommand(3).execute(game)

      game.betting.playBet shouldBe 0
      game.betting.playerBalance shouldBe 980

      result.betting.playBet shouldBe 30
      result.betting.playerBalance shouldBe 950

    "leave the original game state unchanged after Fold" in:
      val game = Game.newGame()

      val result = FoldCommand.execute(game)

      game.betting.folded shouldBe false
      result.betting.folded shouldBe true

    "preserve other betting values when checking" in:
      val game = Game.newGame()

      val result = CheckCommand.execute(game)

      result.betting shouldBe game.betting
      result.playerCards shouldBe game.playerCards
      result.dealerCards shouldBe game.dealerCards
      result.communityCards shouldBe game.communityCards
