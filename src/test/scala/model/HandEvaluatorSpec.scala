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

