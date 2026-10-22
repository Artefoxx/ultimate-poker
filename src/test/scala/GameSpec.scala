import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class GameSpec extends AnyWordSpec with Matchers:
  "A new game" should {
    "deal 2 cards to the player" in {
      val game = Game.newGame()
      game.playerCards.size shouldBe 2
    }
    "deal 2 cards to the dealer" in {
      val game = Game.newGame()

      game.dealerCards.size shouldBe 2
    }
    "deal 5 community cards" in {
      val game = Game.newGame()
      game.communityCards.size shouldBe 5
    }
    "contain only unique cards" in {
      val game     = Game.newGame()
      val allCards = game.playerCards ++ game.dealerCards ++ game.communityCards
      allCards.distinct.size shouldBe 9
    }
    "start in the PreFlop phase" in {
      val game = Game.newGame()
      game.phase shouldBe GamePhase.PreFlop
    }

    "Game phase" should {
      "move from PreFlop to Flop" in {
        val game = Game.newGame()
        val next = nextPhase(game)
        next.phase shouldBe GamePhase.Flop
      }
      "move from Flop to River" in {
        val game = Game.newGame().copy(phase = GamePhase.Flop)
        val next = nextPhase(game)
        next.phase shouldBe GamePhase.River
      }
      "move from River to Showdown" in {
        val game = Game.newGame().copy(phase = GamePhase.River)
        val next = nextPhase(game)
        next.phase shouldBe GamePhase.Showdown
      }
      "stay in Showdown when the game is finished" in {
        val game = Game.newGame().copy(phase = GamePhase.Showdown)
        val next = nextPhase(game)
        next.phase shouldBe GamePhase.Showdown
      }

    }
  }
