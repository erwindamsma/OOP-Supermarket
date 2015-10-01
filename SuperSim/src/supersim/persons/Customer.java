/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supersim.persons;

import java.util.Date;
import java.util.List;
import supersim.CustomerGroup.CustomerGroup;
import supersim.Product.ProductWrapper;

/**
 *
 * @author Jens
 */
public class Customer extends Person{
    
    List<ProductWrapper> shoppingCart;
    List<ProductWrapper> groceryList;
    
    CustomerGroup group;
    
    public boolean hasLeftStore;
    
    float budget;//Used for buying random extra items;
    
    public Customer(CustomerGroup group)
    {
        this.currentState = State.IDLE;
        
    }
    
    public void leaveStore()
    {
        this.hasLeftStore = true;
    }

    @Override
    public void update(Date simulatedDate, float deltatime) {
        super.update(simulatedDate, deltatime);
        
        //Called every tick, update position and state
        switch(currentState)
        {
            
        }
    }
    
}
