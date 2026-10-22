object Game:

  def newGame(): GameState =
    val deck = Deck.shuffled

    val playerCards    = deck.take(2)
    val dealerCards    = deck.slice(2, 4)
    val communityCards = deck.slice(4, 9)

    GameState(playerCards, dealerCards, communityCards, GamePhase.PreFlop)
