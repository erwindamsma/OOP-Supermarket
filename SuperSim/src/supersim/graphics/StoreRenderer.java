/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supersim.graphics;

import java.awt.Graphics;
import javax.swing.JPanel;
import supersim.StoreObjects.StoreObject;

public class StoreRenderer extends JPanel {
    private supersim.SuperSim superSim;
    
    public StoreRenderer(supersim.SuperSim superSim){
        this.superSim = superSim;
    }
    
    @Override protected void paintComponent(Graphics g){
        //super.paintComponent(g);
        for (StoreObject[] soY : superSim.store.layout.matrix){
            for (StoreObject soX : soY){
                if(soX != null)
                    soX.onDraw(g, this.getWidth(), this.getHeight());
            }
        }
    }
}
