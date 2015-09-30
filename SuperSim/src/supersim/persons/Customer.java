/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supersim.persons;

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
    
    float budget;//Used for buying random extra items;
    
    public Customer(CustomerGroup group)
    {
        this.currentState = State.IDLE;
        
    }

    @Override
    public void update() {
        super.update();
        
        //Called every tick, update position and state
        switch(currentState)
        {
            
        }
    }
    
}
