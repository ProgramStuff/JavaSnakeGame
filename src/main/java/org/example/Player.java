package org.example;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static org.example.GamePanel.*;

/**
 * Title:
 * Filename: Player
 * Author: Jordan Kelsey
 * Date: 2024-11-26
 * Purpose:
 */

public class Player {

    int bodyParts = 6;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    char direction = 'R';
    int appleEaten = 0;
    int currentPlayer;
    MyKeyAdapter playerKeys = new MyKeyAdapter();

    // Constructor to allow creation of multiple players
    public Player(int player) {
        this.currentPlayer = player;
    }

    // Move snake
    public void move(){
        for(int i = bodyParts; i > 0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch (direction){
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    public void checkApple(Apple apple){
        if((x[0] == apple.getFoodX()) && (y[0] == apple.getFoodY())){
            bodyParts++;
            appleEaten++;
            apple.newFood();
        }
    }

    public Boolean checkPoison(PoisonFood poison){
        return (x[0] != poison.getFoodX()) || (y[0] != poison.getFoodY());

    }

    public Boolean checkObstacle(Obstacle wall){
            return (x[0] != wall.getObstacleX()) || (y[0] != wall.getObstacleY());
    }

    public Boolean checkCollisions(){
        // Check body collision
        for (int i = bodyParts; i > 0; i--){
            if((x[0] == x[i]) && (y[0] == y[i])){
                return false;
            }
        }
        //Check left wall
        if(x[0] < 0){
            return false;
        }
        //Check right wall
        if(x[0] > SCREEN_WIDTH - 25){
            return false;
        }
        //Check top wall
        if(y[0] < 0){
            return false;
        }
        //Check bottom wall
        if(y[0] > SCREEN_HEIGHT - 25){
            return false;
        }

        return true;
    }

    // Set the key pressed events for each player
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            if (currentPlayer == 1) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        if (direction != 'R') {
                            direction = 'L';
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (direction != 'L') {
                            direction = 'R';
                        }
                        break;
                    case KeyEvent.VK_UP:
                        if (direction != 'D') {
                            direction = 'U';
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (direction != 'U') {
                            direction = 'D';
                        }
                        break;
                }
            }else if (currentPlayer == 2){
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A:
                        if (direction != 'R') {
                            direction = 'L';
                        }
                        break;
                    case KeyEvent.VK_D:
                        if (direction != 'L') {
                            direction = 'R';
                        }
                        break;
                    case KeyEvent.VK_W:
                        if (direction != 'D') {
                            direction = 'U';
                        }
                        break;
                    case KeyEvent.VK_S:
                        if (direction != 'U') {
                            direction = 'D';
                        }
                        break;
                }
            }
        }
    }
}
