package org.example;

import java.awt.*;
import java.util.Random;

import static org.example.GamePanel.*;
import static org.example.GamePanel.UNIT_SIZE;

/*
 * Title:
 * Filename: Obstacle
 * Author: Jordan Kelsey
 * Date: 2024-11-26
 * Purpose:
 */


/**
 * Obstacle class
 * To create a wall in the snake game
 */
public class Obstacle {
    private int obstacleX;
    private int obstacleY;
    private Color color;

    /**
     * @param color to set the obstacle color
     */
    public Obstacle(Color color) {
        this.color = color;
    }

    /**
     * Creates a new object and randomly assigns the location
     */
    public void newObstacle(){
        Random random = new Random();
        obstacleX = random.nextInt((int)(SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        obstacleY = random.nextInt((int)(SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    /**
     * @return the object color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @return the obstacles X coordinate
     */
    public int getObstacleX() {
        return obstacleX;
    }

    /**
     * @return the obstacles Y coordinate
     */
    public int getObstacleY() {
        return obstacleY;
    }

    /**
     * @param obstacleX sets the obstacles X coordinate
     */
    public void setObstacleX(int obstacleX) {
        this.obstacleX = obstacleX;
    }

    /**
     * @param obstacleY sets the obstacles Y coordinate
     */
    public void setObstacleY(int obstacleY) {
        this.obstacleY = obstacleY;
    }
}
