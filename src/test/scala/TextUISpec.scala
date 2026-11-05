import model.*
import view.TextUI
import controller.GameController
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class TextUISpec extends AnyWordSpec with Matchers:

  private def captureOutput(game: GameState): String =
    val stream  = new java.io.ByteArrayOutputStream()
    val printer = new java.io.PrintStream(stream)

    try
      Console.withOut(printer) {
        val controller = new GameController()
        val textUI = new TextUI(controller)
        textUI.showGame(game)
      }
      printer.flush()
      stream.toString("UTF-8")
    finally printer.close()

  "TextUI" should {
    "hide community and dealer cards during PreFlop" in {
      val game   = Game.newGame()
      val output = captureOutput(game)
      output should include("Phase: PreFlop")
      output should include("Community cards:\nDealer cards: Hidden")
    }
    "show 3 community cards and hide dealer cards during Flop" in {
      val game   = nextPhase(Game.newGame())
      val output = captureOutput(game)
      output should include("Phase: Flop")
      output should include("Dealer cards: Hidden")
      game.communityCards
        .take(3)
        .foreach(card => output should include(s"${card.rank} of ${card.suit}"))
      output should not include s"${game.communityCards(3).rank} of ${game.communityCards(3).suit}"
    }
    "show 5 community cards and hide dealer cards during River" in {
      val game   = nextPhase(nextPhase(Game.newGame()))
      val output = captureOutput(game)
      output should include("Phase: River")
      output should include("Dealer cards: Hidden")
      game.communityCards.foreach(card =>
        output should include(s"${card.rank} of ${card.suit}")
      )
    }
    "reveal dealer cards during Showdown" in {
      val game   = nextPhase(nextPhase(nextPhase(Game.newGame())))
      val output = captureOutput(game)
      output should include("Phase: Showdown")
      output should include("Dealer cards:")
      game.dealerCards.foreach(card =>
        output should include(s"${card.rank} of ${card.suit}")
      )
    }
  }
