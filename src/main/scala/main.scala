import model.*
import controller.GameController
import view.TextUI
//> using test.dep org.scalatest::scalatest:3.2.19
//> using testFramework org.scalatest.tools.Framework

@main def runPoker(): Unit =
  val controller = new GameController()
  val textUI = new TextUI(controller)

  controller.add(textUI)
  textUI.update()

  var running = true

  while running do

    println("Enter a command (next / quit):")
    val command = scala.io.StdIn.readLine()

    if command == "next" then controller.next()
    else if command == "quit" then running = false
    else println("Unknown command")

  // println("Enter a command (next / quit):")
  // val command = scala.io.StdIn.readLine()

  // if command == "next" then
  //     TextUI.showGame(nextPhase(game))
