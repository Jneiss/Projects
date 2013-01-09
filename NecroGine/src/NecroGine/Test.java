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
public class Test extends Sprite {
    
    public Test (BufferedImage[] i, double x, double y, long delay, GamePanel p){
      super(i,x,y,delay,p);
        
    }

    @Override
    public boolean collideWith(Sprite s) {
       return false;
    }
    
}
