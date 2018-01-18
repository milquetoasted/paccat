/** Hannah He, Lila Huang, Lindsey Jin
 * ICS4U
 * January 26, 2016
 * Extra Life Cat movement, life/death, etc.*/
 
import java.awt.*;
import java.io.*;
import javax.imageio.*;

public class Extra extends Life
{
    private int lifetimer; // extra life cat will disappear after 15 seconds into the level

    public Extra(Grid G){
        super(G);
        try{
            img = ImageIO.read(new File("extracat.png"));
            img2 = ImageIO.read(new File("extralife.png"));
        } catch(IOException ie){};
        lifetimer = 0;
    }

     /* stops displaying after a timer is up */ 
    public void display (Graphics g){
        if (lifetimer < 150) super.display (g);
    }

     /* moves extra life cat */ 
    public void move (){
        super.move ();
        lifetimer++;
    }

     /* return new number of lives of cat */ 
    public int mate (int lives){
        died = true;
        return lives+1;
    }
}
