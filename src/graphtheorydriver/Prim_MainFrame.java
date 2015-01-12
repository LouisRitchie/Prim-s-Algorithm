/*
 * Prim_MainFrame.java
 * Louis Ritchie        louiscritchie@gmail.com         January 11th, 2015
 * 
 * This is the JFrame class for the Prims_Algorithm application. A tabbed pane
 * sets up the two content panels, found in the Prim_IntroPanel.java and
 * Prim_GraphPanel.java classes.
 */
package graphtheorydriver;

import javax.swing.*;

public class Prim_MainFrame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Prim's Algorithm for Minimum Spanning Trees");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JTabbedPane tp = new JTabbedPane();
        tp.addTab("Intro", new Prim_IntroPanel());
        tp.addTab("Graph", new Prim_GraphPanel());
        
        frame.getContentPane().add(tp);
        
        frame.pack();
        frame.setVisible(true);
    }
    
}
