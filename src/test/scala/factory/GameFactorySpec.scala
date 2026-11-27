package factory

import controller.GameController
import model.*
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class GameFactorySpec extends AnyWordSpec with Matchers:
  "FixedDeckGameFactory" should:
    "deal the supplied cards in order when injected into the controller" in:
      val deck = Deck.full
      val game = new GameController(new FixedDeckGameFactory(deck)).getGameState

      game.playerCards shouldBe deck.take(2)
      game.dealerCards shouldBe deck.slice(2, 4)
      game.communityCards shouldBe deck.slice(4, 9)
      game.phase shouldBe GamePhase.PreFlop
