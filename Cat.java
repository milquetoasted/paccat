import java.io.*;
import javax.imageio.*;

public class Cat extends Life
{
    public Cat(int X, int Y, Grid G){
        super(X,Y,G);
        try{
            img = ImageIO.read(new File("cat.png"));
            img2 = ImageIO.read(new File("deadcat.png"));
        } catch(IOException ie){};
    }
    
    /* moves according to key pressed */ 
    public void move(){
        int temp = direction;
        direction = g.directionpressed;
        findempty();
        adv = advance();
        if (!adv){
            direction = temp;
            adv = advance();
            g.directionpressed = temp;
        }
    }
    
    /* eats a dot on the grid */ 
    public int eat(int points){
        g.dots[x][y] = 0;
        return points+10;
    }
    
    /* if the cat and predator pass each other */ 
    public boolean pass(Life l){
        int d = l.direction;
        if (l.x == x && l.y == y) return true;
        else if ((direction == 0 && d == 2 && x == l.x + 1 && y == l.y)||(direction == 2 && d == 0 && x == l.x - 1 && y == l.y)) return true;
        else if ((direction == 1 && d == 3 && x == l.x && y == l.y - 1)||(direction == 3 && d == 1 && x == l.x && y == l.y + 1)) return true;
        else return false;
    }
    
    /* lose a life */ 
    public int die(int lives){
        died = true;
        return lives-1;
    }
}
