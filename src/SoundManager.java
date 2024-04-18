import javazoom.jl.player.advanced.*;
import javazoom.jl.decoder.JavaLayerException;

public class SoundManager {
    public void playSound(AdvancedPlayer p, String filePath){
        try {
            p = new AdvancedPlayer(getClass().getResourceAsStream(filePath));
            p.play();
        } catch (JavaLayerException ex) {
            ex.printStackTrace();
        }
    }
    
}
