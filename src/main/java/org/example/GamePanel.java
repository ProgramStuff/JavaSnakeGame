package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/*
 * Title:
 * Filename: GamePanel
 * Author: Jordan Kelsey
 * Date: 2024-11-12
 * Purpose:
 */

/**
 * @author Jordan Kelsey
 *
 * Gamepanel Class extendsnJPanel implements ActionListner
 * Contains the snake game logic for ending the game, detecting collisions, increasing snake size
 * beginning next level and ending game
 *
 *  running controls the first level start and stop
 *  runningLevel2 controls the second level start and stop
 *  runningLevle3 controls the third level start and stop
 *  logger to save and retrieve high score
 *  apple the snake will eat to increase length
 *  allPoison contains all poison apples that stop the game if hit
 *  walls contains all walls that stop the game if hit
 *  p1 player 1
 *  p2 player 2
 *  player1Alive boolean used to decide the game status
 *  player2Alive boolean used to decide the game status
 *  playAgin a button to play again
 *  quit a button to quit
 *  nextLevel a button to begin next level
 */

public class GamePanel extends JPanel implements ActionListener{

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT / UNIT_SIZE);
    static final int DELAY = 75;
    boolean running = false;
    boolean runningLevel2 = false;
    boolean runningLevel3 = false;
    Timer timer;
    Random random;

    Logger logger = new Logger();
    int matchWinner;


    // Create Food Poison and obstacles
    Apple apple = new Apple(Color.YELLOW);
    PoisonFood[] allPoison = new PoisonFood[3];
    Obstacle[] walls = new Obstacle[4];


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


    /**
     * GamePanel constructor
     *
     * Instantiates Obstacles and PosionFood
     * add the key listeners and action listeners
     * starts the game
     */
    GamePanel(){
        random = new Random();

        // Create poison food
        for (int i = 0; i < 3; i++) {
            allPoison[i] = new PoisonFood(new Color(87, 9, 176));
        };

        // Create walls
        for(int i = 0; i < 4; i++) {
            walls[i] = new Obstacle(Color.GRAY);
        }


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


    /**
     * StartGame method
     *
     * resets player information, spawns new food and starts the game
     */
      public void startGame(){
        // Resets player on every start
        p1.resetPlayer();
        p2.resetPlayer();
        runningLevel2 = false;
        runningLevel3 = false;
        level = 1;
        apple.newFood();
        running = true;

        //Stop timer if exists and create a new one
        if (timer != null) {
            timer.stop();
        }
        timer = new Timer(DELAY, this);
        timer.start();
        // When restarting game focus has to be set back to the current container for key events to work
        this.requestFocus();

        // Remove button from container
        this.remove(playAgain);
        this.remove(quit);
    }

    /**
     * @param g the <code>Graphics</code> object to protect
     *
     * calls the draw method if the game is still running and calls the gaeOver function otherwise
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if (running || runningLevel2 || runningLevel3){
            draw(g);
        } else {
            gameOver(g);
        }

    }


    /**
     * @param g the <code>Graphics</code> object to display the score
     * Displays the current players scores
     */
    public void displayScores(Graphics g){
        // Display player scores
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 20));
        g.drawString("Player 1 Score: " + p1.appleEaten, 10, g.getFont().getSize());

        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 20));
        g.drawString("Player 2 Score: " + p2.appleEaten, 430, g.getFont().getSize());
    }

    /**
     * @param g the <code>Graphics</code> object to display the snake body
     * Create and fill the snakes body pieces
     */
    public void createSnake(Graphics g){
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

    /**
     * @param g the <code>Graphics</code> object to display the apples, poison and walls
     *
     * Displays the food, obstacles and poison food for each level
     */
    public void draw(Graphics g){


        // Uncomment to add a grid for visualization
//        for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
//            g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
//            g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
//        }

        // Create Apple
        g.setColor(apple.getColor());
        g.fillOval(apple.getFoodX(), apple.getFoodY(), UNIT_SIZE, UNIT_SIZE);

        // Draw Poison Food for Levels 2 and 3
        if (runningLevel2 || runningLevel3) {
            for (PoisonFood poisonFood : allPoison) {
                g.setColor(poisonFood.getColor());
                g.fillRoundRect(poisonFood.getFoodX(), poisonFood.getFoodY(), UNIT_SIZE, UNIT_SIZE, 10, 10);
            }
        }

        // Draw Obstacles for Level 3
        if (runningLevel3) {
            for (Obstacle wall : walls) {
                g.setColor(wall.getColor());
                g.fillRect(wall.getObstacleX(), wall.getObstacleY(), UNIT_SIZE, UNIT_SIZE);
            }
        }

        displayScores(g);
        createSnake(g);

        // Level checks
        if (running && (p1.appleEaten >= 1 || p2.appleEaten >= 1)) {
            nextLevel(g);
        } else if (runningLevel2 && (p1.appleEaten >= 2 || p2.appleEaten >= 2)) {
            nextLevel(g);
        }

    }


    /**
     * Triggers the next level to begin, incrementing the level and creating the poison food
     * and obstacles
     */
    public void startNextLevel(){
        // Start the next level and create new food and obstacles for the level
        if (timer != null) {
            timer.stop();
        }
        timer = new Timer(DELAY, this);
        timer.start();
        level++;
        // Using overloaded resetPlayer function to persist apple count
        if(player1Alive){
            p1.resetPlayer(p1.appleEaten);
        }
        if(player2Alive){
            p2.resetPlayer(p2.appleEaten);
        }
        apple.newFood();
        // Create poison food
        if(level == 2){
            runningLevel2 = true;
            running = false;
            for (PoisonFood poisonFood : allPoison) {
                poisonFood.newFood();
            }
        }

        // Create obstacles
        if (level == 3){
            runningLevel3 = true;
            runningLevel2 = false;
            for (Obstacle wall : walls) {
                wall.newObstacle();
            }
        }


        // When restarting game focus has to be set back to the current container for key events to work
        this.requestFocus();
        // Remove the next level button
        this.remove(nextLevel);
        revalidate();
        repaint();
    }


    /**
     * @param g the <code>Graphics</code> object to display the level text
     *
     * Displays the level text and the nextLevel button to start next level
     */
    public void nextLevel(Graphics g){
        // Stop the timer to prevent snake from moving and to display the screen
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

        // Add the next level button
        this.add(nextLevel);
        this.revalidate();

    }

    /**
     * @param g the <code>Graphics</code> object to display the game over text,
     * the players score and the high score.
     * Writes and reads from the text file using the logger class instance
     */
    public void gameOver(Graphics g){

        /*
            Set all poison and wall positions to off screen location
            otherwise they would still be on screen but invisible. Causes collisions when they can't be seen
         */
        for (PoisonFood poisonFood : allPoison) {
            poisonFood.setFoodX(800);
            poisonFood.setFoodY(800);
        }

        for (Obstacle wall : walls) {
            wall.setObstacleX(800);
            wall.setObstacleY(800);
        }


        // Use logger to check high score, set high score and display high score
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

        // Notify user of new high score
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

        // Buttons to allow user to quit or play again
        playAgain.setBounds(100, 500 , 100, 30);
        quit.setBounds(400, 500 , 100, 30);

        this.add(playAgain);
        this.add(quit);
    }


    /**
     * @param e the event to be processed
     * Listens for collision with obstacles, food or poison food
     * clears snake body and triggers the gameOver function by setting running values to false
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.running || this.runningLevel2 || this.runningLevel3){
            // If players lose set player alive to false and set the snake body size to 0

            /* Player 1 */
            p1.move();
            p1.checkApple(apple);

            player1Alive = p1.checkCollisions();
            for (PoisonFood poisonFood : allPoison) {
                player1Alive &= p1.checkPoison(poisonFood);
            }
            for (Obstacle wall : walls) {
                player1Alive &= p1.checkObstacle(wall);
            }


            if (!player1Alive){
                p1.bodyParts = 0;
            }

            /* Player 2 */

            p2.move();
            p2.checkApple(apple);
            player2Alive = p2.checkCollisions();
            for (PoisonFood poisonFood : allPoison) {
                player2Alive &= p2.checkPoison(poisonFood);
            }
            for (Obstacle wall : walls) {
                player2Alive &= p2.checkObstacle(wall);
            }


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

    /**
     * Click listener to define the buttons implementation
     */
    public class Clicklistener implements ActionListener{
        /**
         * @param e the event to be processed
         */
        // Click listener to call functions when buttons are pressed
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == playAgain){
                startGame();
            } else if (e.getSource() == quit) {
                System.exit(0);
            }else if(e.getSource() == nextLevel){
                startNextLevel();
            }
        }
    }



}
