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
    }

    public ArrayList<Card> getHand(){return hand;}
    public ArrayList<Card> getAllCards(){return allCards;}

    public void addCard(Card c){
        hand.add(c);
        allCards.add(c);
    }

    public String playHand(ArrayList<Card> communityCards) {     
        allCards.clear();
        allCards.addAll(hand);
        allcards.addAll(communityCards);
        sortCards();
        ArrayList<Integer> rankFrequency = findRankingFrequency();
        ArrayList<Integer> suitFrequency = findSuitFrequency();
        if (isRoyalFlush()) return "Royal Flush";
        if (isStraightFlush()) return "Straight Flush";
        if (isFourOfAKind()) return "Four of a kind";
        if (isFullHouse()) return "Full House";
        if (isFlush()) return "Flush";
        if (isStraight()) return "Straight";
        if (isThreeOFAKind()) return "Three of a kind";
        if (isTwoPair()) return "Two Pairs";
        if (isPair()) return "Pair";
        if (isHighCard()) return "High Card";

        return "Nothing";
    }

    public void SortCards() {
        allCards.sort(Comparator.comparingInt(eachCard -> Utility.getRankValue(eachCard.getRank())));
    } 

    public ArrayList<Integer> findRankingFrequency(){
        ArrayList<Integer> frequency = new ArrayList<>(Collections.nCopies(13, 0));
        for (Card card : allCards) {
            int rankValue = Utility.getRankValue(card.getRank()) - 2;
            frequency.set(rankValue, frequency.get(rankValue) + 1);
        }
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
