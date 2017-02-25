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
public class Node {
        int x;
        int y;
        int name; 
        int radius;
        boolean selected;
        ArrayList<Node> connectedTo;
    
        public Node(int x,int y,int name){
            this.x = x;
            this.y = y;
            this.name = name;
            this.radius = 0;
            this.selected = false;
            this.connectedTo = new ArrayList<>();
        }
}
