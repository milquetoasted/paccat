/** Hannah He, Lila Huang, Lindsey Jin
 * ICS4U
 * January 26, 2016
 * Display, buttons, keybinding*/

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;  //for ActionListener
import java.io.*;

import javax.swing.KeyStroke; //for key bindings
import javax.swing.AbstractAction;
import javax.swing.Action;

public class GUI extends JFrame
{
    // Instance variables
    private Game game; 

    private String[] dir = {"W","D","S","A"}; //keys for directions

    public GUI(Game m){
        game = m;

        // button and listener
        BtnListener btnListener = new BtnListener (); 
        JButton newBtn = new JButton ("New Game");
        newBtn.addActionListener (btnListener);

        // create content pane, set layout
        JPanel content = new JPanel ();        // Create a content pane
        content.setLayout (new BorderLayout ()); // Use BorderLayout for panel
        JPanel south = new JPanel ();
        south.setLayout (new FlowLayout ()); // Use FlowLayout for input area

        DrawArea board = new DrawArea (512,538);

        // key bindings ----- W is up, A is left, S is down, and D is right
        Action upAction = new AbstractAction(){public void actionPerformed(ActionEvent e){game.grid.directionpressed = 0;}};
        board.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"),"upAction");
        board.getActionMap().put("upAction",upAction);

        Action rightAction = new AbstractAction(){public void actionPerformed(ActionEvent e){game.grid.directionpressed = 1;}};
        board.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"),"rightAction");
        board.getActionMap().put("rightAction",rightAction);

        Action downAction = new AbstractAction(){public void actionPerformed(ActionEvent e){game.grid.directionpressed = 2;}};
        board.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"),"downAction");
        board.getActionMap().put("downAction",downAction);

        Action leftAction = new AbstractAction(){public void actionPerformed(ActionEvent e){game.grid.directionpressed = 3;}};
        board.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"),"leftAction");
        board.getActionMap().put("leftAction",leftAction);

        // add the components to the input area.
        south.add (newBtn);
        content.add (south, "South");
        content.add (board, "North");

        // set this window's attributes.
        setContentPane (content);
        pack ();
        setTitle ("meow");
        setSize(518,599);
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo (null);           // Center window.
    }

    class BtnListener implements ActionListener 
    {
        public void actionPerformed (ActionEvent e){ // New Game Button
            game.setchoice(0); 
        }
    }

    class DrawArea extends JPanel
    {
        public DrawArea (int width, int height){ // Creates draw area
            this.setPreferredSize (new Dimension (width, height)); // size
        }

        public void paintComponent (Graphics g){
            game.display(g); // Displays game
        }
    }
}
