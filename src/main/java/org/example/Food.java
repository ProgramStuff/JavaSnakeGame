package org.example;

import javax.swing.*;
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

public class Food extends JPanel {
    int appleEaten = 0;
    int appleX;
    int appleY;

    public void newApple(){
        Random random = new Random();
        appleX = random.nextInt((int)(SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }



    public boolean checkApple(int x[], int y[]){
        boolean ballEaten = false;
        if((x[0] == appleX) && (y[0] == appleY)){
            appleEaten++;
            newApple();
            ballEaten = true;
        }
        return ballEaten;
    }

    public int getAppleX() {
        return appleX;
    }

    public int getAppleY() {
        return appleY;
    }
}
