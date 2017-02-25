/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package replica;

import java.util.ArrayList;


/**
 *
 * @author MSaqib
 */
public class DuplicateNode {
    int x;
    int y;
    int id;
    double angle;
    char name;
    boolean selected;
    ArrayList<Node> connectedTo;
    int radius;
    
    public DuplicateNode(Node n,char name){
        this.id = n.name;
        this.radius = 0;
        this.angle = 0.0;
        this.selected = false;
        this.x = (int)(400*Math.cos(Math.toRadians(0))) + 450;
        this.y = (int)(400*Math.sin(Math.toRadians(0))) + 450;         
        this.connectedTo = n.connectedTo;
        this.name = name;
    }
}
