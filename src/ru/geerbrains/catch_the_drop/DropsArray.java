package ru.geerbrains.catch_the_drop;


import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class DropsArray {
    private ArrayList<Drop> dList;
    int current_drop;
    DropsArray(){
        dList = new ArrayList<>();
    }

    void addDrop(float left, float top, float speed) throws IOException{
        dList.add(new Drop(left, top, speed));
    }

    void addDrop(int fieldWidth, float speed) {
        dList.add(new Drop(fieldWidth, speed));
    }

    void clearAll() {
        dList.clear();
    }

    void removeDrop(int index) {
        try {
            dList.remove(index);
        } catch (IndexOutOfBoundsException e) {}
    }

    boolean isHitAny(int x, int y){
        current_drop = 0;
        for (Drop d : dList) {
            if (d.isHit(x, y)) {
                return true;
            }
            current_drop++;
        }
        return false;
    }

    void updatePosition(float delta_time){
        for (Drop d : dList) {
            d.updatePosition(delta_time);
        }
    }

    boolean isAnyOut(int wHeight) {
        for (Drop d : dList) {
            if (d.isOutOfWindow(wHeight)) return true;
        }
        return false;
    }

    void drawSelf(Graphics g) {
        for (Drop d : dList) {
            d.drawSelf(g);
        }
    }
}
