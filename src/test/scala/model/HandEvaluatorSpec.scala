package model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class HandEvaluatorSpec extends AnyWordSpec with Matchers:

  "HandEvaluator" should:
    "recognize high card" in:
      val cards = Vector(
        Card(Rank.Ace, Suit.Spades),
        Card(Rank.Jack, Suit.Hearts),
        Card(Rank.Nine, Suit.Clubs),
        Card(Rank.Seven, Suit.Diamond),
        Card(Rank.Four, Suit.Spades)
      )
      HandEvaluator.evaluate(cards) shouldBe HandRank.HighCard

    "recognize one pair" in:
      val cards = Vector(
        Card(Rank.Ace, Suit.Spades),
        Card(Rank.Ace, Suit.Hearts),
        Card(Rank.King, Suit.Clubs),
        Card(Rank.Nine, Suit.Diamond),
        Card(Rank.Four, Suit.Spades)
      )
      HandEvaluator.evaluate(cards) shouldBe HandRank.OnePair

    "recognize two pair" in:
      val cards = Vector(
        Card(Rank.Ace, Suit.Spades),
        Card(Rank.Ace, Suit.Hearts),
        Card(Rank.King, Suit.Clubs),
        Card(Rank.King, Suit.Diamond),
        Card(Rank.Four, Suit.Spades)
      )
      HandEvaluator.evaluate(cards) shouldBe HandRank.TwoPair

    "recognize three of a kind" in:
      val cards = Vector(
        Card(Rank.Ace, Suit.Spades),
        Card(Rank.Ace, Suit.Hearts),
        Card(Rank.Ace, Suit.Clubs),
        Card(Rank.Nine, Suit.Diamond),
        Card(Rank.Four, Suit.Spades)
      )
      HandEvaluator.evaluate(cards) shouldBe HandRank.ThreeOfAKind

    "recognize straight" in:
      val cards = Vector(
        Card(Rank.Five, Suit.Spades),
        Card(Rank.Six, Suit.Hearts),
        Card(Rank.Seven, Suit.Clubs),
        Card(Rank.Eight, Suit.Diamond),
        Card(Rank.Nine, Suit.Spades)
      )
      HandEvaluator.evaluate(cards) shouldBe HandRank.Straight

    "recognize flush" in:
      val cards = Vector(
        Card(Rank.Ace, Suit.Spades),
        Card(Rank.Jack, Suit.Spades),
        Card(Rank.Nine, Suit.Spades),
        Card(Rank.Seven, Suit.Spades),
        Card(Rank.Four, Suit.Spades)
      )
      HandEvaluator.evaluate(cards) shouldBe HandRank.Flush

    "recognize full house" in:
      val cards = Vector(
        Card(Rank.Ace, Suit.Spades),
        Card(Rank.Ace, Suit.Hearts),
        Card(Rank.Ace, Suit.Clubs),
        Card(Rank.King, Suit.Diamond),
        Card(Rank.King, Suit.Spades)
      )
      HandEvaluator.evaluate(cards) shouldBe HandRank.FullHouse

    "recognize four of a kind" in:
      val cards = Vector(
        Card(Rank.Ace, Suit.Spades),
        Card(Rank.Ace, Suit.Hearts),
        Card(Rank.Ace, Suit.Clubs),
        Card(Rank.Ace, Suit.Diamond),
        Card(Rank.King, Suit.Spades)
      )
      HandEvaluator.evaluate(cards) shouldBe HandRank.FourOfAKind

    "recognize straight flush" in:
      val cards = Vector(
        Card(Rank.Five, Suit.Spades),
        Card(Rank.Six, Suit.Spades),
        Card(Rank.Seven, Suit.Spades),
        Card(Rank.Eight, Suit.Spades),
        Card(Rank.Nine, Suit.Spades)
      )
      HandEvaluator.evaluate(cards) shouldBe HandRank.StraightFlush

    "recognize ace-low straight" in:
      val cards = Vector(
        Card(Rank.Ace, Suit.Spades),
        Card(Rank.Two, Suit.Hearts),
        Card(Rank.Three, Suit.Clubs),
        Card(Rank.Four, Suit.Diamond),
        Card(Rank.Five, Suit.Spades)
      )
      HandEvaluator.evaluate(cards) shouldBe HandRank.Straight

    "reject hands with fewer than five cards" in:
      val cards = Vector(
        Card(Rank.Ace, Suit.Spades),
        Card(Rank.King, Suit.Hearts),
        Card(Rank.Queen, Suit.Clubs),
        Card(Rank.Jack, Suit.Diamond)
      )
      val exception = intercept[IllegalArgumentException] {
        HandEvaluator.evaluate(cards)
      }
      exception.getMessage should include("Exactly 5 cards required")

    "reject hands with more than five cards" in:
      val cards = Vector(
        Card(Rank.Ace, Suit.Spades),
        Card(Rank.King, Suit.Hearts),
        Card(Rank.Queen, Suit.Clubs),
        Card(Rank.Jack, Suit.Diamond),
        Card(Rank.Ten, Suit.Spades),
        Card(Rank.Nine, Suit.Hearts)
      )

      an[IllegalArgumentException] should be thrownBy
        HandEvaluator.evaluate(cards)

    "return correct kickers for high card" in:
      val cards = Vector(
        Card(Rank.Nine, Suit.Clubs),
        Card(Rank.Ace, Suit.Spades),
        Card(Rank.Four, Suit.Spades),
        Card(Rank.Jack, Suit.Hearts),
        Card(Rank.Seven, Suit.Diamond)
      )

      HandEvaluator.evaluateHand(cards) shouldBe
        HandValue(HandRank.HighCard, Vector(14, 11, 9, 7, 4))

    "return correct kickers for one pair" in:
      val cards = Vector(
        Card(Rank.Ace, Suit.Spades),
        Card(Rank.Five, Suit.Hearts),
        Card(Rank.King, Suit.Clubs),
        Card(Rank.Five, Suit.Diamond),
        Card(Rank.Nine, Suit.Spades)
      )

      HandEvaluator.evaluateHand(cards) shouldBe
        HandValue(HandRank.OnePair, Vector(5, 14, 13, 9))

    "return correct kickers for two pair" in:
      val cards = Vector(
        Card(Rank.Five, Suit.Spades),
        Card(Rank.King, Suit.Hearts),
        Card(Rank.Ace, Suit.Clubs),
        Card(Rank.Five, Suit.Diamond),
        Card(Rank.King, Suit.Spades)
      )

      HandEvaluator.evaluateHand(cards) shouldBe
        HandValue(HandRank.TwoPair, Vector(13, 5, 14))

    "return correct kickers for three of a kind" in:
      val cards = Vector(
        Card(Rank.Ace, Suit.Spades),
        Card(Rank.Seven, Suit.Hearts),
        Card(Rank.King, Suit.Clubs),
        Card(Rank.Seven, Suit.Diamond),
        Card(Rank.Seven, Suit.Spades)
      )

      HandEvaluator.evaluateHand(cards) shouldBe
        HandValue(HandRank.ThreeOfAKind, Vector(7, 14, 13))

    "return correct kickers for straight" in:
      val cards = Vector(
        Card(Rank.Seven, Suit.Spades),
        Card(Rank.Nine, Suit.Hearts),
        Card(Rank.Five, Suit.Clubs),
        Card(Rank.Eight, Suit.Diamond),
        Card(Rank.Six, Suit.Spades)
      )

      HandEvaluator.evaluateHand(cards) shouldBe
        HandValue(HandRank.Straight, Vector(9))

    "return correct kickers for ace-low straight" in:
      val cards = Vector(
        Card(Rank.Ace, Suit.Spades),
        Card(Rank.Two, Suit.Hearts),
        Card(Rank.Three, Suit.Clubs),
        Card(Rank.Four, Suit.Diamond),
        Card(Rank.Five, Suit.Spades)
      )

      HandEvaluator.evaluateHand(cards) shouldBe
        HandValue(HandRank.Straight, Vector(5))

    "return correct kickers for flush" in:
      val cards = Vector(
        Card(Rank.Nine, Suit.Spades),
        Card(Rank.Ace, Suit.Spades),
        Card(Rank.Four, Suit.Spades),
        Card(Rank.Jack, Suit.Spades),
        Card(Rank.Seven, Suit.Spades)
      )

      HandEvaluator.evaluateHand(cards) shouldBe
        HandValue(HandRank.Flush, Vector(14, 11, 9, 7, 4))

    "return correct kickers for full house" in:
      val cards = Vector(
        Card(Rank.Ace, Suit.Spades),
        Card(Rank.Five, Suit.Hearts),
        Card(Rank.Ace, Suit.Clubs),
        Card(Rank.Five, Suit.Diamond),
        Card(Rank.Five, Suit.Spades)
      )

      HandEvaluator.evaluateHand(cards) shouldBe
        HandValue(HandRank.FullHouse, Vector(5, 14))

    "return correct kickers for four of a kind" in:
      val cards = Vector(
        Card(Rank.Eight, Suit.Spades),
        Card(Rank.Ace, Suit.Hearts),
        Card(Rank.Eight, Suit.Clubs),
        Card(Rank.Eight, Suit.Diamond),
        Card(Rank.Eight, Suit.Hearts)
      )

      HandEvaluator.evaluateHand(cards) shouldBe
        HandValue(HandRank.FourOfAKind, Vector(8, 14))

    "return correct kickers for straight flush" in:
      val cards = Vector(
        Card(Rank.Five, Suit.Spades),
        Card(Rank.Six, Suit.Spades),
        Card(Rank.Seven, Suit.Spades),
        Card(Rank.Eight, Suit.Spades),
        Card(Rank.Nine, Suit.Spades)
      )

      HandEvaluator.evaluateHand(cards) shouldBe
        HandValue(HandRank.StraightFlush, Vector(9))

    "return 1 when first hand has a stronger rank" in:
      val first  = HandValue(HandRank.Flush, Vector(14, 11, 9, 7, 4))
      val second = HandValue(HandRank.Straight, Vector(14))
      HandEvaluator.compare(first, second) shouldBe 1

    "return -1 when second hand has a stronger rank" in:
      val first  = HandValue(HandRank.OnePair, Vector(14, 13, 9, 5))
      val second = HandValue(HandRank.TwoPair, Vector(5, 2, 14))
      HandEvaluator.compare(first, second) shouldBe -1

    "return 1 when first hand has a higher kicker" in:
      val first  = HandValue(HandRank.OnePair, Vector(14, 13, 9, 5))
      val second = HandValue(HandRank.OnePair, Vector(14, 12, 9, 5))
      HandEvaluator.compare(first, second) shouldBe 1

    "return -1 when second hand has a higher kicker" in:
      val first  = HandValue(HandRank.OnePair, Vector(14, 12, 9, 5))
      val second = HandValue(HandRank.OnePair, Vector(14, 13, 9, 5))
      HandEvaluator.compare(first, second) shouldBe -1

    "compare later kickers when previous values are equal" in:
      val first  = HandValue(HandRank.OnePair, Vector(14, 13, 9, 5))
      val second = HandValue(HandRank.OnePair, Vector(14, 13, 8, 5))
      HandEvaluator.compare(first, second) shouldBe 1

    "return 0 when both hands are equal" in:
      val first  = HandValue(HandRank.OnePair, Vector(14, 13, 9, 5))
      val second = HandValue(HandRank.OnePair, Vector(14, 13, 9, 5))
      HandEvaluator.compare(first, second) shouldBe 0

    "return -1 when first kicker vector is shorter" in:
      val first  = HandValue(HandRank.OnePair, Vector(14, 13))
      val second = HandValue(HandRank.OnePair, Vector(14, 13, 9))
      HandEvaluator.compare(first, second) shouldBe -1

    "return 1 when second kicker vector is shorter" in:
      val first  = HandValue(HandRank.OnePair, Vector(14, 13, 9))
      val second = HandValue(HandRank.OnePair, Vector(14, 13))
      HandEvaluator.compare(first, second) shouldBe 1

    "prefer a stronger hand rank over higher kickers" in:
      val first  = HandValue(HandRank.OnePair, Vector(2, 9, 7, 4))
      val second = HandValue(HandRank.HighCard, Vector(14, 13, 11, 8, 5))
      HandEvaluator.compare(first, second) shouldBe 1
