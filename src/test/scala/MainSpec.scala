import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class MainSpec extends AnyWordSpec with Matchers:

  private def run(commands: String): String =
    val input = new java.io.ByteArrayInputStream(
      commands.getBytes("UTF-8")
    )
    val output  = new java.io.ByteArrayOutputStream()
    val printer = new java.io.PrintStream(output)

    try
      Console.withIn(input) {
        Console.withOut(printer) {
          runPoker()
        }
      }
      printer.flush()
      output.toString("UTF-8")
    finally printer.close()

  "Main" should {

    "quit the game" in {
      val output = run("quit\n")

      output should include("Phase: PreFlop")
      output should include("Enter a command")
      output should include("Thanks for playing Ultimate Poker!")
    }

    "move from PreFlop to Flop after Check" in {
      val output = run("check\nquit\n")

      output should include("Phase: PreFlop")
      output should include("Phase: Flop")
      output should include("Action completed successfully.")
    }

    "reach Showdown through valid actions" in {
      val output = run("check\ncheck\nplay 1\nquit\n")

      output should include("Phase: Flop")
      output should include("Phase: River")
      output should include("Phase: Showdown")
      output should include("--- Dealer cards ---")
    }

    "reach Showdown directly with Play 3" in {
      val output = run("play 3\nquit\n")

      output should include("Phase: Showdown")
      output should include("Play Bet: 30")
      output should include("Balance: 950")
    }

    "accept Play 4 during PreFlop" in {
      val output = run("play 4\nquit\n")

      output should include("Phase: Showdown")
      output should include("Play Bet: 40")
      output should include("Balance: 940")
    }

    "accept Play 2 during Flop" in {
      val output = run("check\nplay 2\nquit\n")

      output should include("Phase: Showdown")
      output should include("Play Bet: 20")
      output should include("Balance: 960")
    }

    "accept Fold during River" in {
      val output = run("check\ncheck\nfold\nquit\n")

      output should include("Phase: Showdown")
      output should include("Folded: true")
    }

    "reject an invalid bet" in {
      val output = run("play 2\nquit\n")

      output should include("Invalid action:")
      output should include("Action is not allowed in this phase")
      output should not include "Phase: Showdown"
    }

    "reject actions after Showdown" in {
      val output = run("play 3\ncheck\nquit\n")

      output should include("Phase: Showdown")
      output should include("Invalid action:")
      output should include("Play bet has already been placed")
    }

    "restore the displayed game after undoing a play bet" in {
      val output = run("PLAY 4\nundo\nundo\nquit\n")

      output should include("Phase: Showdown")
      output should include("Balance: 940")
      output should include("Play Bet: 40")

      val restoredPhase = output.lastIndexOf("Phase: PreFlop")
      restoredPhase should be > output.indexOf("Phase: Showdown")

      val restored = output.substring(restoredPhase)
      restored should include("Balance: 980")
      restored should include("Play Bet: 0")
      restored should include("Invalid action: Nothing to undo")
    }

    "handle unknown commands" in {
      val output = run("invalid\nquit\n")

      output should include("Unknown command")
    }

    "handle incomplete Play commands" in {
      val output = run("play\nquit\n")

      output should include("Unknown command")
    }

    "handle commands regardless of case and surrounding spaces" in {
      val output = run("  CHECK  \nquit\n")

      output should include("Phase: Flop")
    }

    "exit when input reaches EOF" in {
      val output = run("")

      output should include("Thanks for playing Ultimate Poker!")
    }

    "reject a nonnumeric play multiplier" in {
      val output = run("play abc\nquit\n")

      output should include("Unknown command")
      output should not include "Phase: Showdown"
      output should include("Thanks for playing Ultimate Poker!")
    }

    "reject a play multiplier without the command name" in {
      val output = run("4\nquit\n")

      output should include("Unknown command")
      output should not include "Phase: Showdown"
    }

  }
