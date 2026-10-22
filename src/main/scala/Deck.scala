object Deck:
  def full: Vector[Card] =
    for
      rank <- ranks
      suit <- suits
    yield Card(rank, suit)

  private val ranks = Rank.values.toVector

  private val suits = Suit.values.toVector

  def shuffled: Vector[Card] =
    scala.util.Random.shuffle(Deck.full)

  // def test(): Unit = {
  // for rank <- ranks do
  //     for suit<-suits do
  //         println(s"$rank of $suit")
  // println(Deck.shuffled)
  // }
