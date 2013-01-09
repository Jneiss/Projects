/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NecroGine;

/**
 *
 * @author coloSSus
 */
import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import java.util.Hashtable;
import java.util.Vector;

public class SoundLib {
    
    Hashtable<String, AudioClip> sounds;
    Vector<AudioClip> loopingClips;
    
    public SoundLib(){
        sounds = new Hashtable<String, AudioClip>();
        loopingClips = new Vector<AudioClip>();
    }
    
    public void loadSound(String name, String path){
        
        if(sounds.containsKey(name)){
          return;
    }
        
        URL sound_url = getClass().getClassLoader().getResource(path);
        sounds.put(name, (AudioClip)Applet.newAudioClip(sound_url));
        
    }
    
    public void playSound(String name){
        AudioClip audio = sounds.get(name);
        audio.play();
    }
    
    public void loopSound(String name){
        AudioClip audio = sounds.get(name);
        loopingClips.add(audio);
        audio.loop();
    }
    
    public void stopLoopingSound(){
        for(AudioClip c:loopingClips){
            c.stop();
        }
    }
    
    public void clearLib(){
        sounds.clear();
        loopingClips.clear();
    }
    
}
