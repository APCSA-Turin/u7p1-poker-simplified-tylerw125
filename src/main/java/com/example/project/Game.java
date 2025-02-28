package com.example.project;
import java.util.ArrayList;


public class Game{
    public static String determineWinner(Player p1, Player p2,String p1Hand, String p2Hand,ArrayList<Card> communityCards){
        int p1Rank = Utility.getHandRanking(p1Hand);
        int p2Rank = Utility.getHandRanking(p2Hand);
        if (p1Rank > p2Rank) {
            return "Player 1 wins!";
        } else if (p2Rank > p1Rank) {
            return "Player 2 wins!";
        } else {
            p1.SortCards();
            p2.SortCards();
            for (int i = p1.getAllCards().size() - 1; i >= 0; i--) {
                int p1CardRank = Utility.getRankValue(p1.getAllCards().get(i).getRank());
                int p2CardRank = Utility.getRankValue(p2.getAllCards().get(i).getRank());
                if (p1CardRank > p2CardRank) {
                    return "Player 1 wins!";
                } else if (p2CardRank > p1CardRank) {
                    return "Player 2 wins!";
                }
                return "Tie!";
                }
        }
        return "Error";
    }

    public static void play(){ //simulate card playing
    
    }
        
        

}