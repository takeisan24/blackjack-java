package methods.utils;

public class EndMessage {
    public static String MessageSend(String msg, int player, int dealer){
        if(player > 21 && dealer > 21){
                    msg = "You and Dealer both bust!";
                }
                else if(dealer > 21){
                    msg = "You Win!";
                }
                else if(player > 21){
                    msg = "You Lose!";
                }
                else if(player == dealer){
                    msg = "Tie!";
                }
                else if (player > dealer) {
                    msg = "You Win!";
                }
                else if(player < dealer){
                    msg = "You Lose!";
                }
        return msg;        
    }
}
