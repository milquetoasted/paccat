import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;
import java.util.Date; // for counting the time

public class Game
{
    // Declare variables
    static GUI window;
    static Grid grid;

    private Image ready; //"ready!" image
    private boolean waiting; //whether or not to display ready image
    
    private int level, lives, points, choice, highscore; //numbers in game
    
    //life forms
    private Predator[] p = new Predator[3]; //3 predators
    private Cat cat; //user
    private Frog frog;
    private Extra extra; //extra cat for mating
    
    private Sound sound = new Sound (); //for music

    public Game(){ 
        try{ready = ImageIO.read(new File("ready.png"));} catch(IOException ie){}; //Ready! Image

        level = 1; //Set/reset values
        lives = 3;
        points = 0;
        choice = -1;
        highscore = 0;
        grid = new Grid(level); //Load new grid and lifeforms
        p[0] = new Predator(17,14,1,grid);
        p[1] = new Predator(16,16,0,grid);
        p[2] = new Predator(17,18,3,grid);
        cat = new Cat(30,16,grid);
        frog = new Frog(grid);
        grid.directionpressed = -1;
        window = new GUI(this); //create GUI
    }

    /* paint drawarea */ 
    public void display (Graphics g){
        //Display the background and pellets
        grid.display(g);

        //Display the lifeforms
        if (!cat.dead()) cat.display(g);
        for (int x = 0; x < 3; x++) p[x].display(g);
        if (cat.dead()) cat.display(g);
        if (!frog.isgone()) frog.display(g);
        if (level > 1 && !extra.isgone()) extra.display(g); //Extra life cat only shows up after level 1
        if (waiting) g.drawImage(ready,212,305,null); //Displays ready before game starts to show that game has started

        //Display user statistics
        g.drawString("Level: "+level,10,528);
        g.drawString("Lives: "+lives,100,528);
        g.drawString("Score: "+points,190,528);
        if (points > highscore) highscore = points;
        g.drawString("High Score: "+highscore,280,528);
    }

    /* play, level up, etc */ 
    public void play(){
        sound.playSound (); // play sound
        
        //While the number of lives is greater than one and the user is not choosing to start a new game
        while (lives > 0 && choice != 0){ 
            while (lives > 0 && !grid.done() && choice != 0){
                advance(); //Keep advancing life forms on grid while alive and not finished dots
            }
            if (lives > 0 && choice != 0){ //Level up
                JOptionPane.showMessageDialog(null, "Level up!", "", JOptionPane.INFORMATION_MESSAGE);
                levelup();
            }
        }

        //If the lives are 0, the game ends
        if (lives == 0){
            JOptionPane.showMessageDialog(null, "You died!", "", JOptionPane.ERROR_MESSAGE); //died
            choice = JOptionPane.showOptionDialog(null, "New Game?", "", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, 0);
        }

        // If the user chooses to start a new game, reset the game
        if (choice == 0){
            reset(); //new game
            choice = -1;
        }
    }

    /* move life froms on the grid */ 
    public void advance(){
        wait(150); 
        window.repaint();
        cat.move(); //Move cat
        if (!frog.dead()){ //Frog
            frog.move();
            if (cat.pass(frog)){
                points = frog.die(points); //Cat eats frog and gets extra points
                window.repaint();
                wait(1000);
                frog.remove();
            }
        }
        if (level > 1 && !extra.dead()){ //Extra Life Cat
            extra.move();
            if (cat.pass(extra)){
                lives = extra.mate(lives); //cat mates with other cat and gains extra life
                window.repaint();
                wait(1000);
                extra.remove();
            }
        }
        if (grid.dots[cat.x][cat.y] == 2) //Large food wounds predators
            for (int x = 0; x < 3; x++)
            {
                p[x].setwounded(true);
                p[x].retime();
            }            
        if (grid.dots[cat.x][cat.y] != 0) points = cat.eat(points); //Eat any dot
        for (int x = 0; x < 3; x++){ // Predators
            p[x].move(); 
            if (cat.pass(p[x])){ //Predator and cat pass each other
                if (p[x].getwounded()){
                    points = p[x].die(points); //Predator dies if wounded
                    window.repaint();
                    wait(1000);
                    p[x].revive();
                    p[x].setlocation(16,16);
                }
                else{
                    lives = cat.die(lives); //Cat dies
                    window.repaint();
                    wait(1000);
                    cat.revive();
                    renew();
                }
            }
        }
    }

    public void setchoice(int c){ //For resetting the game
        choice = c;
    }

    /* move cat and predators back to inital positions */ 
    public void renew(){ 
        for (int x = 0; x < 3; x++) p[x].setwounded(false);
        p[0].setlocation(17,14);
        p[1].setlocation(16,16);
        p[2].setlocation(17,18);
        cat.setlocation(30,16);
        grid.directionpressed = -1;
        waiting = true; //show ready screen
        window.repaint();
        wait(1000);
        waiting = false;
    }

    /* move to next level */ 
    public void levelup(){
        level++;
        grid = new Grid(level%6); //Load new grid and lifeforms
        p[0] = new Predator(17,14,1,grid);
        p[1] = new Predator(16,16,1,grid);
        p[2] = new Predator(17,18,1,grid);
        cat = new Cat(30,16,grid);
        if (level > 1) extra = new Extra(grid);
        frog = new Frog(grid);
        grid.directionpressed = -1;
        waiting = true; //Show ready screen
        window.repaint();
        wait(1000);
        waiting = false;
        sound.playSound (); // play sound
    }

    /* start a new game */ 
    public void reset(){
        level = 0; //Reset values
        lives = 3;
        points = 0;
        choice = -1;
        levelup();
    }

    /* for delays */ 
    public void wait(int n){
        try{Thread.sleep(n);} catch(InterruptedException ie){};
    }

    /* main */ 
    public static void main (String[]args){
        //display instructions
        Image info = null;
        try{info = ImageIO.read(new File("Title.png"));} catch(IOException ie){};
        JLabel picLabel = new JLabel(new ImageIcon(info));
        JOptionPane.showMessageDialog(null, picLabel, "", JOptionPane.PLAIN_MESSAGE);
        
        Game game = new Game (); //Create game
        window.setVisible (true); //Set window properties
        window.setResizable(false);
        
        game.reset();
        while (game.choice != 1){
            game.play(); //Keep playing
        }
        window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING)); //close window
    }
}