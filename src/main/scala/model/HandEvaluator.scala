package model

object HandEvaluator:

  def evaluate(cards: Vector[Card]): HandRank =
    require(cards.size == 5, "Exactly 5 cards required")

    val rankCounts = cards.groupBy(_.rank).view.mapValues(_.size).toMap
    val counts = rankCounts.values.toVector.sorted(using Ordering.Int.reverse)

    val isFlush = cards.map(_.suit).distinct.size == 1

    val ranks = cards.map(_.rank.ordinal + 2).distinct.sorted

    val isStraight =
      ranks.size == 5 && (ranks.last - ranks.head == 4 || ranks == Vector(2, 3,
        4, 5, 14))

    if isStraight && isFlush then HandRank.StraightFlush
    else if isFlush then HandRank.Flush
    else if isStraight then HandRank.Straight
    else
      counts match
        case Vector(4, 1)       => HandRank.FourOfAKind
        case Vector(3, 2)       => HandRank.FullHouse
        case Vector(3, 1, 1)    => HandRank.ThreeOfAKind
        case Vector(2, 2, 1)    => HandRank.TwoPair
        case Vector(2, 1, 1, 1) => HandRank.OnePair
        case _                  => HandRank.HighCard

    // if rankCounts.values.count(_== 2) == 1 then
    //     HandRank.OnePair
    // else
    //     HandRank.HighCard
  def evaluateHand(cards: Vector[Card]): HandValue =
    val handRank = evaluate(cards)

    val values       = cards.map(_.rank.ordinal + 2)
    val sortedValues = values.sorted(using Ordering.Int.reverse)

    val rankCounts = values.groupBy(identity).view.mapValues(_.size).toMap

    val sortedGroups = rankCounts.toVector.sortBy { case (rank, count) =>
      (-count, -rank)
    }

    val groupedValues = sortedGroups.map(_._1)

    val straightHigh =
      if values.sorted == Vector(2, 3, 4, 5, 14) then 5
      else values.max

    val kickers = handRank match
      case HandRank.Straight | HandRank.StraightFlush => Vector(straightHigh)

      case HandRank.Flush | HandRank.HighCard => sortedValues

      case _ => groupedValues

    HandValue(handRank, kickers)

  def compare(first: HandValue, second: HandValue): Int =
    if first.rank.ordinal > second.rank.ordinal then 1
    else if first.rank.ordinal < second.rank.ordinal then -1
    else compareKickers(first.kickers, second.kickers)

  private def compareKickers(
      first: Vector[Int],
      second: Vector[Int]
  ): Int =
    if first.isEmpty && second.isEmpty then 0
    else if first.isEmpty then -1
    else if second.isEmpty then 1
    else if first.head > second.head then 1
    else if first.head < second.head then -1
    else compareKickers(first.tail, second.tail)
