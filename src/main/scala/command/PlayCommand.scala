package command
import model.GameState

case class PlayCommand(multiplier: Int) extends GameCommand:
    override def execute(game: GameState): GameState =
        
        val betting = game.betting 

        val amount = betting.ante * multiplier

        val updatedBetting = betting.copy(
            playerBalance = betting.playerBalance - amount,
            playBet = amount 
        )

        game.copy(betting = updatedBetting)
