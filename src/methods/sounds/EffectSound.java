package methods.sounds;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.*;
public class EffectSound {
//     private String filePath;
//     public EffectSound(String filePath){
//         this.filePath = filePath;
        
//     }
//     public void playSound(){
//         try {
//             AdvancedPlayer p = new AdvancedPlayer(getClass().getResourceAsStream(filePath));
//             p.play();
//         } catch (JavaLayerException ex) {
//             ex.printStackTrace();
//         }
//     }
    



    private Player player;
    private String bgmFilePath;

    public EffectSound(String bgmFilePath){
        this.bgmFilePath = bgmFilePath;
    }

    public void playSound(){
        try {
            FileInputStream fis = new FileInputStream(bgmFilePath);
            BufferedInputStream bis = new BufferedInputStream(fis);
            player = new Player(bis);

            Thread playerThread = new Thread(() -> {
                try {
                    player.play();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            });
            playerThread.start();
        } catch (FileNotFoundException | JavaLayerException e) {
            e.printStackTrace();
        }
    }
    public void stopSound(){
        if(player != null){
            player.close();
        }
    }
}