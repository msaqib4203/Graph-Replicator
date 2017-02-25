/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package replica;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.lang.Math.*;
import java.util.ArrayList;


/**
 *
 * @author MSaqib
 */
public class Panel2 extends javax.swing.JPanel {

    /**
     * Creates new form Panel2
     */
    public Panel2() {
        initComponents();
    }
   
   
    public static ArrayList<DuplicateNode> duplicateNodes = new ArrayList<>();
    public static ArrayList<DuplicateEdge> duplicateEdges = new ArrayList<>();
    public Color nodeColor = new Color(219,127,241);
    public Color nodeBorder = new Color(177,25,214);
    static char NAME = 'A';
    static boolean started = true;
    boolean growing = false;
    int XX=0, YY=0;
    int DIVISION = 0;
    static int currentsize = 0;
 
   public boolean existingAlready(int id){
       for(DuplicateNode dn : duplicateNodes){
           if(dn.id == id){
               return true;
           }
       }
       return false;
   }
   
   public void startTraversal(Node starter){
        if(!existingAlready(starter.name)){
            duplicateNodes.add(new DuplicateNode(starter,NAME++));
            startDrawing();
        }
        else
            return;
 
       for(Node n: starter.connectedTo){
           startTraversal(n);
       }
   }
   
    public Timer gap2  = new Timer(500,new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            gap2.stop();
            grow.start();
        }
    });
   
   
   public Timer grow = new Timer(10,new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            growing = true;
            DuplicateNode dn  = duplicateNodes.get(currentsize-1);
                    
            if(dn.radius < 40){
               dn.radius+=4;
               repaint();
           }
           else{
            growing = false;
            grow.stop();
            repaint();
            gap3.start();
           }
        }
    });
   
  
   
    Timer gap = new Timer(2500,new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            if(currentsize<duplicateNodes.size()){
                currentsize++;
            }
            else{
                started = false;
                gap.stop();
                return;
            }
            gap.stop();
        DIVISION = 360/currentsize;
        animate.start();
        
        }
    });
    
    
     public Timer gap3  = new Timer(1000,new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            if(currentsize>1){
               DuplicateNode dn = duplicateNodes.get(currentsize-1);
                for(Node n: dn.connectedTo){
                    for(int i=0;i<currentsize;i++){
                        if(duplicateNodes.get(i).id == n.name){
                            DuplicateEdge fresh = new DuplicateEdge(dn,duplicateNodes.get(i));
                            if(dn.x == duplicateNodes.get(i).x)
                                fresh.angle = Math.PI/2 +Math.PI;
                            else{
                                fresh.angle = (dn.y*1.0 - duplicateNodes.get(i).y*1.0)/(dn.x - duplicateNodes.get(i).x);
                                fresh.angle = Math.atan(fresh.angle)+Math.PI; 
                            }
                            duplicateEdges.add(fresh);
                            
                        }
                    }
                }
               }
        gap3.stop();
        drawLine.start();
        }
    });
   
      Timer drawLine = new Timer(1,new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
          boolean completed = true;
          for(DuplicateEdge  de : duplicateEdges){
              if((de.radius*de.radius)<(((de.src.x-de.dstn.x)*(de.src.x-de.dstn.x))+(de.src.y-de.dstn.y)*(de.src.y-de.dstn.y))){
                  completed = false;
                  de.radius+=5;
                  de.x2 =(int)( de.dstn.x - de.radius*Math.cos(de.angle));
                  de.y2 = (int)(de.dstn.y - de.radius*Math.sin(de.angle));
                  //System.err.println(de.angle);
                  //System.out.println(de.x2+" "+de.y2+"----"+de.dstn.x+" "+de.dstn.y+"----"+de.src.x+" "+de.src.y);
              }
              else{
                  de.x2 = de.src.x;
                  de.y2 = de.src.y;
                  de.animated = true;
              }
          }
          if(completed){
              drawLine.stop();
                gap.start();
          }
          repaint();
        
        }
    });
   
   
   Timer animate = new Timer(5,new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            boolean Complete = true;
            for(int i=0;i<currentsize;i++){
                DuplicateNode current = duplicateNodes.get(i);
                if(current.angle<(DIVISION*(currentsize-i-1))){
                    Complete = false;
                    current.angle+=1;
                    current.x = (int)(400*Math.cos(Math.toRadians(current.angle))) + 450;
                    current.y = (int)(400*Math.sin(Math.toRadians(current.angle))) + 450;
                    repaint();
                }
            }
           
           if(Complete){
               animate.stop();     
               gap2.start();
               
           }
        }
    });
    
      void startDrawing(){
          gap.start();
    }
    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs); //To change body of generated methods, choose Tools | Templates.
        Graphics2D g = (Graphics2D)grphcs;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g.setStroke(new BasicStroke(3));
        g.setColor(new Color(234,247,235));
        g.drawOval(50,50, 800, 800);
        g.setColor(new Color(167,11,65));
        
        try{
            for(DuplicateEdge de:duplicateEdges){
                if(de.animated)
                     g.drawLine(de.dstn.x, de.dstn.y, de.src.x, de.src.y);
                else
                    g.drawLine(de.dstn.x, de.dstn.y, de.x2, de.y2);
            }
            
            for(int i=0;i<currentsize;i++){
                DuplicateNode dn = duplicateNodes.get(i);
                if(dn.selected)
                    g.setColor(Color.RED);
                else
                    g.setColor(nodeColor);
                g.fillOval(dn.x-dn.radius/2,dn.y-dn.radius/2,dn.radius,dn.radius);
             
                if(dn.radius==40){
                    g.setColor(nodeBorder);
                    g.drawOval(dn.x-20,dn.y-20,40,40);
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("SansSerif",Font.PLAIN,22));
                    g.drawString(Character.toString(dn.name), dn.x-8, dn.y+8);
                }
            }
        }catch(Exception e){}
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        // TODO add your handling code here:
        int x = evt.getX();
        int y = evt.getY();
        
        boolean Legal = false;
        outer:  for(DuplicateNode dn:duplicateNodes){
            if(dn.x-20 < x && x < dn.x+20 && dn.y-20 < y && y < dn.y+20 ){
                dn.selected = true;
                for(Node n:Panel1.originalNodes){
                    if(dn.id == n.name){
                        n.selected = true;
                        Legal = true;
                        break outer;
                    }
                }
            }
        }
        if(!Legal){
            for(DuplicateNode dn: duplicateNodes)
                dn.selected = false;
            for(Node n: Panel1.originalNodes)
                n.selected = false;
        }
        ContainerFrame.highLight();
    }//GEN-LAST:event_formMouseMoved

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
