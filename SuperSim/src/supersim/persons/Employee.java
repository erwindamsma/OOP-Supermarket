/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supersim.persons;

import supersim.Store;

/**
 *
 * @author Jens
 */
public class Employee extends Person{
    
    enum Task {NONE, FILL_SHELF, CASH_REGISTER, FRESH_PRODUCT_COUNTER};
    
    
    Task currentTask;
    State currentState;
    Store store;
    
    public Employee(Store s)
    {
        this.store = s;
        this.currentState = State.IDLE;
        this.currentTask = Task.NONE;
    }

    @Override
    public void update() {
        super.update(); //Movement is handeled in Person class
        
        //Called every tick, update position and state
        switch(currentState)
        {
            
        }
    }
    
}
