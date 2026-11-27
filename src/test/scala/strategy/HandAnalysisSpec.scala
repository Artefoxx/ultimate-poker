package strategy

import model.*
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class HandAnalysisSpec extends AnyWordSpec with Matchers:
  private val analysis = new HandAnalysis(FiveCardStrategy, SevenCardStrategy)
  private val straightFlush =
    Vector(Rank.Ten, Rank.Jack, Rank.Queen, Rank.King, Rank.Ace)
      .map(rank => Card(rank, Suit.Spades))

  "HandAnalysis" should:
    "evaluate five cards directly" in:
      analysis.evaluate(straightFlush) shouldBe Some(
        HandEvaluator.evaluateHand(straightFlush)
      )

    "find the best five cards in a seven-card hand" in:
      val extra =
        Vector(Card(Rank.Two, Suit.Hearts), Card(Rank.Three, Suit.Diamond))
      val cards    = extra ++ straightFlush
      val expected = Some(HandEvaluator.evaluateHand(straightFlush))

      analysis.evaluate(cards) shouldBe expected
      analysis.evaluate(cards.reverse) shouldBe expected

    "return None for a hand of unsupported size" in:
      analysis.evaluate(straightFlush.take(4)) shouldBe None

  "SevenCardStrategy" should:
    "require exactly seven cards when called directly" in:
      intercept[IllegalArgumentException] {
        SevenCardStrategy.evaluate(straightFlush)
      }.getMessage should include("Exactly 7 cards required")
