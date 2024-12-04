package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
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
    boolean runningLevel2 = false;
    boolean runningLevel3 = false;
    Timer timer;
    Random random;

    Logger logger = new Logger();
    int matchWinner;
    // Food types
    Apple apple = new Apple(Color.YELLOW);


    PoisonFood poison1 = new PoisonFood(new Color(87, 9, 176));
    PoisonFood poison2 = new PoisonFood(new Color(87, 9, 176));
    PoisonFood poison3 = new PoisonFood(new Color(87, 9, 176));
    ArrayList<PoisonFood> poisonApples = new ArrayList<PoisonFood>();

    // Obstacle

    Obstacle wall = new Obstacle(Color.GRAY);
    Obstacle wall2 = new Obstacle(Color.GRAY);
    Obstacle wall3 = new Obstacle(Color.GRAY);
    Obstacle wall4 = new Obstacle(Color.GRAY);



    // Player
    Player p1 = new Player(1);
    Player p2 = new Player(2);
    boolean player1Alive = true;
    boolean player2Alive = true;

    int level = 1;

    // Buttons to quit or restart the game
    Clicklistener clicklistener = new Clicklistener();
    JButton playAgain = new JButton("Play Again");
    JButton quit = new JButton("Quit");
    // Button to begin next level
    JButton nextLevel = new JButton("Next Level");


    GamePanel(){
        random = new Random();

        poisonApples.add(poison1);
        poisonApples.add(poison2);
        poisonApples.add(poison3);
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(p1.playerKeys);
        this.addKeyListener(p2.playerKeys);

        // Adding an action event listener to the buttons
        playAgain.addActionListener(clicklistener);
        quit.addActionListener(clicklistener);
        nextLevel.addActionListener(clicklistener);
        startGame();
    }

      public void startGame(){
        p1.resetPlayer();
        p2.resetPlayer();
        runningLevel2 = false;
        runningLevel3 = false;
        level = 1;
        apple.newFood();
        running = true;

        if (timer != null) {
            timer.stop();
        }
        timer = new Timer(DELAY, this);
        timer.start();
        // When restarting game focus has to be set back to the current container for key events to work
        this.requestFocus();

        this.remove(playAgain);
        this.remove(quit);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if (running){
            draw(g);
        }
        if (runningLevel2){
            drawLevel2(g);
        }
        if (runningLevel3){
            drawLevel3(g);
        }
        if (!running && !runningLevel2 && !runningLevel3){
            gameOver(g);
        }

    }

    public void displayScores(Graphics g){
        // Display player scores
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 20));
        g.drawString("Player 1 Score: " + p1.appleEaten, 10, g.getFont().getSize());

        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 20));
        g.drawString("Player 2 Score: " + p2.appleEaten, 430, g.getFont().getSize());
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


            displayScores(g);

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


            /*** LEVEL 2 ***/
            if (p1.appleEaten >= 1 || p2.appleEaten >= 1 ){
                nextLevel(g);
            }

        }

    }

    public void drawLevel2(Graphics g){
        if(runningLevel2) {

            // Adds a grid for visualization can be deleted
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }

            // Create Apple
            g.setColor(apple.getColor());
            g.fillOval(apple.getFoodX(), apple.getFoodY(), UNIT_SIZE, UNIT_SIZE);


            g.setColor(poison1.getColor());
            g.fillRoundRect(poison1.getFoodX(), poison1.getFoodY(), UNIT_SIZE, UNIT_SIZE, 10, 10);
            g.setColor(poison2.getColor());
            g.fillRoundRect(poison2.getFoodX(), poison2.getFoodY(), UNIT_SIZE, UNIT_SIZE, 10, 10);
            g.setColor(poison3.getColor());
            g.fillRoundRect(poison3.getFoodX(), poison3.getFoodY(), UNIT_SIZE, UNIT_SIZE, 10, 10);

            displayScores(g);

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
            /*** LEVEL 3 ***/
            if (p1.appleEaten >= 2 || p2.appleEaten >= 2 ){
                nextLevel(g);
            }
        }


    }

    public void drawLevel3(Graphics g){
        if(runningLevel3) {

            // Adds a grid for visualization can be deleted
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }

            // Create Apple
            g.setColor(apple.getColor());
            g.fillOval(apple.getFoodX(), apple.getFoodY(), UNIT_SIZE, UNIT_SIZE);


            // Create Obstacle
            g.setColor(wall.getColor());
            g.fillRect(wall.getObstacleX(), wall.getObstacleY(), UNIT_SIZE, UNIT_SIZE);
            g.setColor(wall2.getColor());
            g.fillRect(wall2.getObstacleX(), wall2.getObstacleY(), UNIT_SIZE, UNIT_SIZE);
            g.setColor(wall3.getColor());
            g.fillRect(wall3.getObstacleX(), wall3.getObstacleY(), UNIT_SIZE, UNIT_SIZE);
            g.setColor(wall4.getColor());
            g.fillRect(wall4.getObstacleX(), wall4.getObstacleY(), UNIT_SIZE, UNIT_SIZE);

            g.setColor(poison1.getColor());
            g.fillRoundRect(poison1.getFoodX(), poison1.getFoodY(), UNIT_SIZE, UNIT_SIZE, 10, 10);
            g.setColor(poison2.getColor());
            g.fillRoundRect(poison2.getFoodX(), poison2.getFoodY(), UNIT_SIZE, UNIT_SIZE, 10, 10);
            g.setColor(poison3.getColor());
            g.fillRoundRect(poison3.getFoodX(), poison3.getFoodY(), UNIT_SIZE, UNIT_SIZE, 10, 10);

            displayScores(g);

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
        }


    }

    public void startNextLevel(boolean player1Alive, boolean player2Alive){
        if (timer != null) {
            timer.stop();
        }
        timer = new Timer(DELAY, this);
        timer.start();
        level++;
        if(player1Alive){
            p1.resetPlayer(p1.appleEaten);
        }
        if(player2Alive){
            p2.resetPlayer(p2.appleEaten);
        }
        apple.newFood();
        if(level == 2){
            runningLevel2 = true;
            running = false;
            poison1.newFood();
            poison2.newFood();
            poison3.newFood();
        }

        if (level == 3){
            runningLevel3 = true;
            runningLevel2 = false;
            wall.newObstacle();
            wall2.newObstacle();
            wall3.newObstacle();
            wall4.newObstacle();
        }


        // When restarting game focus has to be set back to the current container for key events to work
        this.requestFocus();
        this.remove(nextLevel);

        revalidate();
        repaint();
    }

    public void nextLevel(Graphics g){
        timer.stop();

        // Game over text
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        if(level + 1 == 2){
            g.drawString("LEVEL 2", (SCREEN_WIDTH - metrics2.stringWidth("LEVEL 2"))/2, SCREEN_HEIGHT/2);
        } else if(level + 1 == 3){
            g.drawString("LEVEL 3", (SCREEN_WIDTH - metrics2.stringWidth("LEVEL 3"))/2, SCREEN_HEIGHT/2);
        }


        nextLevel.setBounds(250, 350 , 100, 30);

        this.add(nextLevel);
        this.revalidate();

    }

    public void gameOver(Graphics g){

        // Use logger to check high score, set high score and display hgihg score
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 30));
        // Display both players score after game
        g.drawString("Player 1 Score: " + p1.appleEaten, 10, g.getFont().getSize());

        g.drawString("Player 2 Score: " + p2.appleEaten, 330, g.getFont().getSize());

        if(p1.appleEaten > p2.appleEaten){
            matchWinner = p1.appleEaten;
        }else {
            matchWinner = p2.appleEaten;
        }

        if(matchWinner > Integer.parseInt(logger.getHighScore())){
            logger.setHighScore(matchWinner);
            // HighScore text
            g.setColor(Color.RED);
            g.setFont(new Font("Ink Free", Font.BOLD, 30));
            g.drawString(STR."New High Score: \{logger.getHighScore()}", 200, 400);
        }else{
            // HighScore text
            g.setColor(Color.RED);
            g.setFont(new Font("Ink Free", Font.BOLD, 30));
            g.drawString(STR."High Score: \{logger.getHighScore()}", 200, 400);
        }




        // Game over text
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);

        playAgain.setBounds(100, 500 , 100, 30);
        quit.setBounds(400, 500 , 100, 30);

        this.add(playAgain);
        this.add(quit);
    }

    // Use actionEvent to create new obstacles

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.running || this.runningLevel2 || this.runningLevel3){
            // If players lose set player alive to false and set the snake body size to 0
            p1.move();
            p1.checkApple(apple);


            player1Alive = p1.checkPoison(poison1) && p1.checkPoison(poison2) && p1.checkPoison(poison3) && p1.checkObstacle(wall) && p1.checkObstacle(wall2) && p1.checkObstacle(wall3) && p1.checkObstacle(wall4) && p1.checkCollisions();;


            if (!player1Alive){
                p1.bodyParts = 0;
            }

            p2.move();
            p2.checkApple(apple);
            player2Alive = p2.checkPoison(poison1) && p2.checkPoison(poison2) && p2.checkPoison(poison3) && p2.checkObstacle(wall) && p2.checkObstacle(wall2) && p2.checkObstacle(wall3) && p2.checkObstacle(wall4) && p2.checkCollisions();;


            if (!player2Alive){
                p2.bodyParts = 0;
            }

            // Stop the game if both players lose

            if (!player1Alive && !player2Alive){
                running = false;
                runningLevel2 = false;
                runningLevel3 = false;
                timer.stop();
            }
        }

        repaint();
    }

    public class Clicklistener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == playAgain){
                startGame();
            } else if (e.getSource() == quit) {
                System.exit(0);
            }else if(e.getSource() == nextLevel){
                startNextLevel(player1Alive, player2Alive);
            }
        }
    }



}
