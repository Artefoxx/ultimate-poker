import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class CardSpec extends AnyWordSpec with Matchers {
    "A card" should { 
        "have a rank and a suit" in{
            val card = Card("Ace", "Spades")

            card.rank shouldBe "Ace"
            card.suit shouldBe "Spades"
        } 
    }
}