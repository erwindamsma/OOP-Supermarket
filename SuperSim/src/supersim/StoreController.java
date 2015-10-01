/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supersim;

import supersim.timer.ITimeable;
import java.util.Date;
import supersim.StoreObjects.StoreObject;
import supersim.persons.Employee;

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
        store.storage.storageList = store.simulator.database.getStorage();
        
        //create store layout
        store.layout.matrix = new StoreObject[][] 
                              {{null,null,null,null,null,null,null,null},
                               {null,null,null,null,null,null,null,null},
                               {null,null,null,null,null,null,null,null},
                               {null,null,null,null,null,null,null,null},
                               {null,null,null,null,null,null,null,null},
                               {null,null,null,null,null,null,null,null}, 
                               {null,null,null,null,null,null,null,null},
                               {null,null,null,null,null,null,null,null}
                            };
    
        
        //add employees
        store.employees.add(new Employee(store));
        store.employees.add(new Employee(store));
        store.employees.add(new Employee(store));
        
        store.simulator.timer.addSubscriber(this); //Subscribe to the timer
        
    }
     
    @Override
    public void onTick(Date simulatedDate, float deltatime) 
    {
        this.updateCustomers(simulatedDate, deltatime);
        this.updateEmployees(simulatedDate, deltatime);
    }
    
    public void updateCustomers(Date simulatedDate, float deltatime)
    {
        //check if new customers entered the store
        
        //loop through customers and call update()
        for(int i = 0; i < store.customers.size(); i++)
        {
            store.customers.get(i).update(simulatedDate, deltatime);
        }
        
        //If customers have left the store
        for(int i = 0; i < store.customers.size(); i++)
        {
            if(store.customers.get(i).hasLeftStore) store.customers.remove(i);
            i--;
        }
    }
    
    public void updateEmployees(Date simulatedDate, float deltatime)
    {
        //loop through employees and call update()
        for(int i = 0; i < store.employees.size(); i++)
        {
            store.employees.get(i).update(simulatedDate, deltatime);
        }
    }
    
}
