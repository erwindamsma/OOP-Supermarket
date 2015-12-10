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
    
    public long nextStateUpdateTime = 0;
    
    public boolean happy = true;
    
    public enum State {IDLE, WALKING, WORKING_TASK, TAKING_PRODUCT, WAITING_IN_LINE, LOOKING_FOR_PRODUCT};
    public State currentState;
    
    @Override
    public void onDraw(Graphics g, int cellWidth, int cellHeight)
    {
        super.onDraw(g, cellWidth, cellHeight);
       
        g.setColor(Color.BLACK);
        g.drawString(this.name, this.location.x * cellWidth, this.location.y * cellHeight+ 20);
        
    }
    
    public void update(Date simulatedDate, float deltatime) {}
    
}
