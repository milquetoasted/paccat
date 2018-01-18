/** Hannah He, Lila Huang, Lindsey Jin
 * ICS4U
 * January 26, 2016
 * Predator movement, life/death, etc.*/ 

 //Import
import java.awt.*;
import java.io.*;
import javax.imageio.*;

public class Predator extends Life
{
    private Image alt; //Wounded bird
    private int woundtimer; //Times how long the birds stay wounded
    private boolean wounded;

    public Predator (int X, int Y, int D, Grid G){ 
        super (X, Y, G); //Location of bird and grid
        direction = D; // Set other information
        wounded = false;
        retime();
        try{
            img = ImageIO.read(new File("bird.png"));
            alt = ImageIO.read(new File("woundedbird.png"));
        } catch(IOException ie){};
    }

    /* display predator */ 
    public void display(Graphics g){ 
        if (!wounded || died) super.display(g);
        else{
            if (woundtimer < 100){
                if (woundtimer%2 == 0 || fork()){
                    if (woundtimer < 80) g.drawImage(alt,y*16-4,x*16-4,null);
                    else g.drawImage(img,y*16-4,x*16-4,null);
                }
                else{
                    if (direction == 0) g.drawImage(alt,y*16-4,x*16-16,null);
                    else if (direction == 1) g.drawImage(alt,y*16+12,x*16-4,null);
                    else if (direction == 2) g.drawImage(alt,y*16-4,x*16+12,null);
                    else if (direction == 3) g.drawImage(alt,y*16-16,x*16-4,null);
                }
                woundtimer++;
            }
            else{
                wounded = false;
                retime();
                super.display(g);
            }
        }
    }

    /* moves predator */ 
    public void move(){
        if ((!wounded || woundtimer%2 == 0)||(fork() && x != 16 && y != 16)) super.move();
    }
    
    /* setter */ 
    public void setwounded(boolean b){ // Wounded makes bird move twice as slowly
        wounded = b;
    }
    
    /* getter */ 
    public boolean getwounded(){
        return wounded;
    }

    /* resetter */ 
    public void retime(){
        woundtimer = 0;
    }

    /* predator dies, returns new points for cat */ 
    public int die (int points){
        died = true;
        wounded = false;
        retime();
        if (woundtimer < 80){
            try{img2 = ImageIO.read(new File("250.png"));} catch(IOException ie){};
            return points+250;
        }
        else{
            try{img2 = ImageIO.read(new File("400.png"));} catch(IOException ie){};
            return points + 400;
        }
    }
}
