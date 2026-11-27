package controller
import observer.{Observer, Observable}
import command.{GameCommand, UndoManager}
import validation.BetValidationChain
import state.PhaseState
import factory.{GameFactory, StandardGameFactory}
import model.*

class GameController(gameFactory: GameFactory = new StandardGameFactory()) extends Observable:

  private var game: GameState = gameFactory.newGame()
  private var undoManager: UndoManager = UndoManager()

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
        val previousGame = game
        val currentState = PhaseState.from(game.phase)
        val updatedGame  = command.execute(game)

        game = currentState.afterAction(updatedGame, command)

        undoManager = undoManager.record(command, previousGame)

        notifyObservers()
        Right(())
  
  def undo(): Either[String, Unit] = 
    undoManager.undo() match
      case None => 
        Left("Nothing to undo")
      
      case Some((restoredGame, updatedManager)) =>
        game = restoredGame 
        undoManager = updatedManager

        notifyObservers()
        Right(())
    
