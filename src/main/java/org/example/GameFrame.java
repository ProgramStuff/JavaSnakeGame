package org.example;

import javax.swing.*;
import java.awt.*;

/**
 * Title:
 * Filename: GameFrame
 * Author: Jordan Kelsey
 * Date: 2024-11-12
 * Purpose:
 */
public class GameFrame extends JFrame {
    public GameFrame() {
        GamePanel gamePanel = new GamePanel();
        this.add(gamePanel);
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        // Create instance of food class, return food eaten status to snake and update body parts
    }
}
