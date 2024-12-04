package org.example;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * Title:
 * Filename: Logger
 * Author: Jordan Kelsey
 * Date: 2024-11-30
 * Purpose:
 */


/**
 * Logger class to reade and write data to a text file
 */
public class Logger {
    private static Logger _instance;
    private PrintWriter fileWriter;
    private String highScores;

    /**
     * Logger constructor
     * Creates a text file or opens a text file
     */
    public Logger(){
        try {
            fileWriter = new PrintWriter(new FileWriter("highScores.txt", true));
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    /**
     * @param highScore for the new high score
     *
     * writes the high score to the text file
     */
    public void setHighScore(int highScore){
        try {
            FileWriter fileWriter1 = new FileWriter("highScores.txt");
            PrintWriter writer = new PrintWriter(fileWriter1);
            writer.println(highScore);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * @return highScore stored within the text file
     */
    public String getHighScore(){

        try {
            File myObj = new File("highScores.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                highScores = myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return highScores;
    }

}
