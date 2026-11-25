package command

import model.GameState
import java.nio.channels.NonReadableChannelException

case class UndoManager(
    history: List[(GameCommand, GameState)] = Nil
):
    def record(
        command: GameCommand,
        previousGame: GameState
    ): UndoManager = 
        copy(history = (command, previousGame) :: history)

    def undo(): Option[(GameState, UndoManager)] =
        history match 
            case Nil =>
                None 
            
            case(command, previousGame) :: remaining => 
                val restoredGame = command.undo(previousGame)
                val updatedManager = copy(history = remaining)

                Some((restoredGame, updatedManager))