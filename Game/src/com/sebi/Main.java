package com.sebi;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Main {
    static JFrame Frame = new JFrame("Game");   //delcaring JFrame out of main void for easier access later

    public static void main(String[] args) {
      GridLayout layout = new GridLayout(0,1);
      JPanel Menu = new JPanel();
      JButton slow = new JButton("slow");
      JButton medium = new JButton("medium");
      JButton fast = new JButton("fast");
      Menu.setLayout(layout);
      Menu.add(slow);
      Menu.add(medium);
      Menu.add(fast);
      JFrame fr = new JFrame();
      slow.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
          fr.dispose();
          game(1);
        }
      });
      medium.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
          fr.dispose();
          game(2);
        }
      });
      fast.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
          fr.dispose();
          game(3);

        }
      });
      fr.add(new MenuIcon(), BorderLayout.CENTER);
      fr.add(Menu, BorderLayout.SOUTH);
      fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      fr.setSize(300, 300);
      fr.setLocationRelativeTo(null);
      fr.setVisible(true);
      try {
        UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
    }
    catch (UnsupportedLookAndFeelException e) {
       // handle exception
    }
    catch (ClassNotFoundException e) {
       // handle exception
    }
    catch (InstantiationException e) {
       // handle exception
    }
    catch (IllegalAccessException e) {
       // handle exception
    }


    }

    static void game(int PlayerSpeed){
      Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //initialising basic stuff like position and size
      Frame.setLocationRelativeTo(null);
      Frame.setSize(800,400);
      Frame.add(new MySnake(PlayerSpeed));                       //add the custom JPanel
      Frame.setIconImage(new ImageIcon("src/com/sebi/dot.png").getImage());   //trying to add an ImageIcon, succeeded in my IDE, but as soon as I compiled the Jar, it stopped working
      Frame.setResizable(false);
      Frame.setVisible(true);             //setVisible AFTER adding the JPanel
    }
}
