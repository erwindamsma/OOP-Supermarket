/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supersim.persons;

import java.awt.Point;
import supersim.StoreObjects.StoreObject;

/**
 *
 * @author Jens
 */
public class Person extends StoreObject {
    
    public String name;
    public float speed;
    
    public Point destination;
    
    enum State {IDLE, WALKING, WORKING_TASK};
    State currentState;
    
    public void update()
    {
        if(this.location != this.destination)
        {
            //Moving logic here
            this.currentState = State.WALKING;
            
        }
        else
        {
            this.currentState = State.IDLE;
        }
    }
    
    public void setDestination(Point dest)
    {
        this.destination = dest;
    }
    
}
