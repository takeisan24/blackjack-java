public class App {
    public static void main(String[] args) {
        Blackjack blackjack = new Blackjack();
        BackgroundMusic bgmMusic = new BackgroundMusic("D:\\blackjack-java\\src\\sounds\\background-music.mp3");
        bgmMusic.play();
        System.out.println("The game is running, please wait for a second! Enjoy Blackjack <3");
    }
}
