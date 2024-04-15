import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Blackjack {
    //! Card Class
    private class Card{
        //! Card Variable
        String value;
        String type;
        //! Card constructor
        Card(String value, String type){
            this.value = value;
            this.type = type;
        }
        //! Hàm chuyển đổi giá trị của deck về kiểu String
        public String toString(){
            return value + "-" + type;
        }
        //! Hàm lấy giá trị của bài 
        public int getValue(){
            if("AJQK".contains(value)){ //* value from A, J, Q, K */
                if(value == "A"){
                    return 11;
                }
                return 10;
            }
            return Integer.parseInt(value); //* value from 2 - 10 */
        }
        //! Hàm kiểm tra lá bài có phải lá A hay không
        public boolean isAce(){
            return value == "A";
        }

        //! Hàm lấy đường dẫn tương đối của ảnh
        public String getImagePath(){
            return "./cards/" + toString() + ".png";
        }

    }

    //! Khai báo các obj liên quan đến các thư viện ở đầu
    //*  Dùng ArrayList để lưu trữ các lá bài vào trong deck
    ArrayList<Card> deck;
    //*  Tạo 1 obj Random để random các lá bài trong deck 
    Random random = new Random();

    //! Dealer Card
    Card hiddenCard;
    ArrayList<Card> dealerHand;
    private int dealerSum;
    private int dealerAceCount;

    //! Player Card
    ArrayList<Card> playerHand;
    private int playerSum;
    private int playerAceCount;

    //! Window
    public static final int boardWidth = 1000;
    public static final int boardHeight = 650;

    //! Image Sizes
    public static final int cardWidth = 110;
    public static final int cardHeight = 154;

    //! Create frame using JFrame object
    JFrame frame = new JFrame("Play Blackjack");
    //! Panel Drawing
    JPanel startPanel = new JPanel() {
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            try{
                Image JackCard = new ImageIcon(getClass().getResource("./cards/J-D.png")).getImage();
                Image AceCard = new ImageIcon(getClass().getResource("./cards/A-S.png")).getImage();
                g.drawImage(AceCard, 360, 320, cardWidth, cardHeight, null );
                g.drawImage(JackCard, 500, 320, cardWidth, cardHeight, null );
            }catch(Exception e){
                e.printStackTrace();
            }
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.setColor(Color.WHITE);
            g.drawString("Blackjack Game", 327, 259);
        }
    };
    JPanel gamePanel = new JPanel() {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            try {
                //! Draw hiddencard
                Image hiddenCardImg = new ImageIcon(getClass().getResource("./cards/BACK.png")).getImage();
                if(!stayButton.isEnabled()){
                    hiddenCardImg = new ImageIcon(
                    getClass().getResource(hiddenCard.getImagePath())).getImage();
                    g.setFont(new Font("Arial", Font.PLAIN, 18));
                    g.setColor(Color.WHITE);
                    g.drawString("DEALER:" + dealerSum, 80, 199);
                }
                g.drawImage(hiddenCardImg, 20, 20, cardWidth, cardHeight, null);
                

                //! Draw dealer's hand
                
                for(int i = 0; i < dealerHand.size(); i++){
                    Card card = dealerHand.get(i);
                    Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                    g.drawImage(cardImg, cardWidth + 25 + (cardWidth + 5)*i, 20, cardWidth, cardHeight, null);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            //! Draw player's hand
            for(int i = 0; i < playerHand.size(); i++){
                Card card = playerHand.get(i);
                Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                g.drawImage(cardImg, 20 + (cardWidth + 5)*i, 320, cardWidth, cardHeight, null);
                g.setFont(new Font("Arial", Font.PLAIN, 18));
                g.setColor(Color.WHITE);
                g.drawString("PLAYER:" + playerSum, 80, 499);
            }
            
            if(!stayButton.isEnabled()){
                dealerSum = reduceDealerAce();
                playerSum = reducePlayerAce();
                // System.out.println("STAY:");
                // System.out.println(dealerSum);
                // System.out.println(playerSum);

                String message = "";
                if(playerSum > 21 && dealerSum > 21){
                    message = "You and Dealer both bust!";
                }
                else if(dealerSum > 21){
                    message = "You Win!";
                }
                else if(playerSum > 21){
                    message = "You Lose!";
                }
                else if(playerSum == dealerSum){
                    message = "Tie!";
                }
                else if (playerSum > dealerSum) {
                    message = "You Win!";
                }
                else if(playerSum < dealerSum){
                    message = "You Lose!";
                }
                

                g.setFont(new Font("Arial", Font.PLAIN, 30));
                g.setColor(Color.WHITE);
                g.drawString(message, 500, 240);
            }

        }
    };
    JPanel buttonPanel = new JPanel();
    JPanel playPanel = new JPanel();
    JButton playGame = new JButton("Play");
    JButton newGame = new JButton("New Game");
    JButton hitButton = new JButton("Hit");
    JButton stayButton = new JButton("Stay");
    //Constructor
    Blackjack(){
        
        //startGame();
        
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocation(0, 0);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        startPanel.setLayout(new BorderLayout());
        startPanel.setBackground(new Color(53, 101, 77));
        frame.add(startPanel);
        
        playGame.setFocusable(false);
        playGame.setEnabled(true);
        playPanel.add(playGame);
        startPanel.add(playPanel, BorderLayout.SOUTH);
        
        
        newGame.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                hitButton.setEnabled(true);
                stayButton.setEnabled(true);
                startGame();
                gamePanel.repaint();
            }
        });

        playGame.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                startGame();
                startPanel.setVisible(false);
                playGame.setVisible(false);
                newGame.setFocusable(false);
                newGame.setEnabled(false);
                newGame.setBackground(Color.gray);
                buttonPanel.add(newGame);
                hitButton.setFocusable(false);
                hitButton.setBackground(Color.green);
                buttonPanel.add(hitButton);
                stayButton.setFocusable(false);
                stayButton.setBackground(Color.red);
                buttonPanel.add(stayButton);
                frame.add(buttonPanel, BorderLayout.SOUTH);
                    
                gamePanel.setLayout(new BorderLayout());
                gamePanel.setBackground(new Color(53, 101, 77));
                frame.add(gamePanel);

                frame.repaint();
            }
        });
        hitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                Card card = deck.remove(deck.size()-1);
                playerSum += card.getValue();
                playerAceCount += card.isAce() ? 1 : 0;
                // System.out.println(playerSum);
                playerHand.add(card);
                if(reducePlayerAce() > 21){
                    hitButton.setEnabled(false);
                }

                gamePanel.repaint();
            }
        });

        stayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                hitButton.setEnabled(false);
                stayButton.setEnabled(false);
                newGame.setEnabled(true);
                while(dealerSum < 17){
                    Card card = deck.remove(deck.size()-1);
                    dealerSum += card.getValue();
                    dealerAceCount += card.isAce() ? 1 : 0;
                    dealerHand.add(card);
                }
                gamePanel.repaint();
            }
        });

        gamePanel.repaint();
    }

    //Game Method
    public void startGame(){
        //! Deck started
        buildDeck();
        shuffleDeck();

        //! Dealer
        dealerHand = new ArrayList<Card>();
        dealerSum= 0;
        dealerAceCount = 0;

        hiddenCard = deck.remove(deck.size()-1); //* Remove card at last index*/
        dealerSum += hiddenCard.getValue();
        dealerAceCount += hiddenCard.isAce() ? 1 : 0;

        Card card = deck.remove(deck.size()-1);
        dealerSum += card.getValue();
        dealerAceCount += card.isAce() ? 1 : 0;
        dealerHand.add(card);

        // System.out.println("DEALER:");
        // System.out.println(hiddenCard);
        // System.out.println(dealerHand);
        // System.out.println(dealerSum);
        // System.out.println(dealerAceCount);

        //! Player
        playerHand = new ArrayList<Card>();
        playerSum = 0;
        playerAceCount = 0;
        for(int i = 0; i < 2; i++){
            card = deck.remove(deck.size()-1);
            playerSum += card.getValue();
            playerAceCount += card.isAce() ? 1 : 0;
            playerHand.add(card);
        }

        // System.out.println("PLAYER:");
        // System.out.println(playerHand);
        // System.out.println(playerSum);
        // System.out.println(playerAceCount);

    }

    public void buildDeck(){
        deck = new ArrayList<Card>();
        String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        /*
         * C:
         * D: Diamonds
         * H: Hearts
         * S: Spades
         */
        String[] types= {"C", "D", "H", "S"};

        for(int i = 0; i < types.length; i++){
            for(int j = 0; j < values.length; j++){
                Card card = new Card(values[j], types[i]);
                deck.add(card);
            }
        }

        // System.out.println("BUILD DECK:");
        // System.out.println(deck);
    }
    public void shuffleDeck(){
        for(int i = 0; i < deck.size(); i++){
            int j = random.nextInt(deck.size());
            Card currentCard = deck.get(i);
            Card randomCard = deck.get(j);
            deck.set(i, randomCard);
            deck.set(j, currentCard);
        }

        // System.out.println("AFTER SHUFFLE:");
        // System.out.println(deck);
    }

    public int reducePlayerAce(){
        while (playerSum > 21 && playerAceCount > 0) {
            playerSum -= 10;
            playerAceCount -= 1;
        }
        return playerSum;
    }

    public int reduceDealerAce(){
        while (dealerSum > 21 && dealerAceCount > 0) {
            dealerSum -= 10;
            dealerAceCount -= 1;
        }
        return dealerSum;
    }

}
