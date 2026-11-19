package model

case class BettingState(
    playerBalance: Int,
    ante: Int,
    blind: Int,
    playBet: Int,
    folded: Boolean
)

object BettingState:
  def initial: BettingState =
    BettingState(
      playerBalance = 980,
      ante = 10,
      blind = 10,
      playBet = 0,
      folded = false
    )
