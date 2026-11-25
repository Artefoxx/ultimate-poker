package command

import scala.util.Try

object CommandParser:
    def parse(input: String): Option[GameCommand] = 
        val normalizedInput = input.trim.toLowerCase

        normalizedInput match
            case "check" => Some(CheckCommand)
            case "fold" => Some(FoldCommand)

            case value if value.startsWith("play ") =>
                val multiplier = value.stripPrefix("Play ").trim 

                Try(multiplier.toInt).toOption.map(
                    number => PlayCommand(number)
                )
            
            case _ => None
            
        