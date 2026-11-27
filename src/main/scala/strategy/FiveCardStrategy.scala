package strategy

import model.{Card, HandValue, HandEvaluator}

object FiveCardStrategy extends HandEvaluationStrategy:
    override def evaluate(cards: Vector[Card]): HandValue = 
        HandEvaluator.evaluateHand(cards)
