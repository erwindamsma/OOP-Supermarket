/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supersim.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Date;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import supersim.StoreObjects.StoreObject;
import supersim.persons.Customer;
import supersim.persons.Employee;
import supersim.timer.ITimeable;

public class StoreRenderer extends JPanel implements ITimeable {
    private supersim.SuperSim superSim;
    private static final int fpsCap = 60;
    
    public StoreRenderer(supersim.SuperSim superSim){
        this.superSim = superSim;
        this.superSim.timer.addSubscriber(this);
    }
    
    public void invokeRepaint()
    {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                StoreRenderer.this.repaint();
                //paintImmediately(new Rectangle(0,0,StoreRenderer.this.getWidth(),StoreRenderer.this.getHeight()));
            }});
    }
    
    @Override protected void paintComponent(Graphics g){
        //super.paintComponent(g);
        g.clearRect(0, 0, this.getWidth(), this.getHeight());
        if(superSim.store != null && superSim.store.layout != null)
        {
            for (StoreObject[] soY : superSim.store.layout.matrix)
            {
                for (StoreObject soX : soY){
                    if(soX != null)
                        soX.onDraw(g, this.getWidth(), this.getHeight());
                }
            }
            
            
            //Draw the employees
            for(Employee e : superSim.store.employees)
            {
                e.onDraw(g, this.getWidth(), this.getHeight());
            }
            
             //Draw the Customers
            for(Customer c : superSim.store.customers)
            {
                c.onDraw(g, this.getWidth(), this.getHeight());
            }
        }
        
        
        
        g.setColor(Color.black);
        g.drawString("Current date: " + superSim.timer.getSimulatedDate().toString(), 10, this.getHeight() - 10);
    }

    float culDelta = 0;
    @Override
    public void onTick(Date simulatedDate, float deltatime) {
        if(culDelta/1000 > 1/fpsCap)
        {
            invokeRepaint();
            culDelta = 0;
        }
        culDelta += deltatime;
    }
}
