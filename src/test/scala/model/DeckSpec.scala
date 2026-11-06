package model
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class DeckSpec extends AnyWordSpec with Matchers:
  "A deck" should {
    "contain 52 cards" in {
      val deck = Deck.full

      deck.size shouldBe 52
    }
    "contain only unique cards" in {
      val deck = Deck.full
      deck.distinct.size shouldBe deck.size
    }
    "keep all the cards after shuffling" in {
      val original = Deck.full
      val shuffled = Deck.shuffled

      original.toSet shouldBe shuffled.toSet
      original.size shouldBe shuffled.size
    }
  }
