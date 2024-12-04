package org.example;

import java.awt.*;
import java.util.Random;

import static org.example.GamePanel.SCREEN_WIDTH;
import static org.example.GamePanel.SCREEN_HEIGHT;
import static org.example.GamePanel.UNIT_SIZE;

/*
 * Title:
 * Filename: Food
 * Author: Jordan Kelsey
 * Date: 2024-11-19
 * Purpose:
 */

/**
 * Food parent class to create and randomly place an object
 */
public class Food {
    private int foodX;
    private int foodY;
    private Color color;

    /**
     * @param color Sets the color of the food
     */
    public Food(Color color) {
        this.color = color;
    }

    /**
     * Create a new food object in random location
     */
    public void newFood(){
        Random random = new Random();
        foodX = random.nextInt((int)(SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        foodY = random.nextInt((int)(SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    /**
     * @return the food color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @return the food X coordinate
     */
    public int getFoodX() {
        return foodX;
    }

    /**
     * @return the food Y coordinate
     */
    public int getFoodY() {
        return foodY;
    }

    /**
     * @param foodX changes the food X coordinate
     */
    public void setFoodX(int foodX) {
        this.foodX = foodX;
    }

    /**
     * @param foodY changes the food Y coordinate
     */
    public void setFoodY(int foodY) {
        this.foodY = foodY;
    }
}
