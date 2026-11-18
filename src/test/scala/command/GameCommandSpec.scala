package command

import model.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class GameCommandSpec extends AnyWordSpec with Matchers:

    "GameCommand" should:
        
        "advance the game phase when checking" in:
            val game = Game.newGame()
            val command: GameCommand = CheckCommand
        
            val result = command.execute(game)

            result.phase shouldBe GamePhase.Flop
            game.phase shouldBe GamePhase.PreFlop
        
        "place a Play bet with the correct multiplier" in:
            val game = Game.newGame()
            val command: GameCommand = PlayCommand(4)

            val result = command.execute(game)

            result.betting.playBet shouldBe 40
            result.betting.playerBalance shouldBe 940

        "mark the player as folded" in:
            val game = Game.newGame()
            val command: GameCommand = FoldCommand

            val result = command.execute(game)

            result.betting.folded shouldBe true
        
        "leave the original game state unchanged" in: 
            val game = Game.newGame()
            
            val result = PlayCommand(3).execute(game)

            game.betting.playBet shouldBe 0 
            game.betting.playerBalance shouldBe 980

            result.betting.playBet shouldBe 30
            result.betting.playerBalance shouldBe 950
