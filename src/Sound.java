import javazoom.jl.player.advanced.*;


public class Sound {
    public void playSound(AdvancedPlayer p, String filePath){
        try {
            p = new AdvancedPlayer(getClass().getResourceAsStream(filePath));
            p.play();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
}
