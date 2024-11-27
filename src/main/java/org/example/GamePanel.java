package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * Title:
 * Filename: GamePanel
 * Author: Jordan Kelsey
 * Date: 2024-11-12
 * Purpose:
 */


public class GamePanel extends JPanel implements ActionListener{

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT / UNIT_SIZE);
    static final int DELAY = 75;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;
    int appleEaten = 0;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    // Food types
    Apple apple = new Apple(Color.YELLOW);
    PoisonFood poison = new PoisonFood(new Color(87, 9, 176));

    // Obstacle

    Obstacle wall = new Obstacle(Color.GRAY);

    // Player
    Player p1 = new Player();



    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(p1.playerKeys);
        startGame();
    }

    public void startGame(){
        apple.newFood();
        poison.newFood();
        wall.newObstacle();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        if(running) {
            // Adds a grid for visualization can be deleted
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }

            // Create Apple
            g.setColor(apple.getColor());
            g.fillOval(apple.getFoodX(), apple.getFoodY(), UNIT_SIZE, UNIT_SIZE);

            // Create poison food
            g.setColor(poison.getColor());
            g.fillRoundRect(poison.getFoodX(), poison.getFoodY(), UNIT_SIZE, UNIT_SIZE, 10, 10);

            // Create Obstacle
            g.setColor(wall.getColor());
            g.fillRect(wall.getObstacleX(), wall.getObstacleY(), UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < p1.bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.GRAY);
                    g.fillRect(p1.x[i], p1.y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(p1.x[i], p1.y[i], UNIT_SIZE, UNIT_SIZE);
                }
                g.setColor(Color.RED);
                g.setFont(new Font("Ink Free", Font.BOLD, 40));
                FontMetrics metrics = getFontMetrics(g.getFont());
                g.drawString("Score: " + p1.appleEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + p1.appleEaten))/2, g.getFont().getSize());
            }
        }else{
            gameOver(g);
        }

    }


//    public void move(){
//        for(int i = bodyParts; i > 0; i--){
//            x[i] = x[i-1];
//            y[i] = y[i-1];
//        }
//
//        switch (direction){
//            case 'U':
//                y[0] = y[0] - UNIT_SIZE;
//                break;
//            case 'D':
//                y[0] = y[0] + UNIT_SIZE;
//                break;
//            case 'L':
//                x[0] = x[0] - UNIT_SIZE;
//                break;
//            case 'R':
//                x[0] = x[0] + UNIT_SIZE;
//                break;
//        }
//    }
//
//    public void checkApple(){
//        if((x[0] == apple.getFoodX()) && (y[0] == apple.getFoodY())){
//            bodyParts++;
//            appleEaten++;
//            apple.newFood();
//        }
//    }
//
//    public void checkPoison(){
//        if((x[0] == poison.getFoodX()) && (y[0] == poison.getFoodY())){
//            running = false;
//        }
//    }
//
//    public void checkObstacle(){
//        if((x[0] == wall.getObstacleX()) && (y[0] == wall.getObstacleY())){
//            running = false;
//        }
//    }

//    public void checkCollisions(){
//        // Check body collision
//        for (int i = bodyParts; i > 0; i--){
//            if((x[0] == x[i]) && (y[0] == y[i])){
//                running = false;
//            }
//        }
//        //Check left wall
//        if(x[0] < 0){
//            running = false;
//        }
//        //Check right wall
//        if(x[0] > SCREEN_WIDTH){
//            running = false;
//        }
//        //Check top wall
//        if(y[0] < 0){
//            running = false;
//        }
//        //Check bottom wall
//        if(y[0] > SCREEN_HEIGHT){
//            running = false;
//        }
//        if(!running){
//            timer.stop();
//        }
//    }

    public void gameOver(Graphics g){
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + p1.appleEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + p1.appleEaten))/2, g.getFont().getSize());

        // Game over text
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running){
            p1.move();
            p1.checkApple(apple);
            this.running = p1.checkPoison(poison);
            this.running = p1.checkObstacle(wall);
            running = p1.checkCollisions();
            //Returns a false value but isn't stopping the program
            System.out.println(p1.checkPoison(poison));
        }
        repaint();
    }




}
