package methods.sounds;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.*;


public class BackgroundMusic {
    private Player player;
    private String bgmFilePath;

    public BackgroundMusic(String bgmFilePath){
        this.bgmFilePath = bgmFilePath;
    }

    public void play(){
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
    public void stop(){
        if(player != null){
            player.close();
        }
    }
}