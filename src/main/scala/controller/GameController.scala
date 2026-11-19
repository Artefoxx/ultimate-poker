package controller
import observer.{Observer, Observable}
import command.GameCommand
import validation.BetValidationChain
import state.PhaseState
import model.*

class GameController extends Observable:
  private var game: GameState = Game.newGame()

  def getGameState: GameState = game

  private var observers: Vector[Observer] = Vector.empty

  override def add(observer: Observer): Unit =
    observers = observers :+ observer

  override def remove(observer: Observer): Unit =
    observers = observers.filterNot(o => o == observer)

  override def notifyObservers(): Unit =
    observers.foreach(o => o.update())

  def executeCommand(command: GameCommand): Either[String, Unit] =
    BetValidationChain.validator.validate(game, command) match
      case Left(error) =>
        Left(error)

      case Right(_) =>
        val currentState = PhaseState.from(game.phase)
        val updatedGame  = command.execute(game)

        game = currentState.afterAction(updatedGame, command)

        notifyObservers()
        Right(())
