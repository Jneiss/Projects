/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author coloSSus
 */
public interface Movable {
    
    public void doLogic(long delta);
    
    public void move (long delta);
    
}
