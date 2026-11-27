package strategy

import model.{Card, HandValue, HandEvaluator}

object SevenCardStrategy extends HandEvaluationStrategy:
    override def evaluate(cards: Vector[Card]): HandValue = 
        require(cards.size == 7, "Exactly 7 cards required")

        val combinations = cards.combinations(5).toVector

        val firstHand = FiveCardStrategy.evaluate(combinations.head)

        val bestHand = combinations.tail.foldLeft(firstHand){
            (best, combination) =>

                val currentHand = FiveCardStrategy.evaluate(combination)

                if HandEvaluator.compare(currentHand, best) == 1 then 
                    currentHand
                else 
                    best
        }
        
        bestHand
