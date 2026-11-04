import model.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class CardSpec extends AnyWordSpec with Matchers:
  "A card" should {
    "have a rank and a suit" in {
      val card = Card(Rank.Queen, Suit.Spades)

      card.rank shouldBe Rank.Queen
      card.suit shouldBe Suit.Spades
    }
  }
