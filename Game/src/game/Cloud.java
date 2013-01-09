/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.image.BufferedImage;

/**
 *
 * @author coloSSus
 */
public class Cloud extends Sprite {
    
  final int SPEED = 20;
    
    public Cloud (BufferedImage[] i, double x, double y, long delay, GamePanel p){
        super(i,x,y,delay,p);
        
        if((Math.random()*2) < 1){
            setHorizontalSpeed(SPEED);
        }else{
            setHorizontalSpeed(-SPEED);
        }
        
    }
    
    @Override
    public void doLogic(long delta){
        super.doLogic(delta);
        
        if(getHorizontalSpeed()>0 && x > parent.getWidth()){
            x = -getWidth();
        }
        
        if(getHorizontalSpeed()<0 && (x+getWidth())<0){
            x = parent.getWidth()+getWidth();
        }

    }

    @Override
    public boolean collideWith(Sprite s) {
        return false;
    }
    
}
