package org.example;

import javax.swing.*;
import java.awt.*;

/*
 * Title:
 * Filename: GameFrame
 * Author: Jordan Kelsey
 * Date: 2024-11-12
 * Purpose:
 */


/**
 * Extends JFrame
 * Hold the game panel and all of its components
 */
public class GameFrame extends JFrame {
    /**
     * GameFrame constructor
     */
    public GameFrame() {
        GamePanel gamePanel = new GamePanel();
        this.add(gamePanel);
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
