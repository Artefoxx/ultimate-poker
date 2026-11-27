package factory

import model.{Card, GameState, GamePhase}

abstract class GameFactory:
    protected def createDeck(): Vector[Card]

    def newGame(): GameState = 
        val deck = createDeck()

        val playerCards = deck.take(2)
        val dealerCards = deck.slice(2, 4)
        val communityCards = deck.slice(4, 9)

        GameState(
            playerCards, 
            dealerCards,
            communityCards,
            GamePhase.PreFlop
        )