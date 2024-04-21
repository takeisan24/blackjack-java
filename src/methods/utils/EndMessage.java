package methods.utils;

public class EndMessage {
    public static int playerScore = 0;
    public static String MessageSend(String msg, int player, int dealer){
        if(player > 21 && dealer > 21){
                    playerScore += 0;
                    msg = "You and Dealer both bust!";
                }
                else if(dealer > 21){
                    playerScore += 10;
                    msg = "You Win!";
                }
                else if(player > 21){
                    playerScore -= 5;
                    msg = "You Lose!";
                }
                else if(player == dealer){
                    playerScore += 2;
                    msg = "Tie!";
                }
                else if (player > dealer) {
                    playerScore += 10;
                    msg = "You Win!";
                }
                else if(player < dealer){
                    playerScore -= 5;
                    msg = "You Lose!";
                }
        return msg;        
    }
    public int getPlayerScore(){
        return playerScore;
    }
}
