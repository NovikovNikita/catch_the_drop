package ru.geerbrains.catch_the_drop;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Game extends JFrame {


    private static Game game_window;
    private static long last_frame_time;
    private static Image background;
    private static DropsArray dropsArray;
    private static GameOver gameOver;
    private static int speed = 200;
    private static int hits = 0;
    private static int score = 0;
    private static int level = 1;


    public static void main(String[] args) throws IOException {
        GameField game_field = new GameField();
        gameOver = new GameOver();

        dropsArray = new DropsArray();
        dropsArray.addDrop(200, -100, speed);

        background = ImageIO.read(Game.class.getResourceAsStream("background.jpg"));
        game_window = new Game();
        game_window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game_window.setTitle("Level: " + level + "  Score: " + score + "  Drops clicked: " + hits + "  Highest Score is: " + gameOver.getOldScore());
        game_window.setLocation(200, 100);
        game_window.setSize(906, 478);
        game_window.setResizable(false);
        last_frame_time = System.nanoTime();
        game_window.add(game_field);
        game_window.setVisible(true);
        game_field.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!gameOver.getGameOver()) {
                    int x = e.getX();
                    int y = e.getY();
                    if (dropsArray.isHitAny(x, y)) {
                        hits++;
                        score += level;
                        game_window.setTitle("Level: " + level + "  Score: " + score + "  Drops clicked: " + hits + "  Highest Score is: " + gameOver.getOldScore());
                        if (hits % 10 == 0) {
                            level++;
                            speed = 200;
                            dropsArray.addDrop(game_field.getWidth(), speed);
                        }
                        dropsArray.removeDrop(dropsArray.current_drop);
                        speed += 10;
                        dropsArray.addDrop(game_field.getWidth(), speed);
                    }
                } else {
                    dropsArray.clearAll();
                    level = 1;
                    score = 0;
                    hits = 0;
                    speed = 200;
                    game_window.setTitle("Level: " + level + "  Score: " + score + "  Drops clicked: " + hits + "  Highest Score is: " + gameOver.getOldScore());
                    dropsArray.addDrop(game_field.getWidth(), speed);
                    gameOver.setGameOver(false);
                }
            }
        });
    }

    private static void onRepaint(Graphics g){
        long current_time = System.nanoTime();
        float delta_time = (current_time - last_frame_time) * 0.000000001f;
        last_frame_time = current_time;

        g.drawImage(background, 0, 0, null);
        dropsArray.updatePosition(delta_time);
        dropsArray.drawSelf(g);

        if (!gameOver.getGameOver()) {
            if (dropsArray.isAnyOut(game_window.getHeight())) {
                dropsArray.removeDrop(dropsArray.current_drop);
                gameOver.setGameOver(true);
            }
        }
        else {
//            gameOver.writeScore(score);
            gameOver.updateScore(score);
            gameOver.drawSelf(g);
        }

    }

    private static class GameField extends JPanel{

        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            onRepaint(g);
            repaint();
        }
    }
}
