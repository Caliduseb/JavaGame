package com.sebi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MySnake extends JPanel implements ActionListener {

    private Player me = new Player();
    private Hindernis hind = new Hindernis(1);
    private Hindernis hind2 = new Hindernis(2);
    private Hindernis hind3 = new Hindernis(3);
    private Health health = new Health();
    private Sun sun = new Sun();

    private Timer timer;
    private Color backgr;

    private boolean imune = false;
    private boolean gameover = false;
    private boolean darker = false;
    private float bright = 0.97f;
    private int asd;
    private int sunIdle = 0;
    private int iters = 0;
    private int cont = 0;
    enum SUNSTATE {sunUp, sunDown, moonUp, moonDown;}


    MySnake(){
        backgr = Color.getHSBColor(0.6f, 0.8f, 0.97f/2);
        setBackground(backgr);                  //init JPanel properties
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(new TAdapter());
        setPreferredSize(new Dimension(800, 400));
        hind.Hx = 600;
        hind2.Hx = 800;
        hind3.Hx = 1200;
        timer = new Timer(5, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(sun.color);
        g.fillOval(sun.x - sun.size, sun.y - sun.size, sun.size, sun.size);
        g.setColor(Color.yellow);
        g.fillRect(me.x, me.y, 10, 10);                   //Player rendern

        int abstnd = 30;
        for (int i = 0; i < health.count; i++){                                 //Herzen rendern
            int[] xPoints = {i * abstnd, i * abstnd,  12+i*abstnd, 15+i*abstnd,
               27+i*abstnd, 27+i*abstnd, 23+i*abstnd, 16+i*abstnd, 16+i*abstnd,
               11+i*abstnd, 11+i*abstnd , 4+i*abstnd, i * abstnd};
            int[] yPoints = {5, 15, 26, 26, 15,  5,  0,  0,  1, 1,  0  , 0, 5};
            g.setColor(Color.decode("#C91010"));
            g.fillPolygon(xPoints, yPoints, 13);
          }

        g.setColor(Color.green);
        g.fillRect(hind.Hx, hind.Hy, hind.Hw, 400);       //Hindernisse Rendern
        g.setColor(Color.green.darker());
        g.fillRect(hind2.Hx, hind2.Hy, hind2.Hw, 400);
        g.setColor(Color.green.darker().darker());
        g.fillRect(hind3.Hx, hind3.Hy, hind3.Hw, 400);
        g.setColor(Color.orange);
        g.fillRect(0, 320, 800, 399);                     //Boden rendern


        if(gameover){
            String msg = "Game Over";
            String msg2 = "Your score: " + cont;
            Font small = new Font("Helvetica", Font.BOLD, 14);
            FontMetrics metr = getFontMetrics(small);

            g.setColor(Color.white);
            g.setFont(small);
            g.drawString(msg, (800 - metr.stringWidth(msg)) / 2, 400 / 2 - 9);
            g.drawString(msg2, (800 - metr.stringWidth(msg2)) / 2, 400 / 2 + 9);
          }

      }


    private boolean collision(){
        if (hind.Hx < me.x + 10 && me.x + 10 < hind.Hx + hind.Hw){      //check for each obstacle
            if (me.y + 11 >= hind.Hy){
                return true;
            }
        }
        if (hind2.Hx < me.x + 10 && me.x + 10 < hind2.Hx + hind2.Hw){
            if (me.y + 11 >= hind2.Hy){
                return true;
            }
        }
        if (hind3.Hx < me.x + 10 && me.x + 10 < hind3.Hx + hind3.Hw){
            return me.y + 11 >= hind3.Hy;
        }

        return false;
    }


    private void doStuff(){
        cont++;

        if ((int) 5 - cont/10000 >= 0){
            timer.setDelay((int) (5 - cont/10000));
        }

        if(cont % 1 == 0){
              if (bright <= 0.2684843){
                  darker = true;
                  asd = cont;
              }

              if (bright >= 0.97f){
                  darker = false;
                  //-1781
              }

              if (darker){
                  bright += 0.0005/2;
              } else {
                  bright -= 0.0005;
              }

              backgr = Color.getHSBColor(0.6f, 0.8f, bright);
              setBackground(backgr);




        }


        if(cont % 1.5 == 0){
          if ( sun.state == SUNSTATE.sunUp && sunIdle < 1){ sun.y -= 1; sun.x -= 1;}
          if ( sun.state == SUNSTATE.sunDown){ sun.y += 1; sun.x -= 1;}
          if ( sun.state == SUNSTATE.moonUp  && sunIdle < 1){ sun.y -= 1; sun.x -= 1;}
          if ( sun.state == SUNSTATE.moonDown){sun.y += 1; sun.x -= 1;}
          if ( sunIdle > 0){
            sun.x -= 1;
            if (sun.x <= 315){
              sunIdle = 0;
              if (sun.state == SUNSTATE.sunUp){sun.state = SUNSTATE.sunDown;}
              if (sun.state == SUNSTATE.moonUp){sun.state = SUNSTATE.moonDown;}
            }
          }
        }

        if(me.up && me.y >= 250){
            me.setY(me.y - 2);
        }

        if (me.y == 250){
            me.up = false;
            me.down = true;
        }

        if (me.down && me.y < 310){
            me.setY(me.y + 1);
        }

        if(me.down && me.y == 310){
            me.down = false;
        }

        hind.Hx -= 1;
        hind2.Hx -= 1;
        hind3.Hx -= 1;

        if ((hind.Hx + hind.Hw) < 0){
            hind.Hx = generateNewPosition(1);
            hind.Hy = 300 - ((int) (Math.random() * 7))*3;
        }

        if ((hind2.Hx + hind2.Hw) < 0){
            hind2.Hx = generateNewPosition(2);
            hind2.Hy = 300 - ((int) (Math.random() * 7))*3;
        }

        if ((hind3.Hx + hind3.Hw) < 0){
            hind3.Hx = generateNewPosition(3);
            hind3.Hy = 300 - ((int) (Math.random() * 7))*3;
        }

        if (collision()) {
            if (!imune) {
                health.count -= 1;
                imune = true;
                iters = cont;
            }
        }

        if (cont - iters == 12){
            imune = false;
        }

        if (health.count < 1){
            gameOver();
        }


        if (sun.state == SUNSTATE.sunUp && sun.y - sun.size == 30){   //sonne oben angekommen
          sunIdle = 1;
        } else {

        if (sun.state == SUNSTATE.sunDown && sun.y + sun.size >= 400){  //sonne unten angekommen
          sun.state = SUNSTATE.moonUp;
          sun.color = Color.white;
          sun.x = 800;
          sun.y = 400;
        } else {

        if (sun.state == SUNSTATE.moonUp && sun.y - sun.size == 30){     //moon oben angekommen
          sunIdle = 1;
        } else {

        if (sun.state == SUNSTATE.moonDown && sun.y - sun.size == 400){ //moon unten angekommen
          sun.state = SUNSTATE.sunUp;
          sun.color = Color.yellow;
          sun.x = 800;
          sun.y = 400;
        }

            }
          }
        }     //endof ifs





    }



    private int generateNewPosition(int h){

      int abs = 400;
      int Hx = 700 + (int)((int) (Math.random() * 328))*2;
      if (h == 1){
        while(hind2.Hx < Hx && Hx < hind2.Hx + abs || hind3.Hx < Hx && Hx < hind3.Hx + abs){
        Hx = 700 + (int)((int) (Math.random() * 328))*2;
      }
      }
      if (h == 2){
        while(hind.Hx < Hx && Hx < hind.Hx + abs || hind3.Hx < Hx && Hx < hind3.Hx + abs){
        Hx = 700 + (int)((int) (Math.random() * 328))*2;
      }
      }
      if (h == 3){
        while(hind2.Hx < Hx && Hx < hind2.Hx + abs || hind.Hx < Hx && Hx < hind.Hx + abs){
        Hx = 700 + (int)((int) (Math.random() * 328))*2;
      }
      }      return Hx;

    }




    private void gameOver(){
        timer.stop();
        gameover = true;
    }


    @Override
    public void actionPerformed(ActionEvent e){
        doStuff();
        repaint();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {


            if(e.getKeyCode() == KeyEvent.VK_NUMPAD7){
              System.out.print(hind.Hx);
              System.out.print(" ");
              System.out.print(hind2.Hx);
              System.out.print(" ");
              System.out.print(hind3.Hx);
              System.out.print(" ");
              System.out.println(" ");
            } else {me.jump();}

            if(gameover && e.getKeyCode() == KeyEvent.VK_ENTER){
                cont = 0;
                iters = 0;
                gameover = false;
                health.count = 4;
                timer.start();
            }

            if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
              System.exit(0);
            }

        }

    }

    class Player{

        int x;
        int y;
        boolean up;
        boolean down;
        Image icon;


        Player(){
            x = 400;
            y = 310;
            up = false;
            down = false;
            icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("src/com/sebi/dot.png")).getImage();
        }

        void jump() {
            if (!this.up && !this.down) {
                this.up = true;
            }

        }

        void setX(int _x){      //probably redundant bc it's never used
            this.x = _x;
        }

        void setY(int _y){
            this.y = _y;
        }

    }

    class Hindernis{

        int Hx;
        int Hy;
        int Hw;
        int id;

        Hindernis(int id){
          Hx = Hx = 700 + ((int) (Math.random() * 328))*3;
          Hy = 300 - ((int) (Math.random() * 7))*3;
          Hw = 12;
          id = id;
        }




    }

    class Sun{

      int x;
      int y;
      int size;
      Color color;
      SUNSTATE state;

      Sun(){
        x = 800;
        y = 400;
        size = 50;
        state = SUNSTATE.moonUp;
        color = Color.white;
      }

    }

    class Health{

        int count;
        Image icon;

        Health(){
            count = 4;
            icon = new ImageIcon("src/com/sebi/heart.png").getImage();
        }

    }






}
