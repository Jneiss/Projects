/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author coloSSus
 */
public class Rocket extends Sprite{
    
int verticalspeed = 70;
Rectangle2D.Double target;
boolean locked = false;
    
    
  public Rocket (BufferedImage[] i, double x, double y, long delay, GamePanel p){
        super(i,x,y,delay,p);
        
        if (y<parent.getHeight()/2){
            setVerticalSpeed(verticalspeed);
        }else{
            setVerticalSpeed(-verticalspeed);
        }
        
    }
    
    @Override
    public void doLogic (long delta){
      super.doLogic(delta); 
      
      if(getHorizontalSpeed()>0){
          target = new Rectangle2D.Double(x+getWidth(),y,
                  parent.getWidth()-x,getHeight());
      }else{
          target = new Rectangle2D.Double(0,y,x,getHeight());
      }
      
      if(!locked && parent.copter.intersects(target)){
          setVerticalSpeed(0);
          locked = true;
      }
      
      if(locked){
          if (y < parent.copter.getY()){
              setVerticalSpeed(40);
          }
          if(y > parent.copter.getY()+parent.copter.getHeight()){
              setVerticalSpeed(-40);
          }
      }
     
      if(getHorizontalSpeed()>0 && x> parent.getWidth()){
          remove = true;
      }
      
      if(getHorizontalSpeed()<0 && x+getWidth()<0){
          remove = true;
      }
      
    }
    
    @Override
    public void setHorizontalSpeed(double d){
        super.setHorizontalSpeed(d);
        
        if(getHorizontalSpeed()>0){
            setLoop(4,7);
        }else{
            setLoop(0,3);
        }
    }

    @Override
    public boolean collideWith(Sprite s) {
        
        if(remove){
            return false;
        }
        
        if(this.intersects(s)){
            
            if(s instanceof Heli){
                parent.createExplosion((int)x,(int) y);
                parent.createExplosion((int)s.x,(int) s.y);
                remove   = true;
                s.remove = true;
                return true;
            }
            
            if(s instanceof Rocket){
                parent.createExplosion((int)x,(int) y);
                parent.createExplosion((int)s.x,(int) s.y);
                remove   = true;
                s.remove = true;
                return true;
            }
        }
        
        return false;
    }
    
    
}
