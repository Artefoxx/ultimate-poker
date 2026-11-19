package validation

import model.GameState
import command.GameCommand

abstract class BetValidator(
    val next: Option[BetValidator]
):
  protected def check(
      game: GameState,
      command: GameCommand
  ): Either[String, Unit]

  final def validate(
      game: GameState,
      command: GameCommand
  ): Either[String, Unit] =

    check(game, command) match
      case Left(error) => Left(error)

      case Right(_) =>
        next match
          case Some(handler) => handler.validate(game, command)

          case None => Right(())
