package com.sebi;

import java.awt.*;
import javax.swing.*;

public class MenuIcon extends JPanel{


  MenuIcon(){
    setBackground(Color.getHSBColor(0.6f, 0.8f, 0.97f/2));
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    g.setColor(Color.yellow);
    g.fillOval(200 - 30, 80 - 30, 30, 30);
    g.fillRect(120, 140, 10, 10);                   //Player rendern

    int abstnd = 30;
    for (int i = 0; i < 2; i++){                                 //Herzen rendern
        int[] xPoints = {i * abstnd, i * abstnd,  12+i*abstnd, 15+i*abstnd,
           27+i*abstnd, 27+i*abstnd, 23+i*abstnd, 16+i*abstnd, 16+i*abstnd,
           11+i*abstnd, 11+i*abstnd , 4+i*abstnd, i * abstnd};
        int[] yPoints = {5, 15, 26, 26, 15,  5,  0,  0,  1, 1,  0  , 0, 5};
        g.setColor(Color.decode("#C91010"));
        g.fillPolygon(xPoints, yPoints, 13);
      }

    g.setColor(Color.green);
    g.fillRect(170, 120, 12, 400);       //Hindernisse Rendern
    g.setColor(Color.green.darker());
    g.fillRect(210, 135, 12, 400);
    g.setColor(Color.green.darker().darker());
    g.fillRect(260, 130, 12, 400);
    g.setColor(Color.orange);
    g.fillRect(0, 150, 800, 399);                     //Boden rendern

    }



}
