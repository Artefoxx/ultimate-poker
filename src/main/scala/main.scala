//> using test.dep org.scalatest::scalatest:3.2.19
//> using testFramework org.scalatest.tools.Framework

@main def runPoker(): Unit =
  var game    = Game.newGame()
  var running = true

  while running do
    TextUI.showGame(game)

    println("Enter a command (next / quit):")
    val command = scala.io.StdIn.readLine()

    if command == "next" then game = nextPhase(game)
    else if command == "quit" then running = false
    else println("Unknown command")

  // println("Enter a command (next / quit):")
  // val command = scala.io.StdIn.readLine()

  // if command == "next" then
  //     TextUI.showGame(nextPhase(game))
