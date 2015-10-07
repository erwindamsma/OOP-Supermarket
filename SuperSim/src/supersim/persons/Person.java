/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supersim.persons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Date;
import supersim.StoreObjects.StoreObject;

/**
 *
 * @author Jens
 */
public class Person extends StoreObject {
    
    public String name;
    public float speed = 1f;
    
    public long nextTaskTime = 0;
    
    public Point destination;
    
    public enum State {IDLE, WALKING, WORKING_TASK, TAKING_PRODUCT, IN_LINE_CASHREGISTER};
    public State currentState;
    
    @Override
    public void onDraw(Graphics g, int panelWidth, int panelHeight)
    {
        super.onDraw(g, panelWidth, panelHeight);
        int cellWidth = panelWidth / store.layout.SIZE;
        int cellHeight = panelHeight / store.layout.SIZE;
        g.setColor(Color.BLACK);
        g.drawString(this.name, this.location.x * cellWidth, this.location.y * cellHeight+ 20);
        
    }
    
    public void update(Date simulatedDate, float deltatime)
    {
        /*if(this.location != this.destination)
        {
            //Moving logic here
            this.currentState = State.WALKING;
            
        }
        else
        {
            this.currentState = State.IDLE;
        }*/
    }
    
    public void setDestination(Point dest)
    {
        this.destination = dest;
    }
    
}
