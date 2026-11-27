package strategy

import model.{Card, HandValue, HandEvaluator}

object SevenCardStrategy extends HandEvaluationStrategy:
  private val handOrdering: Ordering[HandValue] = new Ordering[HandValue]:
    override def compare(first: HandValue, second: HandValue): Int =
      HandEvaluator.compare(first, second)

  override def evaluate(cards: Vector[Card]): HandValue =
    require(cards.size == 7, "Exactly 7 cards required")

    cards
      .combinations(5)
      .map(FiveCardStrategy.evaluate)
      .max(using handOrdering)
