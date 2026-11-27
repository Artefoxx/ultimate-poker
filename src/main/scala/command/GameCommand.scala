package command
import model.GameState

trait GameCommand:
  def execute(game: GameState): GameState

  def undo(previousGame: GameState): GameState =
    previousGame
