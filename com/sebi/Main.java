package com.sebi;

import javax.swing.*;
import java.net.URL;

public class Main {
    private static JFrame Frame = new JFrame("Game");

    public static void main(String[] args) {
        Frame.setLocationRelativeTo(null);
        Frame.add(new MySnake());
        Frame.setIconImage(new ImageIcon("src/com/sebi/dot.png").getImage());
        Frame.setSize(800,400);
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame.setResizable(false);
        Frame.setLocationRelativeTo(null);
        Frame.setVisible(true);

    }
}
