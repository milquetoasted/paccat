import java.io.*;
import javax.imageio.*;

public class Frog extends Life
{
    public Frog(Grid G){
         super(G);
         try{
             img = ImageIO.read(new File("kermit.png"));
             img2 = ImageIO.read(new File("300.png"));
         } catch(IOException ie){};
    }
    
     /* return points gained by cat */ 
    public int die (int points){
        died = true;
        return points+300;
    }
}
