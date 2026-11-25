import controller.GameController
import view.TextUI
import command.CommandParser
import scala.annotation.tailrec

@main def runPoker(): Unit =
  val controller = new GameController()
  val textUI = new TextUI(controller)

  controller.add(textUI)
  textUI.update()

  def printResult(result: Either[String, Unit]): Unit =
    result match
      case Left(error) =>
        println(s"Invalid action: $error")

      case Right(_) =>
        println("Action completed successfully.")

  @tailrec
  def gameLoop(): Unit =
    println("Enter a command (check / play 1-4 / fold / undo / quit):")

    val input = Option(scala.io.StdIn.readLine())
      .map(_.trim.toLowerCase)

    input match
      case Some("quit") | None =>
        println("Thanks for playing Ultimate Poker!")

      case Some("undo") =>
        printResult(controller.undo())
        gameLoop()

      case Some(value) =>
        CommandParser.parse(value) match
          case Some(gameCommand) =>
            printResult(controller.executeCommand(gameCommand))

          case None =>
            println("Unknown command")

        gameLoop()

  gameLoop()