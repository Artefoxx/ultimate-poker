package model

import factory.StandardGameFactory

object Game:

  def newGame(): GameState =
    val factory = new StandardGameFactory()
    factory.newGame()