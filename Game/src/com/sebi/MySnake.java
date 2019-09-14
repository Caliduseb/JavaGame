package com.sebi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MySnake extends JPanel implements ActionListener {

    private Player me = new Player();
    private Hindernis hind = new Hindernis();
    private Hindernis hind2 = new Hindernis();
    private Hindernis hind3 = new Hindernis();
    private int cont = 0;
    private Timer timer;
    private Health health = new Health();
    private boolean imune = false;
    private int iters = 0;
    private boolean gameover = false;
    private Color backgr;
    private float bright = 0.97f;
    private boolean darker = false;


    MySnake(){
        backgr = Color.decode("#1313CA");
        setBackground(backgr);
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(new TAdapter());
        setPreferredSize(new Dimension(800, 400));
        timer = new Timer(5, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.yellow);
        g.fillRect(me.x, me.y, 10, 10);

        int abstnd = 30;
        for (int i = 0; i < health.count; i++){
            int[] xPoints = {i * abstnd, i * abstnd,  12+i*abstnd, 15+i*abstnd, 27+i*abstnd, 27+i*abstnd, 23+i*abstnd, 16+i*abstnd, 16+i*abstnd, 11+i*abstnd, 11+i*abstnd , 4+i*abstnd, i * abstnd};
            int[] yPoints = {5, 15, 26, 26, 15,  5,  0,  0,  1, 1,  0  , 0, 5};
            g.setColor(Color.decode("#C91010"));
            g.fillPolygon(xPoints, yPoints, 13);

        }
        g.setColor(Color.green);
        g.fillRect(hind.Hx, hind.Hy, hind.Hw, 400);
        g.fillRect(hind2.Hx, hind2.Hy, hind2.Hw, 400);
        g.fillRect(hind3.Hx, hind3.Hy, hind3.Hw, 400);
        g.setColor(Color.orange);
        g.fillRect(0, 320, 800, 399);
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
        if (hind.Hx < me.x + 10 && me.x + 10 < hind.Hx + hind.Hw){
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
            timer.setDelay((int) (5 - cont/10000)); }

        if(cont % 10 == 0){
            if (bright <= 0.2684843){
                darker = true;
            }
            if (bright >= 0.97f){
                darker = false;
            }

            if (darker){
                bright += 0.0005;
            } else {
                bright -= 0.0005;
            }

            backgr = Color.getHSBColor(0.6f, 0.8f, bright);
            setBackground(backgr);
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
            hind.Hx = 600 + ((int) (Math.random() * 328))*3;
            hind.Hy = 300 - ((int) (Math.random() * 7))*3;
        }
        if ((hind2.Hx + hind2.Hw) < 0){
            hind2.Hx = 600 + ((int) (Math.random() * 328))*3;
            hind2.Hy = 300 - ((int) (Math.random() * 7))*3;
        }
        if ((hind3.Hx + hind3.Hw) < 0){
            hind3.Hx = 600 + ((int) (Math.random() * 328))*3;
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
            me.jump();
            repaint();

            if(gameover && e.getKeyCode() == KeyEvent.VK_ENTER){
                health.count = 4;
                cont = 0;
                iters = 0;
                gameover = false;
                timer.start();
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

        void setX(int _x){
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

        Hindernis(){
            Hx = 700 + ((int) (Math.random() * 328))*3;
            Hy = 300 - ((int) (Math.random() * 7))*3;
            Hw = 12;
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
