import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class MainSpec extends AnyWordSpec with Matchers:

  private def runWithInput(commands: String): String =
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
      val output = runWithInput("quit\n")

      output should include("Phase: PreFlop")
      output should include("Enter a command")
    }

    "move to the next phase" in {
      val output = runWithInput("next\nquit\n")

      output should include("Phase: PreFlop")
      output should include("Phase: Flop")
    }

    "handle unknown commands" in {
      val output = runWithInput("invalid\nquit\n")

      output should include("Unknown command")
    }

    "reach Showdown" in {
      val output = runWithInput("next\nnext\nnext\nquit\n")

      output should include("Phase: Showdown")
      output should include("Dealer cards:")
    }
  }
