/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supersim.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import supersim.StoreObjects.StoreObject;
import supersim.SuperSim;
import supersim.persons.Customer;
import supersim.persons.Employee;
import supersim.timer.ITimeable;

public class StoreRenderer extends JPanel implements ITimeable {
    private supersim.SuperSim superSim;
    private static final int fpsCap = 120;
    
    public StoreRenderer()
    {
        //Empty constructor
    }
    
    public StoreRenderer(SuperSim superSim){
        this.superSim = superSim;
        this.superSim.timer.addSubscriber(this);
    }
    
    public void setSimulator(SuperSim sim)
    {
        this.superSim = sim;
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
        if(superSim == null)
        {
            g.drawString("No Simulator has been assigned..", 10, 10);
            return;
        }
        
        //super.paintComponent(g);
        
        g.clearRect(0, 0, this.getWidth(), this.getHeight());
        if(superSim.store != null && superSim.store.layout != null)
        {
            int x = 0;int  y = 0;
            for (StoreObject[] soY : superSim.store.layout.matrix)
            {
                for (StoreObject soX : soY){
                    if(soX != null)
                    {
                        //g.drawImage(this.superSim.store.layout.floorImage,x,y,this.getWidth() / superSim.store.layout.SIZE,this.getHeight() / superSim.store.layout.SIZE,null);
                        soX.onDraw(g, this.getWidth(), this.getHeight());
                    }
                    x++;
                }
                x = 0;
                y++;
            }
            
            
            //Draw the employees
            for(Employee e : superSim.store.employees)
            {
                e.onDraw(g, this.getWidth(), this.getHeight());
            }
            
             //Draw the Customers
            List<Customer> custCopy = new ArrayList<>(superSim.store.customers);
            for(Customer c : custCopy)
            {
                c.onDraw(g, this.getWidth(), this.getHeight());
            }
        }
        
        
        if(this.superSim.timer.getSimulatedDate() != null)
        {
            long simdate = this.superSim.timer.getSimulatedDate().getTime();
            Date nDate = new Date();
            nDate.setTime(simdate);
            g.setColor(Color.red);
            g.drawString("Date: " + nDate, this.getWidth() / 2 , this.getHeight() - 20);
            g.setColor(Color.gray);
            g.drawString(":) " + (this.superSim.store.controller.customerCount - this.superSim.store.controller.unhappyCustomerCount) + " |  :( " + this.superSim.store.controller.unhappyCustomerCount, this.getWidth() / 2 , this.getHeight() - 7);
        }
        
        
       
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
