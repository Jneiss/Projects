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
public class Explosion extends Sprite {
int old_pic = 0;

    public Explosion (BufferedImage[] i, double x, double y, long delay, GamePanel p) {
        super(i,x,y,delay,p);
    }


    @Override
    public void doLogic(long delta){
      old_pic = currentpic;
      super.doLogic(delta);
      if(currentpic == 0 && old_pic!=0){
          remove = true;
      }        
        
    }

    @Override
    public boolean collideWith(Sprite s) {
        return false;
    }
    
}
