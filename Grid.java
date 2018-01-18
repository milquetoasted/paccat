/* This class includes the 2D arrays for the grid of walls/spaces
 * as well of the grid of dots for the cat to eat. */

import java.awt.*;
import java.io.*;
import javax.imageio.*;

public class Grid
{
    boolean[][] grid;
    int[][] dots;
    int directionpressed;
    
    private Image bg, dot, largefood;

    public Grid(int level){
        //load grid and background image
        try{
            load("grids\\grid"+level+".txt");
            bg = ImageIO.read(new File("grids\\grid"+level+".png"));
            dot = ImageIO.read(new File("dot.png"));
            largefood = ImageIO.read(new File("largefood.png"));
        } catch(IOException ie){};
    }

    public void load(String fname) throws IOException{
        grid = new boolean[32][32]; //2d arrays for grid (walls vs spaces)
        dots = new int[32][32];     //and dots (small food, large food, empty spaces)

        FileReader fr = new FileReader (fname); //use filereader and bufferedreader
        BufferedReader filein = new BufferedReader (fr);
        String line;
        for (int x = 0; x < grid.length; x++){
            line = filein.readLine() + ""; //read line and assign values to grid
            for (int y = 0; y < grid[0].length; y++){
                if (line.charAt(y) == 'o'){
                    grid[x][y] = false; //wall + no dot
                    dots[x][y] = 0;
                }
                else{
                    grid[x][y] = true;
                    if (line.charAt(y) == '.') dots[x][y] = 1; //small food
                    else if (line.charAt(y) == 'x') dots[x][y] = 2; //large food
                    else dots[x][y] = 0; //empty space
                }
            }
        }
        filein.close (); // close file
    }

    /* display background and dots */ 
    public void display (Graphics g){
        g.drawImage(bg,0,0,null);
        for (int x = 0; x < dots.length; x++){
            for (int y = 0; y < dots[0].length; y++){
                if (dots[x][y] == 1) g.drawImage(dot,y*16,x*16,null);
                else if (dots[x][y] == 2) g.drawImage(largefood,y*16-4,x*16-4,null);
            }
        }
    }
    
    /* when cat eats dot */ 
    public void eat (int x, int y){
        dots[x][y] = 0;
    }
    
    /* if all dots have been eaten */ 
    public boolean done(){
        boolean done = true;
        for (int x = 0; x < dots.length; x++){
            for (int y = 0; y < dots[0].length; y++){
                if (dots[x][y] != 0) done = false;
            }
        }
        return done; // used for leveling up
    }
}
