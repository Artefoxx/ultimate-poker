package factory

import model.Card

class FixedDeckGameFactory(deck: Vector[Card]) extends GameFactory:
    override protected def createDeck(): Vector[Card] =
        deck
