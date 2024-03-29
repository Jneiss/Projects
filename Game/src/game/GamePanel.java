/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

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
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable, KeyListener, ActionListener{   
JFrame frame;

long delta    = 0;
long fps      = 0;
long last     = 0;
long gameover = 0;

Heli copter;
Vector<Sprite> actors;
Vector<Sprite> painter;

boolean up;
boolean down;
boolean left;
boolean right;
boolean started;
int speed = 50;

Timer timer;
BufferedImage[] rocket;
BufferedImage[] explosion;
BufferedImage background;

SoundLib soundlib;

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
      gameover = 0;
      
      BufferedImage [] heli = loadPics("heli.gif", 4);
      rocket                = loadPics("rocket.gif",8);
      background            = loadPics("background.jpg",1)[0];
      explosion             = loadPics("explosion.gif",5);
      
      
      actors  = new Vector<Sprite>();
      painter = new Vector<Sprite>();
      copter  = new Heli(heli,400,300,100,this);
      actors.add(copter);
      
      soundlib = new SoundLib();
      soundlib.loadSound("bumm", "boom.wav");
      soundlib.loadSound("rocket", "rocket_start.wav");
      soundlib.loadSound("heli", "heli.wav");
      
      createClouds();
      
      timer = new Timer (3000,this);
      timer.start();
      
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
      
      g.drawImage(background, 0, 0, this);
      
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
              checkKeys();
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

    private void checkKeys() {

        if (up){
            copter.setVerticalSpeed(-speed);
        }
        
        if (down){
            copter.setVerticalSpeed(speed);
        }
        
        if (left){
            copter.setHorizontalSpeed(-speed);
        }
        
        if (right){
            copter.setHorizontalSpeed(speed);
        }
        
        if (!up&&!down){
            copter.setVerticalSpeed(0);
        }
        
        if (!left&&!right){
            copter.setHorizontalSpeed(0);
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
            
            if(copter.remove && gameover == 0){
              gameover = System.currentTimeMillis();
            }
            
            if(gameover>0){
                if(System.currentTimeMillis()-gameover>3000)
                    stopGame();
            }
       
    }
    
        

    private void moveObjects() {
        
        for (ListIterator<Sprite> it = actors.listIterator();it.hasNext();){
            Sprite r = it.next();
            r.move(delta);
        }
    }
    
    private BufferedImage[] loadPics (String path, int pics){
        
        BufferedImage[] anim = new BufferedImage[pics];
        BufferedImage source = null;
        
        URL pic_url = getClass().getClassLoader().getResource(path);
        
        try {
            source = ImageIO.read(pic_url);
        } catch (IOException e) {}
        
        for (int x=0;x<pics;x++){
            anim [x] = source.getSubimage(x*source.getWidth()/pics, 0, 
                    source.getWidth()/pics, source.getHeight());
        }
        
        return anim;
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

    private void createClouds() {
        BufferedImage[] bi = loadPics("cloud.gif",1);
        
        for(int y=10;y<getHeight();y+=50){
            int x = (int) (Math.random()*getWidth());
            Cloud cloud = new Cloud (bi,x,y,1000,this);
            actors.add(cloud);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(isStarted() && ae.getSource().equals(timer)){
            createRocket();
        }
    }
 
    private void createRocket(){
        
        int x = 0;
        int y = (int) (Math.random() * getHeight());
        int hori = (int) (Math.random() * 2);
        
        if (hori == 1){
            x = -30;
        }else{
            x = getWidth()+30;
        }
        
        Rocket rock = new Rocket (rocket,x,y,100,this);
        if(x<0){
            rock.setHorizontalSpeed(100);
        }else{
            rock.setHorizontalSpeed(-100);
        }
        
        ListIterator<Sprite> it = actors.listIterator();
        it.add(rock);
        soundlib.playSound("rocket");
        
    }

    private void startGame(){
        doInitializations();
        setStarted(true);
        soundlib.loopSound("heli");
    }
    
    private void stopGame() {
        timer.stop();
        setStarted(false);
        soundlib.stopLoopingSound();
    }
    
    public void createExplosion(int x, int y){
          ListIterator<Sprite> it = actors.listIterator();
          it.add(new Explosion(explosion,x,y,100,this));
          soundlib.playSound("bumm");
    }
    
}