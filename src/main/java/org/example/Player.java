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

// The head of the snake is not staying on the body and the poison and wall are not being detected
    // The snake hits the left wall but not the bottom??
public class Player {

    int bodyParts = 6;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    char direction = 'R';
    int appleEaten = 0;
    MyKeyAdapter playerKeys = new MyKeyAdapter();


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
        if((x[0] == poison.getFoodX()) && (y[0] == poison.getFoodY())){
            return false;
        }
        return true;
    }

    public Boolean checkObstacle(Obstacle wall){
        if((x[0] == wall.getObstacleX()) && (y[0] == wall.getObstacleY())){
            return false;
        }
        return true;
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
//        if(!running){
//            timer.stop();
//        }
        return true;
    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
