/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NecroGine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ListIterator;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author coloSSus
 */
public class GamePanel extends JPanel implements Runnable, KeyListener{   
JFrame frame;

long delta    = 0;
long fps      = 0;
long last     = 0;
long gameover = 0;

Vector<Sprite> actors;
Vector<Sprite> painter;

boolean up;
boolean down;
boolean left;
boolean right;
boolean started;

Timer timer;

ImageLib imagelib;
SoundLib soundlib;

      //TESTACTORS*****************
      
      Test loller;

      //TESTACTORS*****************


    public static void main(String[] args) {
        new GamePanel (800, 600);
    }

    
  public GamePanel(int w, int h){
    this.setPreferredSize(new Dimension(w,h));
    this.setBackground(Color.BLUE);
    frame = new JFrame("test");
    frame.setLocation(100, 100);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(this);
    frame.addKeyListener(this);
    frame.pack();
    frame.setVisible(true);

    
    Thread th = new Thread(this);
    th.start();
}
          
  private void doInitializations(){
      
      last     = System.nanoTime();
      
      
      actors  = new Vector<Sprite>();
      painter = new Vector<Sprite>();

      
      //Adding sounds to library//
      soundlib = new SoundLib();
      
      
      //------------------------//
      
      //Adding images to library//
      imagelib = new ImageLib();
      
      imagelib.addImage("lol", "lol.gif", 2);
      //------------------------//
      
      
            
      //TESTACTORS*****************
      
      loller = new Test(imagelib.getImage("lol"),100,100,1000,this);
      actors.add(loller);
      
      //TESTACTORS*****************
      
      
           
      started = true;
      
  }
  
  private void computeDelta(){
      
      delta = System.nanoTime() - last;
      last = System.nanoTime();
      fps = ((long) 1e9) /delta;
      
  }

    @Override
  public void paintComponent(Graphics g){
      super.paintComponent(g);
      
      
      g.setColor(Color.red);  
      g.drawString("FPS: " + Long.toString(fps), 20, 10);  
      
      if(!started){
          return;
      }
      
      for(ListIterator<Sprite> it = painter.listIterator();it.hasNext();){
          Sprite r = it.next();
          r.drawObjects(g);
      }
      
      
  }
  
    @Override
    public void run() {
        
        while(frame.isVisible()){
            
            computeDelta();
            
            if(isStarted()){
              doLogic();
              moveObjects();
              cloneVectors();
            }
            
            repaint();
            
        try {
            Thread.sleep(10);
        }catch (InterruptedException e){}
    }

  }


    private void doLogic() {
        
        for(ListIterator<Sprite> it = actors.listIterator();it.hasNext();){
            Sprite r = it.next();
            r.doLogic(delta);
            
            if(r.remove){
              it.remove();
            }
        }
            for (int i = 0;i < actors.size();i++){
                for (int n = i+1; n < actors.size(); n++){
                    
                    Sprite s1 = actors.elementAt(i);
                    Sprite s2 = actors.elementAt(n);
                    
                    s1.collideWith(s2);
                    
                }
            }
               
    }
    
        

    private void moveObjects() {
        
        for (ListIterator<Sprite> it = actors.listIterator();it.hasNext();){
            Sprite r = it.next();
            r.move(delta);
        }
    }
    
    private void cloneVectors(){
        painter = (Vector<Sprite>) actors.clone();
    }

    @Override
    public void keyTyped(KeyEvent ke) {
      
    }

    @Override
    public void keyPressed(KeyEvent ke) {
      
        if(ke.getKeyCode() == KeyEvent.VK_UP){
            up = true;
        }
        
        if(ke.getKeyCode() == KeyEvent.VK_DOWN){
            down = true;
        }
        
        if(ke.getKeyCode() == KeyEvent.VK_LEFT){
            left = true;
        }
        
        if(ke.getKeyCode() == KeyEvent.VK_RIGHT){
            right = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        
        if(ke.getKeyCode() == KeyEvent.VK_UP){
            up = false;
        }
        
        if(ke.getKeyCode() == KeyEvent.VK_DOWN){
            down = false;
        }
        
        if(ke.getKeyCode() == KeyEvent.VK_LEFT){
            left = false;
        }
        
        if(ke.getKeyCode() == KeyEvent.VK_RIGHT){
            right = false;
        }
        
        if(ke.getKeyCode() == KeyEvent.VK_ENTER){
            if(!isStarted()){
                startGame();
            }
        }
        
        if(ke.getKeyCode() == KeyEvent.VK_ESCAPE){
            if(isStarted()){
                stopGame();
            }else{
              frame.dispose();
            }
        }
        
    }
    
    public void setStarted(boolean started){
        this.started = started;
    }
    
    public boolean isStarted(){
        return started;
    }

    private void startGame(){
        doInitializations();
        setStarted(true);
    }
    
    private void stopGame() {
        timer.stop();
        setStarted(false);
        soundlib.stopLoopingSound();
    }
}