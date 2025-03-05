package com.example.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

public class PokerGameUI {
    private JFrame frame;
    private JButton dealButton, resetButton;
    private JTextArea gameLog;
    private JPanel cardPanel;
    private Game game;

    public PokerGameUI() {
        game = new Game();
        frame = new JFrame("Poker Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());
        
        gameLog = new JTextArea();
        gameLog.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(gameLog);
        frame.add(scrollPane, BorderLayout.CENTER);

        cardPanel = new JPanel();
        frame.add(cardPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        dealButton = new JButton("Deal Hand");
        resetButton = new JButton("Reset Game");
        
        dealButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playGame();
            }
        });
        
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });

        buttonPanel.add(dealButton);
        buttonPanel.add(resetButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void playGame() {
        Player player1 = new Player();
        Player player2 = new Player();
        Deck deck = new Deck();
        
        player1.addCard(deck.drawCard());
        player1.addCard(deck.drawCard());
        player2.addCard(deck.drawCard());
        player2.addCard(deck.drawCard());
        
        ArrayList<Card> communityCards = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            communityCards.add(deck.drawCard());
        }
        
        String p1Result = player1.playHand(communityCards);
        String p2Result = player2.playHand(communityCards);
        
        String winner = Game.determineWinner(player1, player2, p1Result, p2Result, communityCards);
        
        updateUI(player1, player2, communityCards, p1Result, p2Result, winner);
    }

    private void updateUI(Player p1, Player p2, ArrayList<Card> communityCards, String p1Result, String p2Result, String winner) {
        gameLog.append("Player 1 Hand: " + p1Result + "\n");
        gameLog.append("Player 2 Hand: " + p2Result + "\n");
        gameLog.append("Winner: " + winner + "\n\n");
        
        cardPanel.removeAll();
        for (Card card : communityCards) {
            cardPanel.add(new JLabel(card.toString()));
        }
        cardPanel.revalidate();
        cardPanel.repaint();
    }

    private void resetGame() {
        gameLog.setText("");
        cardPanel.removeAll();
        cardPanel.revalidate();
        cardPanel.repaint();
    }

    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(PokerGameUI::new);
    }
}

