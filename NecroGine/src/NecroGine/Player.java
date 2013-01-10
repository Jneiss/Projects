/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NecroGine;

import java.awt.image.BufferedImage;

/**
 *
 * @author coloSSus
 */
public class Player extends Sprite {
int speed = 300;    
    
    
    public Player (BufferedImage[] i, double x, double y, long delay, GamePanel p){
        super(i,x,y,delay,p);
   
    }
    
    @Override
    public void doLogic(long delta){
        super.doLogic(delta);
        
     
        
    }
    

    public void computeMovement(GamePanel p){
        if(p.down){
            setVerticalSpeed(speed);
        }else if(p.up){
            setVerticalSpeed(-speed);
        }else {
            setVerticalSpeed(0);
        }
        
        if(p.left){
            setHorizontalSpeed(-speed);
        }else if(p.right){
            setHorizontalSpeed(speed);
        }else{
            setHorizontalSpeed(0);
        }
    }

    @Override
    public boolean collideWith(Sprite s) {
        return false;
    }

    @Override
    public boolean collideWith(Solid s) {
        
        if(this.intersects(s)){
            System.out.println("aua");
        }
        
        return false;
    }


    
}
