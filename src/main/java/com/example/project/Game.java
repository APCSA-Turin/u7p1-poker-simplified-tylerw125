package com.example.project;
import java.util.ArrayList;


public class Game {
    public static String determineWinner(Player p1, Player p2, String p1Hand, String p2Hand, ArrayList<Card> communityCards) {
        int p1Rank = Utility.getHandRanking(p1Hand);
        int p2Rank = Utility.getHandRanking(p2Hand);
    
        if (p1Rank > p2Rank) return "Player 1 wins!";
        if (p2Rank > p1Rank) return "Player 2 wins!";
    
        // If hands are tied compare the highest HAND card of each player
        p1.sortAllCards();
        p2.sortAllCards();
    
        // Extract the two individual cards each player drew initially
        Card p1Card1 = p1.getHand().get(0);
        Card p1Card2 = p1.getHand().get(1);
        Card p2Card1 = p2.getHand().get(0);
        Card p2Card2 = p2.getHand().get(1);
    
        int p1Highest = Math.max(Utility.getRankValue(p1Card1.getRank()), Utility.getRankValue(p1Card2.getRank()));
        int p1Lowest  = Math.min(Utility.getRankValue(p1Card1.getRank()), Utility.getRankValue(p1Card2.getRank()));
    
        int p2Highest = Math.max(Utility.getRankValue(p2Card1.getRank()), Utility.getRankValue(p2Card2.getRank()));
        int p2Lowest  = Math.min(Utility.getRankValue(p2Card1.getRank()), Utility.getRankValue(p2Card2.getRank()));
    
        // Compare the hand cards
        if (p1Highest > p2Highest) return "Player 1 wins!";
        if (p2Highest > p1Highest) return "Player 2 wins!";
    
        // If highest hand card1 are the same, compare the second hand card
        if (p1Lowest > p2Lowest) return "Player 1 wins!";
        if (p2Lowest > p1Lowest) return "Player 2 wins!";
    
        return "Tie!";
    }
    

    public static void play() {
        Deck deck = new Deck();
    
        // Test Game 1: Debugging Four of a Kind vs. Full House
        Player p1 = new Player();
        Player p2 = new Player();
    
        p1.addCard(new Card("A", "♠"));
        p1.addCard(new Card("A", "♦"));
    
        p2.addCard(new Card("K", "♠"));
        p2.addCard(new Card("K", "♦"));
    
        ArrayList<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card("A", "♣"));
        communityCards.add(new Card("A", "♥"));
        communityCards.add(new Card("K", "♣")); // Player 1 Four of a Kind Player 2 Full House
    
        System.out.println("\n=== Running Debug Test: Four of a Kind vs. Full House ===");
        String p1Result = p1.playHand(communityCards);
        String p2Result = p2.playHand(communityCards);
        System.out.println("Player 1 Hand: " + p1Result);
        System.out.println("Player 2 Hand: " + p2Result);
        System.out.println("Result: " + determineWinner(p1, p2, p1Result, p2Result, communityCards));
        System.out.println("====================================================\n");
    
        // Test Game 2: Debugging Straight vs. Straight Flush
        p1 = new Player();
        p2 = new Player();
    
        p1.addCard(new Card("5", "♠"));
        p1.addCard(new Card("6", "♠"));
    
        p2.addCard(new Card("5", "♦"));
        p2.addCard(new Card("6", "♦"));
    
        communityCards.clear();
        communityCards.add(new Card("7", "♠"));
        communityCards.add(new Card("8", "♠"));
        communityCards.add(new Card("9", "♠"));
    
        System.out.println("\n=== Running Debug Test: Straight Flush vs. Straight ===");
        p1Result = p1.playHand(communityCards);
        p2Result = p2.playHand(communityCards);
        System.out.println("Player 1 Hand: " + p1Result);
        System.out.println("Player 2 Hand: " + p2Result);
        System.out.println("Result: " + determineWinner(p1, p2, p1Result, p2Result, communityCards));
        System.out.println("====================================================\n");
    }
    

    public static void main(String[] args) {
        play();
    }
}