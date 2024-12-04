package org.example;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Title:
 * Filename: Logger
 * Author: Jordan Kelsey
 * Date: 2024-11-30
 * Purpose:
 */
public class Logger {
    private static Logger _instance;
//    private List<int> scores;
    private PrintWriter fileWriter;
    private String highScores;

    public Logger(){
//        scores = new ArrayList<int>();
        try {
            fileWriter = new PrintWriter(new FileWriter("highScores.txt", true));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

//    *** Singleton instance retrieval

    public static Logger getInstance(){
        if (_instance == null){
            synchronized (Logger.class){
                if (_instance == null){
                    _instance = new Logger();
                }
            }
        }
        return _instance;
    }
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
