/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supersim;

import java.awt.Point;
import supersim.timer.ITimeable;
import java.util.Date;
import supersim.CustomerGroup.CustomerGroup;
import supersim.CustomerGroup.CustomerGroupLibrary;
import supersim.persons.Customer;
import supersim.persons.Employee;
import supersim.persons.Person;

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
        store.layout.loadLayoutFromFile("C:/Users/Jens/Desktop/storelayout1.txt");
        
        /*store.layout.matrix = new StoreObject[][]
            {{null,null,null,null,null,null,null,null},
            {null,null,null,null,null,null,null,null},
            {null,null,null,null,null,null,null,null},
            {null,null,null,new Shelf(store.storage.storageList.get(0), new Point(3,3)),null,null,null,null},
            {null,null,null,null,null,null,null,null},
            {null,null,null,null,null,null,null,null},
            {null,null,null,null,null,null,null,null},
            {null,null,null,null,null,null,null,null}
            };*/
    
        
        //add employees DEBUG
        store.employees.add(new Employee(store, new Point(0,0)));
        store.employees.add(new Employee(store,new Point(1,0)));
        store.employees.add(new Employee(store,new Point(2,0)));
        
        store.employees.get(0).currentTask = Employee.Task.FILL_SHELF;
        store.employees.get(0).currentState = Person.State.WORKING_TASK;
        store.employees.get(0).speed = 1f;
        
        store.employees.get(1).currentTask = Employee.Task.FILL_SHELF;
        store.employees.get(1).currentState = Person.State.WORKING_TASK;
        store.employees.get(1).speed = 2.7f;
        
        store.employees.get(2).currentTask = Employee.Task.CASH_REGISTER;
        store.employees.get(2).currentState = Person.State.WORKING_TASK;
        store.employees.get(2).currentCashRegister = store.employees.get(2).findUnmanedCashRegister();
        store.employees.get(2).currentCashRegister.employee = store.employees.get(2);
        store.employees.get(2).location = store.employees.get(2).currentCashRegister.location;
        store.employees.get(2).speed = 1.7f;
        
        store.simulator.timer.addSubscriber(this); //Subscribe to the timer
        
    }
     
    @Override
    public void onTick(Date simulatedDate, float deltatime) 
    {
        this.updateCustomers(simulatedDate, deltatime);
        this.updateEmployees(simulatedDate, deltatime);
        
        //store.simulator.renderer.invalidate();
       
    }
    
    public void updateCustomers(Date simulatedDate, float deltatime)
    {
        //Check if new customers should enter the store..
        if(store.customers.size() < 3)
        {
            CustomerGroup cg = CustomerGroupLibrary.getRandomCustomerGroup();
            Customer c = new Customer(cg, store);
            c.location = new Point(10,19);
            
            store.customers.add(c);
        }
        
        //loop through customers and call update()
        for(int i = 0; i < store.customers.size(); i++)
        {
            store.customers.get(i).update(simulatedDate, deltatime);
        }
        
        //If customers have left the store
        for(int i = 0; i < store.customers.size(); i++)
        {
            if(store.customers.get(i).hasLeftStore)
            {
                store.customers.remove(i);
                i--;
            }
            
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
