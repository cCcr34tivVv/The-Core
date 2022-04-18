package com.stefan.game;

import com.stefan.game.states.GameStateManager;
import com.stefan.game.util.KeyHandler;
import com.stefan.game.util.MouseHandler;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;

// implements Runnable = ii permite clasei sa ruleze pe un thread(fir de executie)
public class GamePanel extends JPanel implements Runnable{

    public static int width;
    public static int height;

    public static int oldFrameCount;

    private Thread thread;
    private boolean running=false;

    private BufferedImage img;
    private Graphics2D g;

    private MouseHandler mouse;
    private KeyHandler key;

    private GameStateManager gsm;

    private static GamePanel instance=null;

    private GamePanel(int width, int height)
    {
        this.width=width;
        this.height=height;

        // seteaza dimensiunile dorite
        setPreferredSize(new Dimension(width,height));
        setFocusable(true);
        requestFocus();
    }

    public static GamePanel getInstance(int width,int height){
        if(instance==null){
            instance=new GamePanel(width,height);
        }
        return instance;
    }

    public void addNotify(){
        super.addNotify();

        if(thread==null){
            thread=new Thread(this,"GameThread");
            thread.start();
        }
    }

    public void init() {
        running=true;

        img=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        g=(Graphics2D) img.getGraphics();

        mouse=new MouseHandler(this);
        key=new KeyHandler(this);

        gsm=new GameStateManager();
    }

    // metoda run este obligatorie deoarece am folosit implements Runnable
    public void run(){
        init();

        final double GAME_HERTZ=60.0;
        // Time Before Update
        final double TBU=1000000000/GAME_HERTZ;

        // Must Update Before Render
        final int MUBR=5;

        double lastUpdateTime=System.nanoTime();
        double lastRenderTime;

        final double TARGET_FPS=60;
        // Total Time Before Render
        final double TTBR=1000000000/TARGET_FPS;

        int frameCount=0;
        int lastSecondTime=(int)(lastUpdateTime/1000000000);
        oldFrameCount=0;

        while (running){
            double now=System.nanoTime();
            int updateCount=0;
            while(((now-lastUpdateTime)>TBU)&&(updateCount<MUBR)){
                update();
                input(mouse,key);
                lastUpdateTime+=TBU;
                updateCount++;
            }

            if(now-lastUpdateTime>TBU){
                lastUpdateTime=now-TBU;
            }

            input(mouse,key);
            render();
            draw();
            lastRenderTime=now;
            frameCount++;

            int thisSecond=(int)(lastUpdateTime/1000000000);
            if(thisSecond>lastSecondTime){
                if(frameCount!=oldFrameCount){
                    //System.out.println("NEW SECOND "+thisSecond+" "+frameCount);
                    oldFrameCount=frameCount;
                }
                frameCount=0;
                lastSecondTime=thisSecond;
            }

            while(now-lastRenderTime<TTBR && now-lastUpdateTime<TBU){
                Thread.yield();

                try{
                    Thread.sleep(1);
                }
                catch (Exception e){
                    System.out.println("ERROR : yielding thread");
                }

                now=System.nanoTime();
            }
        }
    }

    public void update(){
        gsm.update();
    }

    public void input(MouseHandler mouse, KeyHandler key){
        gsm.input(mouse, key);
    }

    public void render(){
        if(g!=null){
            g.setColor(new Color(66,134, 244 ));
            g.fillRect(0,0,width,height);
            gsm.render(g);
        }
    }

    public void draw(){
        Graphics g2=(Graphics) this.getGraphics();
        g2.drawImage(img,0,0,width,height,null);
        g2.dispose();
    }

}
