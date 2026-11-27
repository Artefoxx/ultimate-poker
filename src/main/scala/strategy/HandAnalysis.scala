package strategy

import model.{Card, HandValue}

class HandAnalysis(
    fiveCardStrategy: HandEvaluationStrategy,
    sevenCardStrategy: HandEvaluationStrategy
):
    def evaluate(cards: Vector[Card]): Option[HandValue] = 
        cards.size match 
            case 5 => Some(fiveCardStrategy.evaluate(cards))
            case 7 => Some(sevenCardStrategy.evaluate(cards))
            case _ => None

