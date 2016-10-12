package ru.geerbrains.catch_the_drop;


import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.Scanner;

public class GameOver {
    private static Image image;
    private static boolean over;

    boolean getGameOver(){
        return over;
    }

    void setGameOver(boolean b){
        over = b;
    }

    GameOver() throws IOException{
        image = ImageIO.read(Game.class.getResourceAsStream("game_over.png"));
    }

    void drawSelf(Graphics g) {
        g.drawImage(image, 280, 120, null);
    }

    int getOldScore() {
        try {
            Scanner in = new Scanner(new FileReader("score.hi"));
            return Integer.parseInt(in.next());
        } catch (IOException ioe){}
        return 0;
    }

    void updateScore(int score) {

        if (getOldScore() < score)
            writeScore(score);
    }

    void writeScore(int score) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("score.hi"), "utf-8"))) {
            writer.write("" + score);
        } catch (IOException ioe) {
        }
    }
}
