/*
 * Prim_GraphPanel.java
 * Louis Ritchie        louiscritchie@gmail.com         January 11th, 2015
 * 
 * This class controls the main user interface of this application. Vertices,
 * edges, and weights are managed by various listeners. This class also contains
 * the code for Prim's algorithm.
 */
package graphtheorydriver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Prim_GraphPanel extends JPanel {

    // array of Prim_Vertex objects
    private final Prim_Vertex[] vertices = new Prim_Vertex[100];
    
    // array of Prim_Edge objects. Not final, as Prim's algorithm changes
    // this.
    private Prim_Edge[] edges = new Prim_Edge[100];
    
    private int vCount = 0, eCount = 0; // counts number of vertices, edges
    
    private int next = 1;
    
    private boolean selectionEmpty = true, userDragging = false,
            edgeSelected = false, userClickedAway = true;
    
    private Point dragPoint = null, releasePoint = null,
            pressPoint = null;
    
    private JButton clear, clearEdges, showSelectionArea, prim;
    
    private JTextField weight;
    
    private JLabel weightPrompt;
    
    private Font myFont = new Font("Serif", Font.BOLD, 14);
    //--------------------------------------------------------------------------
    //  Constructor: sets up the panel.
    //--------------------------------------------------------------------------
    public Prim_GraphPanel() {
        
        setLayout(new FlowLayout(FlowLayout.LEFT));
        
        CoordinatesListener listener = new CoordinatesListener();
        addMouseListener(listener);
        addMouseMotionListener(listener);
        
        ButtonListener buttonListener = new ButtonListener();
        clear = new JButton("Clear Vertices and Edges");
        clear.addActionListener(buttonListener);
        clearEdges = new JButton("Clear Edges");
        clearEdges.addActionListener(buttonListener);
        showSelectionArea = new JButton("Show Edge Selection Area");
        showSelectionArea.addActionListener(buttonListener);
        prim = new JButton("Find a Minimum Spanning Tree!");
        prim.addActionListener(buttonListener);
        
        weightPrompt = new JLabel("Select an edge. ");
        
        KeyListener keyListener = new KeyListener();
        weight = new JTextField(5);
        weight.addActionListener(keyListener);
        
        setBackground(Color.white);
        setPreferredSize(new Dimension(1200, 600));
        
        add(prim);
        add(clear);
        add(clearEdges);
        add(showSelectionArea);

        add(weightPrompt);
        add(weight);
    }
    
    //--------------------------------------------------------------------------
    // Generates a Minimum Spanning Tree using Prim's algorithm. 
    //--------------------------------------------------------------------------
    public void PrimMST() {
        int startV = (int) (Math.random() * vCount); // random start vertex
        int iterations = 0, fCount = 0, action, MSTvCount = 0, nonMSTvCount = 0;
        Prim_Vertex currentV = vertices[startV], v, a, b;
        Prim_Edge bestEdge = null, e; // least weight edge of fringe
        
        // arrays keep track of the tree's vertices, edges, and fringe edges.
        // The only constant array is MSTedges.
        Prim_Vertex[] MSTv = new Prim_Vertex[1000];
        Prim_Vertex[] nonMSTv = new Prim_Vertex[1000];
        Prim_Edge[] MSTe = new Prim_Edge[vCount - 1];
        Prim_Edge[] fringe = new Prim_Edge[1000];
        
        // While loop is based on the property of trees that there is one less
        // edge than there are vertices. T(v, e): |{e}| = |{v}| - 1
        while (iterations < vCount - 1) {
        action = 1; // allows first iteration to bypass array re-config as
                    // MSTe array is empty on first iteration.
        
        fringe = new Prim_Edge[1000];
        fCount = 0;
        MSTv = new Prim_Vertex[1000];
        MSTvCount = 0;
        nonMSTv = new Prim_Vertex[1000];
        nonMSTvCount = 0;
        
        if (iterations == 0) {
            action = 0;
            for (int count=0; count < eCount; count++) {
                if (edges[count].checkEndpoints(currentV)) {
                    v = edges[count].getOtherEndpoint(currentV);
                    fringe[fCount] = currentV.getEdge(edges, v);
                    fCount++;
                }
            }
        }
        
        // On any iteration besides the first, each array is re-configured based
        // on the contents of the MSTe array.
        switch (action) {
            case 1:
                
                // reconfigures the MSTv array to contain all endpoints of the
                // edges of the tree.
                MSTv[MSTvCount] = MSTe[0].getStart();
                MSTvCount++;
                MSTv[MSTvCount] = MSTe[0].getEnd();
                MSTvCount++;
                for (int count1=1; count1 < iterations; count1++) {
                    a = MSTe[count1].getStart();
                    b = MSTe[count1].getEnd();
                    boolean adda, addb;
                    adda = true; 
                    addb = true;
                    for (int count2=0; count2 <= iterations; count2++) {    
                        if (MSTv[count2] != null) {
                            if (MSTv[count2].equals(a))
                                adda = false;
                            if (MSTv[count2].equals(b))
                                addb = false;
                        }
                    }
                    if (adda) {
                        MSTv[MSTvCount] = a;
                        MSTvCount++;
                    }
                    if (addb) {
                        MSTv[MSTvCount] = b;
                        MSTvCount++;
                    }
                }
                
                // reconfigures the nonMSTv array to contain all vertices not
                // contained in MSTv.
                for (int count1=0; count1 < vCount; count1++) {
                    boolean addMe = true;
                    for (int count2=0; count2 < MSTvCount; count2++) {
                        if (vertices[count1].equals(MSTv[count2]))
                            addMe = false;
                    }
                    if (addMe) {
                        nonMSTv[nonMSTvCount] = vertices[count1];
                        nonMSTvCount++;
                    }
                }
                
                // reconfigures the fringe array to contain edges that connect
                // a vertex of MSTv to a vertex of nonMSTv.
                for (int count1=0; count1 < eCount; count1++) {
                    e = edges[count1];
                    for (int count2=0; count2 < MSTvCount; count2++) {
                        a = MSTv[count2];
                        for (int count3=0; count3 < nonMSTvCount; count3++) {
                            b = nonMSTv[count3];
                            if (e.checkEndpoints(a, b)) {
                                fringe[fCount] = a.getEdge(edges, b);
                                fCount++;
                                
                            }
                        }
                    }
                }
                break;
            
            default:
                break;
        }
        
        // picks an arbitrary element of fringe array and compares it to all the
        // others to find the edge of least weight.
        bestEdge = fringe[0];
        for (int count=0; count < fCount; count++) {
            if (bestEdge.getWeight() > fringe[count].getWeight())
                bestEdge = fringe[count];
        }
            
        MSTe[iterations] = bestEdge;
        iterations++;
        }
        
        // upon completion of the while loop, the edge array is replaced with
        // the edges of the tree, and their graphics are altered.
        edges = MSTe;
        eCount = vCount - 1;
        for (int count = 0; count < eCount; count++)
            edges[count].MSTcolour(true);
        repaint();
    } 
    
    //--------------------------------------------------------------------------
    //  Draws the vertices, edges, and quantity of each.
    //--------------------------------------------------------------------------
    public void paintComponent(Graphics page) {
        super.paintComponent(page);
        page.setColor(Color.red);
        
        for (int count=0; count < vCount; count++)  // draws all vertices
            vertices[count].draw(page);    
        
        for (int count=0; count < eCount; count++)  // draws all edges
            edges[count].draw(page);
        
        // draws a line from selected vertex to mouse to aid in adding edges
        if (dragPoint != null) {
            for (int count=0; count < vCount; count++)
                if (vertices[count].isSelected()) 
                    page.drawLine(vertices[count].getX(), 
                    vertices[count].getY(), dragPoint.x, dragPoint.y);
        }
        
        page.setFont(myFont);
        page.setColor(Color.red);
        page.drawString("Leave no vertex behind! Prim's Algorithm "
                + "is built for connected graphs.", 5, 50);
    }
    
    //**************************************************************************
    //  Represents the listener for mouse events.
    //**************************************************************************
    private class CoordinatesListener implements MouseListener,
                                                 MouseMotionListener {
        
        //----------------------------------------------------------------------
        // This method handles vertex creation, as well as vertex and edge 
        // selection and deselection; the powerhouse of the UI.
        //----------------------------------------------------------------------
        public void mousePressed(MouseEvent event) {
            pressPoint = event.getPoint();
            
            // multiple for loops examine each element of vertices[] and edges[]
            // to determine if this mouse press will create a vertex, add to the
            // current selection, or clear the current selection. The booleans
            // act as switches.
            selectionEmpty = true;
            userClickedAway = true;
            edgeSelected = false;
            
            for (int count=0; count < eCount; count++)
                edges[count].selected(false);
            
            for (int count=0; count < vCount; count++) 
                if (vertices[count].isNearTo(pressPoint)) {
                    vertices[count].selected(true);
                    selectionEmpty = false;
                    userClickedAway = false;
                    edgeSelected = false;
                }
            
            for (int count=0; count < eCount; count++) {
                if ((edges[count].isNearTo(pressPoint)) && (selectionEmpty)) {
                    edges[count].selected(true);
                    userClickedAway = false;
                    edgeSelected = true;
                }
            }
            
            if (edgeSelected)
                weightPrompt.setText("Enter a weight: ");
            else
                weightPrompt.setText("Select an edge. ");
            
            switch (next) {
                
                case 1: 
                    if ((selectionEmpty) && (!edgeSelected)) {
                        Prim_Vertex v = new Prim_Vertex(pressPoint.x, pressPoint.y);
                        vertices[vCount] = v;
                        vCount++;
                        next = 1;
                    }
                    else
                        next = 2;
                    break;
                    
                case 2:
                    if (userClickedAway) {
                        edgeSelected = false;
                        for (int count=0; count < vCount; count++) 
                            vertices[count].selected(false);
                        for (int count=0; count < eCount; count++) 
                            edges[count].selected(false);
                        next = 1;
                        }
                    else
                        next = 2;  
                    break;
                    
                default:
                    next = 1;
                }
            repaint();
        }
        
        //----------------------------------------------------------------------
        // This method handles the rubber band visual for edge creation.
        //----------------------------------------------------------------------
        public void mouseDragged(MouseEvent event) {
            if (!selectionEmpty) {
                dragPoint = event.getPoint();
                userDragging = true;
            }
            repaint();
        }
        
        //----------------------------------------------------------------------
        // The following method creates an edge when a selection has been made
        // and the mouse is dragged & released on an unselected vertex. The
        // selection is then "emptied".
        //----------------------------------------------------------------------
        public void mouseReleased(MouseEvent event) {
            releasePoint = event.getPoint();
            if ((!selectionEmpty) && (userDragging)) {
                for (int count=0; count < vCount; count++) {
                    if (vertices[count].isNearTo(releasePoint) && 
                            !(vertices[count].isSelected())) {
                        for (int index=0; index < vCount; index++) {
                            if (vertices[index].isSelected()) {
                                Prim_Edge e = new 
                                Prim_Edge(vertices[index], vertices[count]);
                                edges[eCount] = e;
                                eCount++;
                                vertices[index].selected();
                            }
                        }
                    }
                }
            dragPoint = null;
            userDragging = false;
            repaint();
            }
        }
        
        //  Provide empty definitions for unused methods.
        public void mouseClicked (MouseEvent event) {}
        public void mouseEntered(MouseEvent event) {}
        public void mouseExited(MouseEvent event) {}
        public void mouseMoved(MouseEvent event) {}
    }
    
    //**************************************************************************
    //  Represents the listener for button events.
    //**************************************************************************
    private class ButtonListener implements ActionListener {
        public void actionPerformed (ActionEvent event) {
            if (event.getSource() == showSelectionArea) {
                if (edges[0].isShowingArea()) {
                for (int count = 0; count < eCount; count++)
                        edges[count].showSelectionArea(false);
                }
                else {
                    for (int count = 0; count < eCount; count++)
                    edges[count].showSelectionArea(true);
                }  
                repaint();
                return;
            }

            if (event.getSource() == prim) {
                PrimMST();
                repaint();
                return;
            }
            
            for (int count=0; count < eCount; count++)
                    edges = new Prim_Edge[100];
                eCount = 0;
            if (event.getSource() == clear) {
                for (int count=0; count < vCount; count++) 
                    vertices[count] = null;
                vCount = 0;
            }
            repaint();
        }
    }
    
    //**************************************************************************
    //  Represents the listener for keyboard events.
    //**************************************************************************
    private class KeyListener implements ActionListener {
        public void actionPerformed (ActionEvent event) {
            int w;
            String text = weight.getText();
            w = Integer.parseInt(text);
            
            for (int count=0; count < eCount; count++) {
                if (edges[count].isSelected()) {
                    edges[count].setWeight(w);
                    edges[count].selected(false);
                }
            }
            edgeSelected = false;
            repaint();
        }
    }
}

