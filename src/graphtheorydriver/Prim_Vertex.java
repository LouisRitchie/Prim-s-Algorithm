/*
 * Prim_Vertex.java
 * Louis Ritchie        louiscritchie@gmail.com         January 11th, 2015
 * 
 * This class manages the vertices created in the Prim_GraphPanel class. The
 * methods of this class are versatile, and can search Prim_Edge[] objects to
 * find which edge connects two vertices. 
 */
package graphtheorydriver;

import java.awt.*;

public class Prim_Vertex {
    
    private int xCoordinate, yCoordinate;
    
    private final int SIZE = 8; // Diameter of unselected vertex
    
    private final int SELECTED_SIZE = 12; // Diameter of selected vertex
    
    private Boolean selected = false; // True when currently selected by user
    
    //--------------------------------------------------------------------------
    //  Constructor: uses x, y parameters to instantiate the vertex object
    //--------------------------------------------------------------------------
    public Prim_Vertex(int x, int y) {
        xCoordinate = x;
        yCoordinate = y;
    }
    
    //--------------------------------------------------------------------------
    //  Returns x coordinate.
    //--------------------------------------------------------------------------
    public int getX() {
        return xCoordinate;
    }
    
    //--------------------------------------------------------------------------
    //  Returns y coordinate.
    //--------------------------------------------------------------------------
    public int getY() {
        return yCoordinate;
    }
    
    //--------------------------------------------------------------------------
    // Returning the weight of two connecting edges is a little tricky, since
    // weights are stored in the edge class, not the vertex class.
    //
    // This method is called by the current vertex of Prim's algorithm. With a
    // for loop iterating through the adjacent vertices, it produces the weight
    // of connecting those two vertices, allowing us to select the best edge.
    //--------------------------------------------------------------------------
    public int getWeight(Prim_Edge[] e, Prim_Vertex v) {
        if (v != null) {
            boolean matchFound = false;
            int count = 0;
            while (!matchFound) {
                if (e[count].checkEndpoints(this, v)) {
                    matchFound = true;
                    return e[count].getWeight();
                }
                count++;
            }
        }
        return 1000;
    }
    
    //--------------------------------------------------------------------------
    // Retuns the edge from the array that adjoins this vertex and the input
    // vertex. Such an edge must exist or method will create NullPointerExcep.
    //--------------------------------------------------------------------------
    public Prim_Edge getEdge(Prim_Edge[] e, Prim_Vertex v) {
        Prim_Edge match = null;
        boolean matchFound = false;
        int count = 0;
        while ((!matchFound) && (count < e.length)) {
            if (e[count].checkEndpoints(this, v)) {
                match = e[count];
                matchFound = true;
            }
            count++;
        }
        return match;
    }
    
    //--------------------------------------------------------------------------
    //  Sets the x and y coordinates.
    //--------------------------------------------------------------------------
    public void setCoordinates(int x, int y) {
        xCoordinate = x;
        yCoordinate = y;
    }
    
    //--------------------------------------------------------------------------
    //  selected - No Parameter
    //  Manages the vertex's inclusion or exclusion from the user selection.
    //  Each time the method is called, the Boolean is flipped t->f or f->t.
    //--------------------------------------------------------------------------
    public void selected() {
        if (selected)
            selected = false;
        else
            selected = true;
    }
    
    //--------------------------------------------------------------------------
    //  selected - Boolean Parameter
    //  Manages the vertex's inclusion or exclusion from the user selection.
    //  A Boolean parameter determines whether the vertex is included or not.
    //--------------------------------------------------------------------------
    public void selected(Boolean b) {
        selected = b;
    }
    
    //--------------------------------------------------------------------------
    //  Returns a Boolean indicating inclusion or exclusion from user selection.
    //--------------------------------------------------------------------------
    public Boolean isSelected() {
        return selected;
    }
    
    //--------------------------------------------------------------------------
    //  Calculates whether a point is reasonably near the vertex. Used to aid
    //  in deciding if the user meant to click on the vertex or not.
    //--------------------------------------------------------------------------
    public boolean isNearTo(Point p) {
        boolean near = false;
        int lowXbound = (xCoordinate - 20);
        int upXbound = (xCoordinate + 20);
        int lowYbound = (yCoordinate - 20);
        int upYbound = (yCoordinate + 20);
        if (((p.x >= lowXbound) && (p.x <= upXbound)) && ((p.y >= lowYbound) && 
                (p.y <= upYbound))) {
            near = true;
        }
        return near;
    }
    
    //--------------------------------------------------------------------------
    //  Manages the drawing of this vertex on a panel, with  a different drawing
    //  if the vertex is currently in the user's selection.
    //--------------------------------------------------------------------------
    public void draw(Graphics page) {
        if (selected) {
            page.setColor(Color.red);
            page.fillOval(xCoordinate, yCoordinate, SELECTED_SIZE, 
                    SELECTED_SIZE);
        }
        else {
            page.setColor(Color.lightGray);
            page.fillOval(xCoordinate, yCoordinate, SIZE, SIZE);
        }
    } 
    
    
}
