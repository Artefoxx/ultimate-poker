import model.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class GameStateSpec extends AnyWordSpec with Matchers:
  "A game state" should {
    "be PreFlop" in {
      val game = GameState(
        Vector.empty[Card],
        Vector.empty[Card],
        Vector.empty[Card],
        GamePhase.PreFlop
      )

      game.phase shouldBe GamePhase.PreFlop
    }
  }
