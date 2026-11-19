package command

import model.GameState

case object FoldCommand extends GameCommand:

  override def execute(game: GameState): GameState =
    val updatedBetting = game.betting.copy(
      folded = true
    )

    game.copy(betting = updatedBetting)
