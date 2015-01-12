/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphtheorydriver;

import javax.swing.*;
/**
 *
 * @author louis_c_ritchie
 */
public class GraphTheoryDriver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Prim's Algorithm for Minimum Spanning Trees");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JTabbedPane tp = new JTabbedPane();
        tp.addTab("Intro", new IntroPanel());
        tp.addTab("Graph", new graphTheoryPanel());
        
        frame.getContentPane().add(tp);
        
        frame.pack();
        frame.setVisible(true);
    }
    
}
