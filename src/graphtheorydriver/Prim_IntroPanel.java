/*
 * Prim_IntroPanel.java
 * Louis Ritchie        louiscritchie@gmail.com         January 11th, 2015
 * 
 * This class gives a brief introduction to the application including
 * instructions, a overview of trees, and the uses of Prim's algorithm.
 */
package graphtheorydriver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Prim_IntroPanel extends JPanel {
    
    private JLabel readme, text1, text2, text3, text4, text5;
    //--------------------------------------------------------------------------
    // Constructor: Sets up the panel.
    //--------------------------------------------------------------------------
    public Prim_IntroPanel() {
        
        setLayout(new FlowLayout(FlowLayout.LEFT));
        readme = new JLabel("<html><b><u>INSTRUCTIONS:</b></u> <br><br>1. Using your mouse, click on "
                + "the empty space in the Graph tab to create dots that represent "
                + "the <i>vertices</i> of a graph. <br>2. Make a selection of these dots. Click"
                + " and drag this selection to an unselected vertex to create lines"
                + " connecting the dots, representing <i>edges</i>. <br>3. Select each edge and "
                + "type a number into the text field in the upper right. This number represents "
                + "the <i>weight</i> of that edge. <br>4. Finally, click the button "
                + "in the upper right that reads 'Find a Minimum Spanning Tree'. The "
                + "resulting graph is the output of Prim's Algorithm for Minimum Spanning Trees. <br><br></html>");
        text1 = new JLabel("<html>This application demonstrates a powerful, simple, and "
                + "well-studied algorithm known as <b>Prim's Algorithm.</b><br/></html>");
        text2 = new JLabel("<html>Prim's algorithm, when applied to a weighted simple graph -"
                + " containing no reflexive loops or paralell edges - generates"
                + " a Minimum Spanning Tree, or MST. <br/>The MST is a spanning subgraph of the"
                + " input graph, meaning that there is a path from any vertex"
                + " to every other, and every vertex in the "
                + "input graph is contained in the spanning tree.</html>");
        text3 = new JLabel("<html>A Minimum Spanning Tree is, as the name imples, a tree, "
                + "a heavily studied mathematical object from the field of Graph "
                + "Theory. <br/>The study of trees allows us to search through large "
                + "databases efficiently, to create efficient trucking routes "
                + "across continents, and to create the most efficient network "
                + "pathways. <br/The last two examples use Minimum Spanning Trees. </html>");
        text4 = new JLabel("<html>Imagine that there are 8 buildings on a given University "
                + "campus that are connected by a high speed network. <br/>Due to "
                + "financial concerns, some of the connections between old "
                + "buildings have not been upgraded and are much slower than "
                + "the connections between newer buildings. <br/>At what point does "
                + "routing through new buildings become faster than using the "
                + "slower, but more direct line?</html>");
        text5 = new JLabel("<html>Prim's Algorithm will find the answer! On the next page, "
                + "create your own network. <br/>Draw dots as buildings and connect "
                + "them with edges. Then select each edge and add a weight. <br/>Once "
                + "you are ready, select 'Find a Minimum Spanning Tree' and see "
                + "the result of Prim's Algorithm on your graph!</html>");
        
        text1.setHorizontalAlignment(SwingConstants.LEFT);
        text2.setHorizontalAlignment(SwingConstants.LEFT);
        text3.setHorizontalAlignment(SwingConstants.LEFT);
        text4.setHorizontalAlignment(SwingConstants.LEFT);
        text5.setHorizontalAlignment(SwingConstants.LEFT);
        add(readme);
        add(text1);
        add(text2);
        add(text3);
        add(text4);
        add(text5);
        
        setPreferredSize(new Dimension(1200, 600));
    }
    
}
