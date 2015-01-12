/*
This class manages the user-created edges in the Graph Theory application.
 */
package graphtheorydriver;

import java.awt.*;
import java.awt.Font;
import java.awt.Polygon;

public class graphTheoryEdge {
    
    private graphTheoryVertex start, end;
    
    private double xMidpoint, yMidpoint, angle;
    
    private int weight = 1;
    
    private Polygon area;
    
    private Boolean selected = false, showSelectionArea = false,
            MSTcolour = false;
    
    private Font myFont = new Font("Serif", Font.BOLD, 12);
    
    //--------------------------------------------------------------------------
    // Constructor: instantiates the edge using both a starting and ending 
    // graphTheoryVertex object. Also creates diamond-shaped polygon along
    // the edge that provides area for the user to select the edge and assign
    // a weight.
    //--------------------------------------------------------------------------
    public graphTheoryEdge(graphTheoryVertex v, graphTheoryVertex w) {
        if (!(v.equals(w))) {
            if (v.getX() < w.getX()) {// assigns leftmost vertex to start
                start = v;
                end = w;
            }
            else {
                start = w;
                end = v;
            }
        }
        
        if (end.getX() == start.getX()) // avoids dividing by zero on line 46.
            end.setCoordinates(end.getX() + 1, end.getY());
        
        xMidpoint = start.getX() + ((end.getX() - start.getX()) / 2.0);
        yMidpoint = start.getY() + ((end.getY() - start.getY()) / 2.0);
        angle = (Math.PI / 2.0) - (Math.atan((end.getY() - start.getY()) / 
                (end.getX() - start.getX()))); 
        
        // When the angle is a multiple of pi/3, the selection area is too small
        // so this code alters the angle when it's within those bounds.
        double negLow, negUp, posLow, posUp;
        negLow = (Math.PI) / (-3.0) - 0.3;
        negUp = (Math.PI) / (-3.0) + 0.3;
        posLow = (Math.PI) / 3.0 - 0.3;
        posUp = (Math.PI) / 3.0 + 0.3;
        
        if (((negLow < angle) && (negUp > angle)) || ((posLow < angle) && (posUp > angle)))
            angle =+ 0.5;
        
        int[] xpoints = new int[4];
        int[] ypoints = new int[4];
        int npoints = 4;
        xpoints[0] = start.getX();
        xpoints[1] = (int)((10 * Math.cos(angle)) + xMidpoint);
        xpoints[2] = end.getX();
        xpoints[3] = (int)(((-10) * Math.cos(angle)) + xMidpoint);
        ypoints[0] = start.getY();
        ypoints[1] = (int)((10 * Math.sin(angle)) + yMidpoint);
        ypoints[2] = end.getY();
        ypoints[3] = (int)(((-10) * Math.sin(angle)) + yMidpoint);
        
        area = new Polygon(xpoints, ypoints, npoints); 
    }
    
    //--------------------------------------------------------------------------
    // Compares an input vertex to the start and end vertices of the edge.
    //--------------------------------------------------------------------------
    public Boolean checkEndpoints(graphTheoryVertex v) {
        if (v.equals(start) || v.equals(end))
            return true;
        return false;
    }
    
    //--------------------------------------------------------------------------
    // Compares two input vertices to the start and end vertices of the edge.
    //--------------------------------------------------------------------------
    public Boolean checkEndpoints(graphTheoryVertex v, graphTheoryVertex w) {
        if ((v.equals(start) && w.equals(end)) || ((v.equals(end) && w.equals(start))))
            return true;
        return false;
    }
    
    //--------------------------------------------------------------------------
    // Returns the other endpoint when one endpoint is input. The input MUST be
    // an endpoint of the edge for this method to function as intended.
    //--------------------------------------------------------------------------
    public graphTheoryVertex getOtherEndpoint(graphTheoryVertex v) {
        if (start.equals(v))
            return end;
        else
            return start;
    }
    
    //--------------------------------------------------------------------------
    // Returns the starting (leftmost) vertex object of the edge.
    //--------------------------------------------------------------------------
    public graphTheoryVertex getStart() {
        return start;
    }
    
    //--------------------------------------------------------------------------
    // Returns the ending (rightmost) vertex object of the edge.
    //--------------------------------------------------------------------------
    public graphTheoryVertex getEnd() {
        return end;
    }
    
    //--------------------------------------------------------------------------
    // 
    //--------------------------------------------------------------------------
    public int getWeight() {
        return weight;
    }
    
    //--------------------------------------------------------------------------
    // Returns x coordinate of the midpoint of the edge.
    //--------------------------------------------------------------------------
    public int getMidX() {
        return (int) xMidpoint;
    }
    
    //--------------------------------------------------------------------------
    // Returns y coordinate of the midpoint of the edge.
    //--------------------------------------------------------------------------
    public int getMidY() {
        return (int) yMidpoint;
    }
    
    //--------------------------------------------------------------------------
    // Determines is a point is within the pre-defined selection area.
    //--------------------------------------------------------------------------
    public Boolean isNearTo(Point p) {
        if (area.contains(p.x, p.y))
            return true;
        else
            return false;
    }
    
    //--------------------------------------------------------------------------
    // Compares this edge to each element of input array and returns true if
    // this edge exists within that array.
    //--------------------------------------------------------------------------
    public Boolean isWithin(graphTheoryEdge[] e) {
        int index = e.length;
        for (int count=0; count < index; count++)
            if (this.equals(e[count]))
                return true;
        return false;
    }
    
    //--------------------------------------------------------------------------
    // Boolean input decides whether or not the edge is selected.
    //--------------------------------------------------------------------------
    public void selected(Boolean b) {
        selected = b;
    }
    
    //--------------------------------------------------------------------------
    // Returns true if the edge is currently selected.
    //--------------------------------------------------------------------------
    public Boolean isSelected() {
        if (selected)
            return true;
        else
            return false;
    }
    
    //--------------------------------------------------------------------------
    // Toggles the visibility of the edge's selection area polygon.
    //--------------------------------------------------------------------------
    public void showSelectionArea() {
        if (showSelectionArea)
            showSelectionArea = false;
        else
            showSelectionArea = true;
    }
    
    //--------------------------------------------------------------------------
    // Chooses the visibility of the edge's selection area polygon with boolean.
    //--------------------------------------------------------------------------
    public void showSelectionArea(Boolean b) {
        showSelectionArea = b;
    }
    
    //--------------------------------------------------------------------------
    // Returns state of boolean showSelectionArea.
    //--------------------------------------------------------------------------
    public boolean isShowingArea() {
        return showSelectionArea;
    }
    
    //--------------------------------------------------------------------------
    // Changes the edge graphic to distinguish when an MST is created.
    //--------------------------------------------------------------------------
    public void MSTcolour(Boolean b) {
        MSTcolour = b;
    }
    
    //--------------------------------------------------------------------------
    // Sets the weight of the edge.
    //--------------------------------------------------------------------------
    public void setWeight(int a) {
        weight = a;
    }
    //--------------------------------------------------------------------------
    // Manages the drawing of the edge on a panel.
    //--------------------------------------------------------------------------
    public void draw(Graphics page) {    
        page.setFont(myFont);
        
        if (MSTcolour) {
            page.setColor(Color.GREEN);
            page.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
            page.setColor(Color.MAGENTA);
            page.drawString(("" + weight), (int) (xMidpoint - 7), (int) yMidpoint);
            return;
        }
        
        if (showSelectionArea) {
            int[] xpoints = new int[4];
            int[] ypoints = new int[4];
            int npoints = 4;
            xpoints[0] = start.getX();
            xpoints[1] = (int)((10 * Math.cos(angle)) + xMidpoint);
            xpoints[2] = end.getX();
            xpoints[3] = (int)(((-10) * Math.cos(angle)) + xMidpoint);
            ypoints[0] = start.getY();
            ypoints[1] = (int)((10 * Math.sin(angle)) + yMidpoint);
            ypoints[2] = end.getY();
            ypoints[3] = (int)(((-10) * Math.sin(angle)) + yMidpoint);
            if (selected) {
            page.setColor(Color.PINK);
            page.fillPolygon(xpoints, ypoints, npoints);
            }
            else {
            page.setColor(Color.CYAN);
            page.fillPolygon(xpoints, ypoints, npoints);
            }    
        }
        
        if (selected) {
            page.setColor(Color.RED);
            page.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
        }
        else {
            page.setColor(Color.BLUE);
            page.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
        }
        
        page.setColor(Color.BLACK);
        page.drawString(("" + weight), (int) (xMidpoint - 7), (int) yMidpoint);
    }
}
