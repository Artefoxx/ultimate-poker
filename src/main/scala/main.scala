import controller.GameController
import view.TextUI
import scala.annotation.tailrec

@main def runPoker(): Unit =
  val controller = new GameController()
  val textUI     = new TextUI(controller)

  controller.add(textUI)
  textUI.update()

  @tailrec
  def gameLoop(): Unit =
    println("Enter a command (next / quit):")

    val command = Option(scala.io.StdIn.readLine())
      .map(_.trim.toLowerCase)

    command match
      case Some("next") =>
        controller.next()
        gameLoop()

      case Some("quit") | None =>
        println("Thanks for playing Ultimate Poker!")

      case _ =>
        println("Unknown command")
        gameLoop()

  gameLoop()
