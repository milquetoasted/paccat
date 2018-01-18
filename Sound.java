/** Hannah He, Lila Huang, Lindsey Jin
 * ICS4U
 * January 26, 2016
 * Runs music */
 
 // Import needed things
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

class Sound
{
    //input sound files
    File music = new File("Music.wav");
    
    public Sound()
    {
        
    }  
    
    /* plays sound */ 
    public void playSound ()
    {
        try
        {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(music));
            clip.start();
        }
        catch (Exception e)
        {
            
        }
    }
}