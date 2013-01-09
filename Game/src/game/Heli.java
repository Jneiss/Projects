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
public class Heli extends Sprite {
  
  public Heli(BufferedImage[] i, double x, double y, long delay, GamePanel p){
    super(i,x,y,delay,p);
  }

    @Override
  public void doLogic(long delta){
      super.doLogic(delta);
      
      if(getX()<0){
          setHorizontalSpeed(0);
          x = 0;
      }
      
      if(getX()+getWidth()>parent.getWidth()){
          x = (parent.getWidth()-getWidth());
          setHorizontalSpeed(0);
      }
      
      if(getY()<0){
          y = 0;
          setVerticalSpeed(0);
      }
      
      if(getY()+getHeight()>parent.getHeight()){
          y = parent.getHeight()-getHeight();
          setVerticalSpeed(0);
      }
  }

    @Override
    public boolean collideWith(Sprite s) {
        
        if(this.intersects(s)){
            
            if(remove){
                return false;
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
