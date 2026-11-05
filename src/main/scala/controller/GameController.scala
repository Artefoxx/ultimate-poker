package controller

import model.*

class GameController extends Observable:
  private var game: GameState = Game.newGame()

  def getGameState: GameState =
    game

  def next(): Unit =
    game = nextPhase(game)
    notifyObservers()

  private var observers: Vector[Observer] = Vector.empty

  override def add(observer: Observer): Unit =
    observers = observers :+ observer

  override def remove(observer: Observer): Unit =
    observers = observers.filterNot(o => o == observer)

  override def notifyObservers(): Unit =
    observers.foreach(o => o.update())
