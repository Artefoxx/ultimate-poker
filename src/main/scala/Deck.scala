object Deck{
    def full: Vector[Card] = 
        for 
            rank <- ranks 
            suit <- suits 
        yield Card(rank, suit)
    


    private val ranks = Vector("2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace")

    private val suits = Vector("Hearts", "Diamonds", "Spades", "Clubs")

    def shuffled: Vector[Card] = 
        scala.util.Random.shuffle(Deck.full)


    // def test(): Unit = {
    // for rank <- ranks do 
    //     for suit<-suits do
    //         println(s"$rank of $suit")
    // println(Deck.shuffled)   
    //}     

}


