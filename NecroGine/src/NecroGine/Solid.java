/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NecroGine;

import java.awt.geom.Rectangle2D;

/**
 *
 * @author coloSSus
 */
public abstract class Solid extends Rectangle2D.Double {
    
        public abstract boolean collideWith(Solid s);
    
}
