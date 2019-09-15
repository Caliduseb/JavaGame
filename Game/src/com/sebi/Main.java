package com.sebi;

import javax.swing.*;

public class Main {
    private static JFrame Frame = new JFrame("Game");   //delcaring JFrame out of main void for easier access later

    public static void main(String[] args) {
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //initialising basic stuff like position and size
        Frame.setLocationRelativeTo(null);
        Frame.setSize(800,400);
        Frame.add(new MySnake());                       //add the custom JPanel
        Frame.setIconImage(new ImageIcon("src/com/sebi/dot.png").getImage());   //trying to add an ImageIcon, succeeded in my IDE, but as soon as I compiled the Jar, it stopped working
        Frame.setResizable(false);
        Frame.setVisible(true);             //setVisible AFTER adding the JPanel
    }
}
