import javax.swing.JOptionPane;

import methods.utils.PlayerNamePrompt;

public class App {
    public static void main(String[] args) {
        Blackjack blackjack = new Blackjack();
        String playerName = PlayerNamePrompt.getPlayerName(null);
        if (playerName != null && !playerName.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Have fun with Blackjack, " + playerName, "Welcome <3",1);
                    System.out.println("Player: " + playerName);
                }
    }
}
