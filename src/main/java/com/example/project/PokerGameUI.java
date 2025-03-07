package com.example.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.image.Kernel;
import java.awt.image.ConvolveOp;

public class PokerGameUI {
    private JFrame frame;
    private JButton dealButton, resetButton;
    private JPanel communityCardPanel, player1Panel, player2Panel, centerPanel;
    private JLabel scoreLabel, resultLabel;
    private Game game;
    
    private static final String CARD_IMAGE_PATH = "src/main/java/com/example/project/JPEG/";

    public PokerGameUI() {
        game = new Game();
        frame = new JFrame("Poker Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.DARK_GRAY);

        Font customFont = new Font("Verdana", Font.BOLD, 14);

        communityCardPanel = new JPanel();
        communityCardPanel.setBackground(Color.DARK_GRAY);
        frame.add(communityCardPanel, BorderLayout.NORTH);

        player1Panel = new JPanel();
        player1Panel.setBackground(Color.DARK_GRAY);
        frame.add(player1Panel, BorderLayout.WEST);

        player2Panel = new JPanel();
        player2Panel.setBackground(Color.DARK_GRAY);
        frame.add(player2Panel, BorderLayout.EAST);

        centerPanel = new JPanel();
        centerPanel.setBackground(Color.DARK_GRAY);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        scoreLabel = new JLabel("Score: 0 - 0");
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 30)); // Bigger font
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        resultLabel = new JLabel("Result: ");
        resultLabel.setForeground(Color.WHITE);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 14));
        resultLabel.setAlignmentX(Panel.CENTER_ALIGNMENT);

        dealButton = new JButton("Draw");
        resetButton = new JButton("Reset");

        dealButton.addActionListener(e -> playGame());
        resetButton.addActionListener(e -> resetGame());

        centerPanel.add(resultLabel);  // Add result label above scoreboard
        centerPanel.add(scoreLabel);
        centerPanel.add(dealButton);
        centerPanel.add(resetButton);
        frame.add(centerPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void playGame() {
        player1Panel.setBackground(Color.DARK_GRAY);
        player2Panel.setBackground(Color.DARK_GRAY);

        Player player1 = new Player();
        Player player2 = new Player();
        Deck deck = new Deck();

        player1.addCard(deck.drawCard());
        player1.addCard(deck.drawCard());
        player2.addCard(deck.drawCard());
        player2.addCard(deck.drawCard());

        ArrayList<Card> communityCards = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            communityCards.add(deck.drawCard());
        }

        String p1Result = player1.playHand(communityCards);
        String p2Result = player2.playHand(communityCards);
        String winner = Game.determineWinner(player1, player2, p1Result, p2Result, communityCards);

        updateUI(player1, player2, communityCards, p1Result, p2Result, winner);
    }

    private void updateUI(Player p1, Player p2, ArrayList<Card> communityCards, String p1Result, String p2Result, String winner) {
        // Clear existing components from the panels
        player1Panel.removeAll();
        player2Panel.removeAll();
        communityCardPanel.removeAll();
        centerPanel.removeAll(); // This is important for removing previous components from the center panel
    
        // Set panel backgrounds to match
        player1Panel.setBackground(Color.DARK_GRAY);
        player2Panel.setBackground(Color.DARK_GRAY);
        communityCardPanel.setBackground(Color.DARK_GRAY);
    
        // Create the panels for the cards
        JPanel player1Cards = new JPanel();
        player1Cards.setBackground(Color.DARK_GRAY);
        for (Card card : p1.getHand()) {
            addCardToPanel(card, player1Cards);
        }
    
        JPanel player2Cards = new JPanel();
        player2Cards.setBackground(Color.DARK_GRAY);
        for (Card card : p2.getHand()) {
            addCardToPanel(card, player2Cards);
        }

        for (Card card : communityCards) {
            addCardToPanel(card, communityCardPanel);
        }
    
        // Labels
        JLabel p1HandLabel = new JLabel("Player 1 Hand: " + p1Result);
        p1HandLabel.setForeground(Color.WHITE);
        p1HandLabel.setFont(new Font("Arial", Font.BOLD, 12));
    
        JLabel p2HandLabel = new JLabel("Player 2 Hand: " + p2Result);
        p2HandLabel.setForeground(Color.WHITE);
        p2HandLabel.setFont(new Font("Arial", Font.BOLD, 12));
    
        // Organization
        JPanel player1Container = new JPanel(new BorderLayout());
        player1Container.setBackground(Color.DARK_GRAY);
        player1Container.add(player1Cards, BorderLayout.CENTER);
        player1Container.add(p1HandLabel, BorderLayout.SOUTH);
    
        JPanel player2Container = new JPanel(new BorderLayout());
        player2Container.setBackground(Color.DARK_GRAY);
        player2Container.add(player2Cards, BorderLayout.CENTER);
        player2Container.add(p2HandLabel, BorderLayout.SOUTH);
    
        player1Panel.add(player1Container);
        player2Panel.add(player2Container);
        resultLabel.setText("Result: " + winner);
        updateScoreboard(winner);
    
        JPanel playerHandsPanel = new JPanel();
        playerHandsPanel.setLayout(new GridLayout(1, 2));
        playerHandsPanel.setBackground(Color.DARK_GRAY);
        playerHandsPanel.add(player1Panel);
        playerHandsPanel.add(player2Panel);

        centerPanel.add(Box.createVerticalStrut(10)); // Space before scoreboard
        centerPanel.add(resultLabel);  
        centerPanel.add(Box.createVerticalStrut(10)); // Space after result label
        centerPanel.add(scoreLabel);
        centerPanel.add(Box.createVerticalStrut(10)); // Space after scoreboard
        centerPanel.add(communityCardPanel);
        centerPanel.add(Box.createVerticalStrut(10)); // Space between community and player hands
        centerPanel.add(playerHandsPanel);
        centerPanel.add(Box.createVerticalStrut(10)); // Space before buttons
        centerPanel.add(dealButton);
        centerPanel.add(resetButton);
    
        // Refresh the UI
        frame.revalidate();
        frame.repaint();
    }
    
    private void addCardToPanel(Card card, JPanel panel) {
        String suitLetter = convertSuitToLetter(card.getSuit());
        String cardImagePath = CARD_IMAGE_PATH + card.getRank() + suitLetter + ".png";

        try {
            BufferedImage cardImage = ImageIO.read(new File(cardImagePath));
            
            // Image resizing (CARDS WERE HUGE)
            int cardWidth = 100;
            int cardHeight = 140;
            Image resizedImage = cardImage.getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH);

            // Apply drop shadow effect
            BufferedImage shadowedCardImage = applyDropShadow(resizedImage);
            ImageIcon cardIcon = new ImageIcon(shadowedCardImage);

            JLabel cardLabel = new JLabel(cardIcon);
            cardLabel.setBackground(Color.DARK_GRAY);
            panel.add(cardLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String convertSuitToLetter(String suit) {
        switch (suit) {
            case "♠": return "S";
            case "♥": return "H";
            case "♣": return "C";
            case "♦": return "D";
            default: return ""; // Unicode error handling
        }
    }

    private void resetGame() {
        // Reset player panels and community cards
        player1Panel.removeAll();
        player2Panel.removeAll();
        communityCardPanel.removeAll();
        
        // Reset result label and score
        resultLabel.setText("Result: ");
        scoreLabel.setText("Score: 0 - 0");
        
        // Refresh UI
        frame.revalidate();
        frame.repaint();
    }
    

    private void updateScoreboard(String winner) {
        // Get current scoreboard
        String currentScore = scoreLabel.getText();
    
        currentScore = currentScore.replace("Score: ", "");
        String[] scoreParts = currentScore.split(" - ");

        int p1Score = Integer.parseInt(scoreParts[0].trim());
        int p2Score = Integer.parseInt(scoreParts[1].trim());
    
        // Update the score based on the winner
        if (winner.equals("Player 1 wins!")) {
            p1Score++;
        } else if (winner.equals("Player 2 wins!")) {
            p2Score++;
        }
    
        // Set the updated score on the scoreboard
        scoreLabel.setText("Score: " + p1Score + " - " + p2Score);
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PokerGameUI::new);
    }

    // Apply drop shadow to JLabel
    private JLabel applyDropShadow(JLabel label) {
        label.setForeground(Color.WHITE);
        BufferedImage shadowImage = applyShadow(createImageFromLabel(label), 10, Color.BLACK, 0.5f);
        ImageIcon shadowIcon = new ImageIcon(shadowImage);
        label.setIcon(shadowIcon);
        return label;
    }

    private BufferedImage applyDropShadow(Image image) {
        BufferedImage bufferedImage = toBufferedImage(image);
        return applyShadow(bufferedImage, 5, Color.BLACK, 0.5f);
    }

    private BufferedImage toBufferedImage(Image image) {
        // Convert Image to BufferedImage
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return bufferedImage;
    }

    public static BufferedImage generateBlur(BufferedImage imgSource, int size, Color color, float alpha) {
        float[] matrix = new float[size * size];
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = 1.0f / matrix.length; // Averaging filter for blur
        }

        Kernel kernel = new Kernel(size, size, matrix);
        ConvolveOp convolve = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        BufferedImage imgBlur = convolve.filter(imgSource, null);

        // Now apply the color overlay
        Graphics2D g2 = imgBlur.createGraphics();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, alpha));
        g2.setColor(color);
        g2.fillRect(0, 0, imgBlur.getWidth(), imgBlur.getHeight());
        g2.dispose();

        return imgBlur;
    }

    public static BufferedImage applyShadow(BufferedImage imgSource, int size, Color color, float alpha) {
        // Use the version that accepts the BufferedImage, width, and height
        BufferedImage result = createCompatibleImage(imgSource, imgSource.getWidth() + (size * 2), imgSource.getHeight() + (size * 2));
        Graphics2D g2d = result.createGraphics();
        g2d.drawImage(generateShadow(imgSource, size, color, alpha), size, size, null);
        g2d.drawImage(imgSource, 0, 0, null);
        g2d.dispose();
        return result;
    }

    public static BufferedImage generateShadow(BufferedImage imgSource, int size, Color color, float alpha) {
        int imgWidth = imgSource.getWidth() + (size * 2);
        int imgHeight = imgSource.getHeight() + (size * 2);

        BufferedImage imgMask = createCompatibleImage(imgWidth, imgHeight);
        Graphics2D g2 = imgMask.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawImage(imgSource, size, size, null);
        g2.dispose();

        BufferedImage imgGlow = generateBlur(imgMask, size, color, alpha);
        return imgGlow;
    }

    public static BufferedImage createCompatibleImage(int width, int height) {
        return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    public static BufferedImage createCompatibleImage(BufferedImage image, int width, int height) {
        return new BufferedImage(width, height, image.getTransparency());
    }

    // Convert JLabel text to an image for shadow application
    private BufferedImage createImageFromLabel(JLabel label) {
        int width = label.getPreferredSize().width;
        int height = label.getPreferredSize().height;

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        label.paint(g2d);
        g2d.dispose();
        return img;
    }
}

// https://stackoverflow.com/questions/14655643/java-create-shadow-effect-on-image
// https://www.oracle.com/java/technologies/a-swing-architecture.html
// https://stackoverflow.com/questions/11071735/java-image-convolution
// https://www.informit.com/articles/article.aspx?p=1013851&seqNum=5
