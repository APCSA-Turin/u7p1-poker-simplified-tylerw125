package com.example.project;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;


public class Player{
    private ArrayList<Card> hand;
    private ArrayList<Card> allCards; //the current community cards + hand
    String[] suits  = Utility.getSuits();
    String[] ranks = Utility.getRanks();

    public Player(){
        hand = new ArrayList<>();
        allCards = new ArrayList<>();
    }

    public ArrayList<Card> getHand(){return hand;}
    public ArrayList<Card> getAllCards(){return allCards;}

    public void addCard(Card c) {
        hand.add(c);
        allCards.add(c);
    }
    // CARD OUTPUT DETERMINERS
    public boolean isRoyalFlush() {
        return isStraightFlush() && hasAce();
    }

    private boolean hasAce() {
        for (Card card : allCards) {
            if (card.getRank().equals("A")) return true;
        }
        return false;
    }

    public boolean isStraightFlush() {
        return isFlush(findSuitFrequency()) && isStraight();
    }

    public boolean isFourOfAKind(ArrayList<Integer> rankFrequency) {
        for (int count : rankFrequency) {
            if (count == 4) {
                System.out.println("DEBUG: Four of a Kind detected.");
                return true;
            }
        }
        return false;
    }

    public boolean isFullHouse(ArrayList<Integer> rankFrequency) {
        boolean hasThree = false, hasTwo = false;
        for (int count : rankFrequency) {
            if (count == 3) hasThree = true;
            if (count == 2) hasTwo = true;
        }
        return hasThree && hasTwo; // Ensures at least one Three of a Kind
    }

    public boolean isFlush(ArrayList<Integer> suitFrequency) {
        for (int count : suitFrequency) {
            if (count >= 5) {
                System.out.println("DEBUG: Flush detected.");
                return true;
            }
        }
        return false;
    }

    public boolean isStraight() {
    int consecutiveCount = 0;
    int lastRank = -1;

    for (Card card : allCards) {
        int rankValue = Utility.getRankValue(card.getRank());

        if (lastRank == rankValue - 1) {
            consecutiveCount++;
            if (consecutiveCount == 4) return true; // Need 5 consecutive cards
        } else if (lastRank != rankValue) {
            consecutiveCount = 0;
        }

        lastRank = rankValue;
    }
    return false;
}

    public boolean isThreeOfAKind(ArrayList<Integer> rankFrequency) {
        return rankFrequency.contains(3);
    }
    public boolean isTwoPair(ArrayList<Integer> rankFrequency) {
        int pairCount = 0;
        for (int count : rankFrequency) {
            if (count == 2) {
                pairCount++;
            }
        }

        System.out.println("DEBUG: Pair count for Two Pair: " + pairCount);
        return pairCount == 2;
    }


    public boolean isPair(ArrayList<Integer> rankFrequency) {
        for (int count : rankFrequency) {
            if (count == 2) return true; // Ensures only one pair is detected
        }
        return false;
    }


    public boolean isHighCard() {
        sortAllCards();
        for (Card card : hand ) {
            if (card == (allCards.get(4))) {
            return true;
            }
        }
        return false;
    }
    public String playHand(ArrayList<Card> communityCards) {
        allCards.clear(); // Reset allCards to avoid duplicate additions
        allCards.addAll(hand);
        allCards.addAll(communityCards);
        sortAllCards();

        ArrayList<Integer> rankFrequency = findRankingFrequency();
        ArrayList<Integer> suitFrequency = findSuitFrequency();

        // Debugging output
        System.out.println("DEBUG: Rank Frequency = " + rankFrequency);
        System.out.println("DEBUG: Suit Frequency = " + suitFrequency);

        if (isRoyalFlush()) return "Royal Flush";
        if (isStraightFlush()) return "Straight Flush";
        if (isFourOfAKind(rankFrequency)) return "Four of a Kind";
        if (isFullHouse(rankFrequency)) return "Full House";
        if (isFlush(suitFrequency) && !isStraight()) return "Flush";
        if (isStraight()) return "Straight";
        if (isThreeOfAKind(rankFrequency)) return "Three of a Kind";
        if (isTwoPair(rankFrequency)) return "Two Pair";
        if (isPair(rankFrequency)) return "A Pair";
        if (isHighCard()) return "High Card";

        return "Nothing";
    }
    
    
    public void sortAllCards() {
        int n = allCards.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (Utility.getRankValue(allCards.get(j).getRank()) < 
                    Utility.getRankValue(allCards.get(minIndex).getRank())) {
                    minIndex = j;
                }
            }
            Card temp = allCards.get(minIndex);
            allCards.set(minIndex, allCards.get(i));
            allCards.set(i, temp);
        }
        
        System.out.println("DEBUG: Sorted cards: " + allCards);
    }
    
    

    public ArrayList<Integer> findRankingFrequency() {
        ArrayList<Integer> frequency = new ArrayList<>(Collections.nCopies(13, 0));
    
        for (Card card : allCards) {
            int rankValue = Utility.getRankValue(card.getRank());
            if (rankValue >= 2 && rankValue <= 14) {
                int index = rankValue - 2;
                frequency.set(index, frequency.get(index) + 1);
            } else {
                System.out.println("ERROR: Invalid rank detected: " + card.getRank());
            }
        }
        
        System.out.println("DEBUG: Rank Frequency Array: " + frequency);
        return frequency;
    }
    
    
    public ArrayList<Integer> findSuitFrequency(){
        ArrayList<Integer> frequency = new ArrayList<>(Collections.nCopies(4, 0)); 
        for (Card card : allCards) {
            int suitIndex = Arrays.asList(suits).indexOf(card.getSuit());
            frequency.set(suitIndex, frequency.get(suitIndex) + 1);
        }
        return frequency; 
    }
    @Override
    public String toString(){
        return hand.toString();
    }
}
