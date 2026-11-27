package strategy

import model.{Card, HandValue}

trait HandEvaluationStrategy:
    def evaluate(cards: Vector[Card]): HandValue