package state

import model.*
import command.GameCommand

trait PhaseState:
  def afterAction(
      game: GameState,
      command: GameCommand
  ): GameState

object PhaseState:
  def from(phase: GamePhase): PhaseState =
    phase match
      case GamePhase.PreFlop  => PreFlopState
      case GamePhase.Flop     => FlopState
      case GamePhase.River    => RiverState
      case GamePhase.Showdown => ShowdownState
