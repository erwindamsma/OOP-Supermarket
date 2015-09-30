/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supersim;

import supersim.timer.ITimeable;
import java.util.Date;

/**
 *
 * @author Jens
 */
public class StoreController implements ITimeable{

    Store store;
    
    public StoreController(Store store)
    {
        this.store = store;
        
        //fill storage from database
        
        //create store layout
        
        //add employees
        
        store.simulator.timer.addSubscriber(this); //Subscribe to the timer
        
    }
    
    @Override
    public void onTick(Date simulatedDate, float deltatime) 
    {
        this.updateCustomers();
        this.updateEmployees();
    }
    
    public void updateCustomers()
    {
        //check if new customers entered the store
        
        //loop through customers and call update()
        for(int i = 0; i < store.customers.size(); i++)
        {
            store.customers.get(i).update();
        }
    }
    
    public void updateEmployees()
    {
        //loop through employees and call update()
        for(int i = 0; i < store.employees.size(); i++)
        {
            store.employees.get(i).update();
        }
    }
    
}
