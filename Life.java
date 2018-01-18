/** Hannah He, Lila Huang, Lindsey Jin
 * ICS4U
 * January 26, 2016
 * Life movement, life/death, etc.*/
 
 import java.awt.*;

public class Life
{
    //Variables
    int x, y, direction; //0 up, 1 left, 2 down, 3 right
    Grid g;

    protected Image img, img2;
    protected boolean[] empty = new boolean[4];
    protected boolean died, gone, adv; //adv: for advancing
    protected int choices;

    public Life (int X, int Y, Grid G){
        setlocation(X, Y);
        g = G;
        died = false;
        gone = false;
    }

    /* alternate constructor */ 
    public Life (Grid G){ 
        g = G;
        do{
            x = (int)(Math.random()*16);
            y = (int)(Math.random()*32);
        } while (!g.grid[x][y]);
        direction = (int)(Math.random()*4);
        died = false;
        gone = false;
    }

    /* display method */ 
    public void display (Graphics g){
        if (!died) g.drawImage(img,y*16-4,x*16-4,null);
        else g.drawImage(img2,y*16-4,x*16-4,null);
    }

    /* setter */ 
    public void setlocation (int X, int Y){
        x = X; y = Y;
    }

    /* basic move method */ 
    public void move(){
        int newdirection = 0, temp;
        int opposite = 2*(direction%2+1)-direction;
        boolean fork = fork();

        if (x == 16 && y == 16){
            direction = 0;
            advance();
        }
        else if (!fork()){
            adv = advance();
            if (!adv){
                direction = opposite;
                adv = advance();
            }
        }
        else{
            temp = (int)(Math.random()*(choices));
            for (int y = 0; y < 4; y++){
                if (empty[y] && y != 2*(direction%2+1)-direction){
                    if (temp == 0) newdirection = y;
                    temp--;
                }
            }
            direction = newdirection;
            adv = advance();
        }
    }

    /* finds whether the surrounding spaces can be moved into or not */ 
    protected void findempty(){
        empty[0] = g.grid[x-1][y];
        empty[1] = g.grid[x][y+1];
        empty[2] = g.grid[x+1][y];
        empty[3] = g.grid[x][y-1];
    }
    
    /* tells if the path splits or not */ 
    protected boolean fork(){
        boolean fork = false;
        choices = -1;
        findempty();
        for (int a = 0; a < 4; a++){
            if (empty[a]){
                choices++;
                if (a%2 != direction%2) fork = true;
            }
        }
        return fork;
    }

    /* tries to advance the life form */ 
    public boolean advance(){
        boolean advance = true;
        if (direction == 0 && empty[0]) x--;
        else if (direction == 1 && empty[1]) y++;
        else if (direction == 2 && empty[2]) x++;
        else if (direction == 3 && empty[3]) y--;
        else advance = false;
        return advance;
    }
    
    /* if the life form no longer exists */ 
    public boolean isgone(){
        return gone;
    }
    public void remove(){
        setlocation(0,0);
        gone = true;
    }
    
    /* if the life form died */ 
    public boolean dead(){
        return died;
    }
    
    /* revive life form */ 
    public void revive(){
        died = false;
    }
}
