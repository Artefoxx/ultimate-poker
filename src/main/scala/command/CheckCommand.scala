package command

import model.*

case object CheckCommand extends GameCommand:

  override def execute(game: GameState): GameState =
    game
