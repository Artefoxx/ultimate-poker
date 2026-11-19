package model

case class GameState(
    playerCards: Vector[Card],
    dealerCards: Vector[Card],
    communityCards: Vector[Card],
    phase: GamePhase,
    betting: BettingState = BettingState.initial
)

// val game = GameState(
//   Vector.empty[Card],
//   Vector.empty[Card],
//   Vector.empty[Card],
//   GamePhase.PreFlop
// )
