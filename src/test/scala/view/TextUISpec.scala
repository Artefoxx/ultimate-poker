package view

import model.*
import controller.GameController
import command.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class TextUISpec extends AnyWordSpec with Matchers:

  private def captureOutput(game: GameState): String =
    val stream  = new java.io.ByteArrayOutputStream()
    val printer = new java.io.PrintStream(stream)

    try
      Console.withOut(printer) {
        val controller = new GameController()
        val textUI     = new TextUI(controller)
        textUI.showGame(game)
      }
      printer.flush()
      stream.toString("UTF-8")
    finally printer.close()

  private def captureUpdate(controller: GameController): String =
    val stream  = new java.io.ByteArrayOutputStream()
    val printer = new java.io.PrintStream(stream)

    try
      Console.withOut(printer) {
        val textUI = new TextUI(controller)
        textUI.update()
      }
      printer.flush()
      stream.toString("UTF-8")
    finally printer.close()

  private def cardText(card: Card): String =
    s"${card.rank} of ${card.suit}"

  "TextUI" should {

    "show player cards and hide community and dealer cards during PreFlop" in {
      val game   = Game.newGame()
      val output = captureOutput(game)

      output should include("========= Ultimate Poker =========")
      output should include("Phase: PreFlop")
      output should include("--- Player cards ---")
      output should include("--- Community cards ---")
      output should include("--- Dealer cards ---")
      output should include("Hidden")

      game.playerCards.foreach { card =>
        output should include(cardText(card))
      }

      game.communityCards.foreach { card =>
        output should not include cardText(card)
      }

      game.dealerCards.foreach { card =>
        output should not include cardText(card)
      }
    }

    "show 3 community cards and hide dealer cards during Flop" in {
      val game   = Game.newGame().copy(phase = GamePhase.Flop)
      val output = captureOutput(game)

      output should include("Phase: Flop")
      output should include("Hidden")

      game.communityCards.take(3).foreach { card =>
        output should include(cardText(card))
      }

      game.communityCards.drop(3).foreach { card =>
        output should not include cardText(card)
      }

      game.dealerCards.foreach { card =>
        output should not include cardText(card)
      }
    }

    "show 5 community cards and hide dealer cards during River" in {
      val game   = Game.newGame().copy(phase = GamePhase.River)
      val output = captureOutput(game)

      output should include("Phase: River")
      output should include("Hidden")

      game.communityCards.foreach { card =>
        output should include(cardText(card))
      }

      game.dealerCards.foreach { card =>
        output should not include cardText(card)
      }
    }

    "reveal dealer cards during Showdown" in {
      val game   = Game.newGame().copy(phase = GamePhase.Showdown)
      val output = captureOutput(game)

      output should include("Phase: Showdown")
      output should include("--- Dealer cards ---")
      output should not include "Hidden"

      game.dealerCards.foreach { card =>
        output should include(cardText(card))
      }

      game.communityCards.foreach { card =>
        output should include(cardText(card))
      }
    }

    "display the initial betting information" in {
      val game   = Game.newGame()
      val output = captureOutput(game)

      output should include("--- Betting ---")
      output should include("Balance: 980")
      output should include("Ante: 10")
      output should include("Blind: 10")
      output should include("Play Bet: 0")
      output should include("Folded: false")
      output should include("==================================")
    }

    "display updated betting information" in {
      val game = Game
        .newGame()
        .copy(
          betting = BettingState(
            playerBalance = 940,
            ante = 10,
            blind = 10,
            playBet = 40,
            folded = false
          )
        )

      val output = captureOutput(game)

      output should include("Balance: 940")
      output should include("Play Bet: 40")
      output should include("Folded: false")
    }

    "display folded status" in {
      val game = Game
        .newGame()
        .copy(
          betting = BettingState.initial.copy(folded = true)
        )

      val output = captureOutput(game)

      output should include("Folded: true")
    }

    "display the current controller state when updated" in {
      val controller = new GameController()

      controller.executeCommand(CheckCommand) shouldBe Right(())

      val output = captureUpdate(controller)

      output should include("Phase: Flop")
      output should include("Balance: 980")
      output should include("Play Bet: 0")
    }
    "not evaluate the player hand during PreFlop" in {
      val game = Game.newGame()
      val output = captureOutput(game)

      output should include("Player hand: Not available yet")
    }
    "evaluate the player hand using only visible cards" in {
      val game = GameState(
        playerCards = Vector(
          Card(Rank.Ace, Suit.Spades),
          Card(Rank.Ace, Suit.Hearts)
        ),
        dealerCards = Vector(
          Card(Rank.Queen, Suit.Clubs),
          Card(Rank.Jack, Suit.Hearts)
        ),
        communityCards = Vector(
          Card(Rank.King, Suit.Clubs),
          Card(Rank.Nine, Suit.Diamond),
          Card(Rank.Three, Suit.Spades),
          Card(Rank.King, Suit.Hearts),
          Card(Rank.Ace, Suit.Diamond)
        ),
        phase = GamePhase.PreFlop
      )

      val flopOutput = captureOutput(
        game.copy(phase = GamePhase.Flop)
      )

      flopOutput should include("Player hand: OnePair")

      val riverOutput = captureOutput(
        game.copy(phase = GamePhase.River)
      )

      riverOutput should include("Player hand: FullHouse")

      val showdownOutput = captureOutput(
        game.copy(phase = GamePhase.Showdown)
      )

      showdownOutput should include("Player hand: FullHouse")
    }

  }
