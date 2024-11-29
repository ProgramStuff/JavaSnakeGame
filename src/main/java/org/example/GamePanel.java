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
    boolean running = false;
    Timer timer;
    Random random;
    // Food types
    Apple apple = new Apple(Color.YELLOW);
    PoisonFood poison = new PoisonFood(new Color(87, 9, 176));

    // Obstacle

    Obstacle wall = new Obstacle(Color.GRAY);

    // Player
    Player p1 = new Player(1);
    Player p2 = new Player(2);
    boolean player1Alive = true;
    boolean player2Alive = true;




    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(p1.playerKeys);
        this.addKeyListener(p2.playerKeys);
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

            // Display player scores
            g.setColor(Color.RED);
            g.setFont(new Font("Ink Free", Font.BOLD, 20));
            g.drawString("Player 1 Score: " + p1.appleEaten, 10, g.getFont().getSize());

            g.setColor(Color.RED);
            g.setFont(new Font("Ink Free", Font.BOLD, 20));
            g.drawString("Player 2 Score: " + p2.appleEaten, 430, g.getFont().getSize());

            // Create the snakes body parts
            for (int i = 0; i < p1.bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.GRAY);
                    g.fillRect(p1.x[i], p1.y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(p1.x[i], p1.y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            for (int i = 0; i < p2.bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.GRAY);
                    g.fillRect(p2.x[i], p2.y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(37, 171, 222));
                    g.fillRect(p2.x[i], p2.y[i], UNIT_SIZE, UNIT_SIZE);
                }

            }
        }else{
            gameOver(g);
        }

    }

    public void gameOver(Graphics g){
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 30));
        // Display both players score after game
        g.drawString("Player 1 Score: " + p1.appleEaten, 10, g.getFont().getSize());

        g.drawString("Player 2 Score: " + p2.appleEaten, 330, g.getFont().getSize());


        // Game over text
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.running){
            // If players lose set player alive to false and set the snake body size to 0
            p1.move();
            p1.checkApple(apple);
            player1Alive = p1.checkPoison(poison) && p1.checkObstacle(wall) && p1.checkCollisions();
            if (!player1Alive){
                p1.bodyParts = 0;
            }

            p2.move();
            p2.checkApple(apple);
            player2Alive = p2.checkPoison(poison) && p2.checkObstacle(wall) && p2.checkCollisions();
            if (!player2Alive){
                p2.bodyParts = 0;
            }

            // Stop the game if both players lose
            running = player1Alive || player2Alive;

            if(!this.running){
                timer.stop();
            }
        }

        repaint();
    }




}
