package ru.geerbrains.catch_the_drop;


import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Drop {
    private static Image drop;
    private float drop_left;
    private float drop_top;
    private static float drop_v;

    boolean isHit(int x, int y){
        float drop_right = drop_left + this.getWidth();
        float drop_bottom = drop_top + this.getHeight();
        return (x <= (int) drop_right) && (x >= (int) drop_left) && (y >= (int) drop_top) && (y <= (int) drop_bottom);
    }

    private int getWidth(){
        return drop.getWidth(null);
    }

    private int getHeight(){
        return drop.getHeight(null);
    }

    void updatePosition(float delta_time) {
        drop_top += drop_v * delta_time;
    }

    boolean isOutOfWindow(int wHeight){
        return drop_top >= wHeight - 10;
    }

    Drop(float left, float top, float speed) throws IOException{
        drop_left = left;
        drop_top = top;
        drop_v = speed;
        drop = ImageIO.read(Game.class.getResourceAsStream("drop.png"));
    }

    Drop(int fieldWidth, float speed){
        drop_left = (int)(Math.random() * fieldWidth - (drop.getWidth(null) / 2));
        drop_top = -100;
        drop_v = speed;
    }

    void drawSelf(Graphics g) {
        g.drawImage(drop, (int) drop_left, (int) drop_top, null);
    }
}
