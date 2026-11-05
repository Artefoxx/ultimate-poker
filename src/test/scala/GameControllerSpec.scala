import model.*
import controller.GameController
import controller.Observer
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class GameControllerSpec extends AnyWordSpec with Matchers:
  "GameController" should {

    "start at preflop" in {
      val controller = new GameController()

      controller.getGameState.phase shouldBe GamePhase.PreFlop
    }

    "move to the next phase" in {
      val controller = new GameController()

      controller.next()

      controller.getGameState.phase shouldBe GamePhase.Flop
    }

    "reach Showdown" in {
      val controller = new GameController()

      controller.next()
      controller.next()
      controller.next()

      controller.getGameState.phase shouldBe GamePhase.Showdown
    }
    "notify observers after phase change" in {
      val controller = new GameController()

      var updateCount = 0

      val observer = new Observer:
        override def update(): Unit =
          updateCount += 1

      controller.add(observer)
      controller.next()

      updateCount shouldBe 1
    }
    "stop notifying removed observers" in {
      val controller = new GameController()

      var updateCount = 0

      val observer = new Observer:
        override def update(): Unit =
          updateCount += 1

      controller.add(observer)
      controller.next()

      controller.remove(observer)
      controller.next()

      updateCount shouldBe 1

    }

  }
