/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NecroGine;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;
import javax.imageio.ImageIO;

/**
 *
 * @author coloSSus
 */
public class ImageLib {
    
    Hashtable<String, BufferedImage[]> images;
    
    public ImageLib(){
        images = new Hashtable<String, BufferedImage[]>();
    }
    
    public void addImage(String name, String path, int pics){
        
        if(images.containsKey(name)){
          return;
    }
        
        images.put(name, loadPics(path,pics));
        
    }
    
    public BufferedImage[] getImage(String name){
        BufferedImage[] img = images.get(name);
        return img;
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
 
    public void clearLib(){
        images.clear();
    }
       
}
