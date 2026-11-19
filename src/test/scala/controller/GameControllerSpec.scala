package controller

import model.*
import command.*
import observer.Observer
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class GameControllerSpec extends AnyWordSpec with Matchers:

  "GameController" should {

    "start at PreFlop with initial betting state" in {
      val controller = new GameController()

      controller.getGameState.phase shouldBe GamePhase.PreFlop
      controller.getGameState.betting shouldBe BettingState.initial
    }

    "move from PreFlop to Flop after Check" in {
      val controller = new GameController()

      controller.executeCommand(CheckCommand) shouldBe Right(())

      controller.getGameState.phase shouldBe GamePhase.Flop
    }

    "reach Showdown through valid actions" in {
      val controller = new GameController()

      controller.executeCommand(CheckCommand) shouldBe Right(())
      controller.executeCommand(CheckCommand) shouldBe Right(())
      controller.executeCommand(PlayCommand(1)) shouldBe Right(())

      controller.getGameState.phase shouldBe GamePhase.Showdown
    }

    "place a Play bet and update the balance" in {
      val controller = new GameController()

      controller.executeCommand(PlayCommand(4)) shouldBe Right(())

      controller.getGameState.betting.playBet shouldBe 40
      controller.getGameState.betting.playerBalance shouldBe 940
      controller.getGameState.phase shouldBe GamePhase.Showdown
    }

    "reject an invalid action without changing the game" in {
      val controller   = new GameController()
      val originalGame = controller.getGameState

      controller.executeCommand(PlayCommand(2)) shouldBe
        Left("Action is not allowed in this phase")

      controller.getGameState shouldBe originalGame
    }

    "reject actions after Showdown" in {
      val controller = new GameController()

      controller.executeCommand(PlayCommand(3)) shouldBe Right(())

      controller.executeCommand(CheckCommand) shouldBe
        Left("Play bet has already been placed")

      controller.getGameState.phase shouldBe GamePhase.Showdown
    }

    "notify observers after a successful action" in {
      val controller  = new GameController()
      var updateCount = 0

      val observer = new Observer:
        override def update(): Unit =
          updateCount += 1

      controller.add(observer)
      controller.executeCommand(CheckCommand) shouldBe Right(())

      updateCount shouldBe 1
    }

    "not notify observers after an invalid action" in {
      val controller  = new GameController()
      var updateCount = 0

      val observer = new Observer:
        override def update(): Unit =
          updateCount += 1

      controller.add(observer)
      controller.executeCommand(PlayCommand(2)) shouldBe
        Left("Action is not allowed in this phase")

      updateCount shouldBe 0
    }

    "stop notifying removed observers" in {
      val controller  = new GameController()
      var updateCount = 0

      val observer = new Observer:
        override def update(): Unit =
          updateCount += 1

      controller.add(observer)

      controller.executeCommand(CheckCommand) shouldBe Right(())
      updateCount shouldBe 1

      controller.remove(observer)

      controller.executeCommand(CheckCommand) shouldBe Right(())
      updateCount shouldBe 1
    }

  }
