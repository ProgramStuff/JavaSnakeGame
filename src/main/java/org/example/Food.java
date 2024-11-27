package org.example;

import java.awt.*;
import java.util.Random;

import static org.example.GamePanel.SCREEN_WIDTH;
import static org.example.GamePanel.SCREEN_HEIGHT;
import static org.example.GamePanel.UNIT_SIZE;

/**
 * Title:
 * Filename: Food
 * Author: Jordan Kelsey
 * Date: 2024-11-19
 * Purpose:
 */

public class Food {
    private int foodX;
    private int foodY;
    private Color color;

    public Food(Color color) {
        this.color = color;
    }

    public void newFood(){
        Random random = new Random();
        foodX = random.nextInt((int)(SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        foodY = random.nextInt((int)(SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    public Color getColor() {
        return color;
    }

    public int getFoodX() {
        return foodX;
    }

    public int getFoodY() {
        return foodY;
    }
}
