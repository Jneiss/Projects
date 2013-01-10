/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NecroGine;

/**
 *
 * @author coloSSus
 */
public class Block extends Solid implements Movable{

protected double dx;
protected double dy;
    
   public Block (double x, double y, double width, double height ){
       
       this.height = height;
       this.width = width;
       
   }    


    @Override
    public void doLogic(long delta) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


        
    public void setHorizontalSpeed(double dx, double scrollspeed){
      this.dx = dx + scrollspeed;
    }
    
    public double getHorizontalSpeed(){
        return dx;
    }
    
    public void setVerticalSpeed(double dy, double scrollspeed){
        this.dy = dy + scrollspeed;
    }
    
    public double getVerticalSpeed(){
        return dy;
    }
    
        @Override
    public void move(long delta) {
      
        if(dx != 0){
            x += dx*(delta/1e9);
        }
        
        if(dy != 0){
            y += dy*(delta/1e9);
        }
        
    }

    @Override
    public boolean collideWith(Solid s) {
        return false;
    }


}
    

