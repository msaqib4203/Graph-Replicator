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
public class Edge {
    int x1;
    int y1;
    int x2;
    int y2;
    Node src;
    Node dstn;
    
    public Edge(Node n1,Node n2){
        this.x1 = n1.x;
        this.y1 = n1.y;
        this.x2 = n2.x;
        this.y2 = n2.y;
        this.src = n1;
        this.dstn = n2;
    }
    public Edge(Node n1,int x2,int y2){
        this.x1 = n1.x;
        this.y1 = n1.y;
        this.x2 = x2;
        this.y2 = y2;
        this.src = n1;
    }
}
