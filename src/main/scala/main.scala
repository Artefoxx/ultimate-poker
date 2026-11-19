import controller.GameController
import view.TextUI
import command.*
import scala.annotation.tailrec

@main def runPoker(): Unit =
  val controller = new GameController()
  val textUI     = new TextUI(controller)

  controller.add(textUI)
  textUI.update()

  @tailrec
  def gameLoop(): Unit =
    println("Enter a command (check / play 1-4 / fold / quit):")

    val input = Option(scala.io.StdIn.readLine())
      .map(_.trim.toLowerCase)

    input match
      case Some("quit") | None =>
        println("Thanks for playing Ultimate Poker!")

      case Some(value) =>
        val command: Option[GameCommand] = value match
          case "check"  => Some(CheckCommand)
          case "fold"   => Some(FoldCommand)
          case "play 1" => Some(PlayCommand(1))
          case "play 2" => Some(PlayCommand(2))
          case "play 3" => Some(PlayCommand(3))
          case "play 4" => Some(PlayCommand(4))
          case _        => None

        command match
          case Some(gameCommand) =>
            controller.executeCommand(gameCommand) match
              case Left(error) =>
                println(s"Invalid action: $error")

              case Right(_) =>
                println("Action completed successfully.")

          case None =>
            println("Unknown command")

        gameLoop()

  gameLoop()
