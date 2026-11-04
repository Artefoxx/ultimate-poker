package model

enum GamePhase:
  case PreFlop, Flop, River, Showdown

def nextPhase(game: GameState): GameState =
  val next = game.phase match
    case GamePhase.PreFlop  => GamePhase.Flop
    case GamePhase.Flop     => GamePhase.River
    case GamePhase.River    => GamePhase.Showdown
    case GamePhase.Showdown => GamePhase.Showdown

  game.copy(phase = next)
