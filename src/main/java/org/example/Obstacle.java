package org.example;

import java.awt.*;
import java.util.Random;

import static org.example.GamePanel.*;
import static org.example.GamePanel.UNIT_SIZE;

/**
 * Title:
 * Filename: Obstacle
 * Author: Jordan Kelsey
 * Date: 2024-11-26
 * Purpose:
 */
public class Obstacle {
    private int obstacleX;
    private int obstacleY;
    private Color color;

    public Obstacle(Color color) {
        this.color = color;
    }

    public void newObstacle(){
        Random random = new Random();
        obstacleX = random.nextInt((int)(SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        obstacleY = random.nextInt((int)(SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    public Color getColor() {
        return color;
    }

    public int getObstacleX() {
        return obstacleX;
    }

    public int getObstacleY() {
        return obstacleY;
    }
}
