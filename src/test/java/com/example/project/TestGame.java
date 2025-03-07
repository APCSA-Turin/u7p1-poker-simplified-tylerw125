package com.example.project;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

public class TestGame {

    @Test // Test case where Player 1 wins with a pair
    public void testAPair_Player1Wins() {
        Player player1 = new Player();
        Player player2 = new Player();
        
        player1.addCard(new Card("A", "♠"));
        player1.addCard(new Card("K", "♠"));
        
        player2.addCard(new Card("9", "♠"));
        player2.addCard(new Card("10", "♠"));
        
        ArrayList<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card("7", "♦"));
        communityCards.add(new Card("8", "♠"));
        communityCards.add(new Card("A", "♣"));
        
        String p1Result = player1.playHand(communityCards);
        String p2Result = player2.playHand(communityCards);
        
        String winner = Game.determineWinner(player1, player2, p1Result, p2Result, communityCards);
        
        assertEquals("Player 1 wins!", winner); // Player 1 should win with a pair of Aces
    }

    @Test // Test case where Player 2 wins with higher high card
    public void testHighCard_Player2Wins() {
        Player player1 = new Player();
        Player player2 = new Player();
        
        player1.addCard(new Card("7", "♠"));
        player1.addCard(new Card("8", "♠"));
        
        player2.addCard(new Card("9", "♠"));
        player2.addCard(new Card("10", "♠"));
        
        ArrayList<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card("Q", "♦"));
        communityCards.add(new Card("J", "♣"));
        communityCards.add(new Card("A", "♠"));
        
        String p1Result = player1.playHand(communityCards);
        String p2Result = player2.playHand(communityCards);
        
        String winner = Game.determineWinner(player1, player2, p1Result, p2Result, communityCards);
        
        assertEquals("Player 2 wins!", winner); // Player 2 should win (high card 10)
    }

    @Test // Test case where it's a tie with equal high cards
    public void testHighCard_TieGame() {
        Player player1 = new Player();
        Player player2 = new Player();

        player1.addCard(new Card("K", "♠"));
        player1.addCard(new Card("Q", "♠"));
        
        player2.addCard(new Card("K", "♠"));
        player2.addCard(new Card("Q", "♠"));
        
        ArrayList<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card("7", "♦"));
        communityCards.add(new Card("8", "♠"));
        communityCards.add(new Card("A", "♣"));
        
        String p1Result = player1.playHand(communityCards);
        String p2Result = player2.playHand(communityCards);
        
        String winner = Game.determineWinner(player1, player2, p1Result, p2Result, communityCards);
        
        assertEquals("Tie!", winner); // It's a tie between both players
    }

    @Test // Test case where Player 1 wins with higher straight
    public void testStraight_Player1Wins() {
        Player player1 = new Player();
        Player player2 = new Player();
        
        // Player 1 has a straight: 7,8,9,10,J
        player1.addCard(new Card("10", "♠"));
        player1.addCard(new Card("J", "♠"));
        
        // Player 2 has a straight: 5, 6, 7, 8, 9
        player2.addCard(new Card("5", "♠"));
        player2.addCard(new Card("6", "♠"));
        
        ArrayList<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card("7", "♦"));
        communityCards.add(new Card("8", "♠"));
        communityCards.add(new Card("9", "♥"));
        
        String p1Result = player1.playHand(communityCards);
        String p2Result = player2.playHand(communityCards);
        
        String winner = Game.determineWinner(player1, player2, p1Result, p2Result, communityCards);
        
        assertEquals("Player 1 wins!", winner); // Player 1 should win with the higher straight (7,8,9,10,J)
    }

    @Test // Test case where Player 1 wins with higher Full House
    public void testFullHouseDraw_Player1Wins() {
        Player player1 = new Player();
        Player player2 = new Player();
        
        player1.addCard(new Card("A", "♠"));
        player1.addCard(new Card("A", "♣"));
        
        player2.addCard(new Card("8", "♠"));
        player2.addCard(new Card("8", "♣"));
        
        ArrayList<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card("7", "♦"));
        communityCards.add(new Card("7", "♥"));
        communityCards.add(new Card("7", "♠"));
        
        String p1Result = player1.playHand(communityCards);
        String p2Result = player2.playHand(communityCards);
        
        String winner = Game.determineWinner(player1, player2, p1Result, p2Result, communityCards);
        
        assertEquals("Player 1 wins!", winner); // Player 1 wins with a higher Full House
    }

    @Test // Test case where Player 1 wins with Full House vs Three of a Kind
    public void testFullHouseVsThreeOfAKind_Player1Wins() {
        Player player1 = new Player();
        Player player2 = new Player();
        
        player1.addCard(new Card("7", "♠"));
        player1.addCard(new Card("7", "♣"));
        
        player2.addCard(new Card("8", "♠"));
        player2.addCard(new Card("3", "♣"));
        
        ArrayList<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card("A", "♦"));
        communityCards.add(new Card("A", "♥"));
        communityCards.add(new Card("A", "♠"));
    
        String p1Result = player1.playHand(communityCards);
        String p2Result = player2.playHand(communityCards);
        
        String winner = Game.determineWinner(player1, player2, p1Result, p2Result, communityCards);
        
        assertEquals("Player 1 wins!", winner); // Player 1 wins with Full House
    }



    @Test // Test case for Tie in Two Pair scenario. Both have a full house
    public void testTwoPair_TieGame() {
        Player player1 = new Player();
        Player player2 = new Player();
        
        player1.addCard(new Card("8", "♠"));
        player1.addCard(new Card("7", "♠"));
        
        player2.addCard(new Card("8", "♠"));
        player2.addCard(new Card("7", "♣"));
        
        ArrayList<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card("8", "♦"));
        communityCards.add(new Card("7", "♠"));
        communityCards.add(new Card("7", "♣"));
        
        String p1Result = player1.playHand(communityCards);
        String p2Result = player2.playHand(communityCards);
        
        String winner = Game.determineWinner(player1, player2, p1Result, p2Result, communityCards);
        
        assertEquals("Tie!", winner); 
    }

    @Test // Test case where Player 1 wins with higher Three of a Kind
    public void testThreeOfAKind_Player1Wins() {
        Player player1 = new Player();
        Player player2 = new Player();
        
        player1.addCard(new Card("A", "♠"));
        player1.addCard(new Card("A", "♣"));
        
        player2.addCard(new Card("K", "♠"));
        player2.addCard(new Card("K", "♣"));
        
        ArrayList<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card("A", "♦"));
        communityCards.add(new Card("K", "♥"));
        communityCards.add(new Card("9", "♠"));
        
        String p1Result = player1.playHand(communityCards);
        String p2Result = player2.playHand(communityCards);
        
        String winner = Game.determineWinner(player1, player2, p1Result, p2Result, communityCards);
        
        assertEquals("Player 1 wins!", winner); 
    }

    @Test //Player 2 wins with Four of a Kind vs Three of a Kind
    public void testFourOfAKindVsThreeOfAKind_Player2Wins() {
        Player player1 = new Player();
        Player player2 = new Player();
        
        // Player 1 has Three of a Kind: 3 Jacks
        player1.addCard(new Card("J", "♠"));
        player1.addCard(new Card("J", "♣"));
        
        // Player 2 has Four of a Kind: 4 Tens
        player2.addCard(new Card("10", "♠"));
        player2.addCard(new Card("10", "♣"));
        
        ArrayList<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card("10", "♦"));
        communityCards.add(new Card("10", "♥"));
        communityCards.add(new Card("J", "♠"));
        
        String p1Result = player1.playHand(communityCards);
        String p2Result = player2.playHand(communityCards);
        
        String winner = Game.determineWinner(player1, player2, p1Result, p2Result, communityCards);
        
        assertEquals("Player 2 wins!", winner); // Player 2 wins with Four of a Kind (Tens)
    }

    @Test //Player 1 wins with Straight Flush
    public void testStraightFlush_Player1Wins() {
        Player player1 = new Player();
        Player player2 = new Player();
        
        // Player 1 has a Straight Flush: 5, 6, 7, 8, 9 of Spades
        player1.addCard(new Card("5", "♠"));
        player1.addCard(new Card("6", "♠"));
        
        // Player 2 has a Flush: 2, 3, 4, 5, 6 of Hearts (but no Straight Flush)
        player2.addCard(new Card("2", "♥"));
        player2.addCard(new Card("3", "♥"));
        
        ArrayList<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card("7", "♠"));
        communityCards.add(new Card("8", "♠"));
        communityCards.add(new Card("9", "♠"));
        
        String p1Result = player1.playHand(communityCards);
        String p2Result = player2.playHand(communityCards);
        
        String winner = Game.determineWinner(player1, player2, p1Result, p2Result, communityCards);
        
        assertEquals("Player 1 wins!", winner); // Player 1 wins with a Straight Flush (5♠, 6♠, 7♠, 8♠, 9♠)
    }

    @Test // Flush Draw test case
    public void testFlushDraw_Player2Wins() {
        Player player1 = new Player();
        Player player2 = new Player();
        
        player1.addCard(new Card("7", "♠"));
        player1.addCard(new Card("10", "♠"));
  
        player2.addCard(new Card("A", "♠"));
        player2.addCard(new Card("3", "♠"));

        
        // Community cards that could help form the flush
        ArrayList<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card("J", "♠")); // Player 1 completes the flush with this card
        communityCards.add(new Card("4", "♠"));
        communityCards.add(new Card("Q", "♠"));
        
        // Player results after playing the hand
        String p1Result = player1.playHand(communityCards);
        String p2Result = player2.playHand(communityCards);
        
        // Determine the winner
        String winner = Game.determineWinner(player1, player2, p1Result, p2Result, communityCards);
        
        // Player 1 should win 
        assertEquals("Player 2 wins!", winner);
    }   
    @Test // A pair in the community cards, find the highest card. 
    public void testPairDrawHighCard_Player2Wins() {
        Player player1 = new Player();
        Player player2 = new Player();
        
        player1.addCard(new Card("7", "♠"));
        player1.addCard(new Card("10", "♠"));
  
        player2.addCard(new Card("A", "♠"));
        player2.addCard(new Card("3", "♠"));

        
        // Community cards that could help form the flush
        ArrayList<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card("J", "♠")); // Player 1 completes the flush with this card
        communityCards.add(new Card("J", "♥"));
        communityCards.add(new Card("Q", "♠"));
        
        // Player results after playing the hand
        String p1Result = player1.playHand(communityCards);
        String p2Result = player2.playHand(communityCards);
        
        // Determine the winner
        String winner = Game.determineWinner(player1, player2, p1Result, p2Result, communityCards);
        
        // Player 1 should win 
        assertEquals("Player 2 wins!", winner);
    }   
}
