/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package replica;

/**
 *
 * @author MSaqib
 */
public class DuplicateEdge {
    
    DuplicateNode src;
    DuplicateNode dstn;
    boolean animated;
    int x2;
    int y2;
    double angle;
    int radius;
    public DuplicateEdge(DuplicateNode n1,DuplicateNode n2){
        
        this.animated = false;
        this.src = n1;
        this.dstn = n2;
        this.x2 = this.src.x;
        this.y2 = this.src.y;
        this.angle = 0.0;
        this.radius = 0;
    }
} 
