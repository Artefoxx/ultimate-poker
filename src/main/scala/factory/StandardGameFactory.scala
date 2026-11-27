package factory

import model.{Card, Deck}

class StandardGameFactory extends GameFactory:

    override protected def createDeck(): Vector[Card] =
        Deck.shuffled
