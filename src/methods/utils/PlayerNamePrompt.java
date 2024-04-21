package methods.utils;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class PlayerNamePrompt {
    public static String getPlayerName(JFrame parent) {
        return JOptionPane.showInputDialog(parent, "Your name:", "Chào mừng", JOptionPane.PLAIN_MESSAGE);
    }
}
