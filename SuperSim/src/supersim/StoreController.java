/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supersim;

import java.awt.Point;
import supersim.timer.ITimeable;
import java.util.Date;
import java.util.Random;
import javax.swing.JOptionPane;
import supersim.CustomerGroup.CustomerGroup;
import supersim.CustomerGroup.CustomerGroupLibrary;
import supersim.Resources.Loader;
import supersim.persons.Customer;
import supersim.persons.Employee;

/**
 *
 * @author Jens
 */
public class StoreController implements ITimeable{

    Store store;
    
    Random random = new Random();
    CustomerGroupLibrary groupLib;
    
    private final int AverageCustomersOnADay = 1000;
    public int customerCount = 0;//The number of customers that have visited the store
    public int unhappyCustomerCount = 0;//The number of customers that left the store unhappy.
    
    public StoreController(Store store)
    {
        this.store = store;
        
        groupLib = new CustomerGroupLibrary(this.store);
        
        //fill storage from database
        store.storage.setStorageList(store.simulator.database.getStorage());
        
        try {
            //create store layout
            store.layout = LayoutLoader.generateStoreLayout("storelayout1.txt", this.store);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error loading layout!");
            return;
        }
        
        //add employees DEBUG
        store.employees.add(new Employee("Employee 1", store, new Point(0,0)));
        store.employees.add(new Employee("Employee 2", store,new Point(1,0)));
        store.employees.add(new Employee("Employee 3", store,new Point(2,0)));
        store.employees.add(new Employee("Employee 4", store,new Point(2,0)));
        store.employees.get(0).speed = 0.7f;

        store.simulator.timer.addSubscriber(this); //Subscribe to the timer so onTick is called on the timer thread
        
    }
     
    @Override
    public void onTick(Date simulatedDate, float deltatime) 
    {
        this.updateCustomers(simulatedDate, deltatime);
        this.updateEmployees(simulatedDate, deltatime);
    }
    
    //Call this every simulated seconds i.e. deltatime > 1000
    public boolean hasCustomerEnteredStore(Date simulatedDate)
    {
        //The opening hours have been split in 5 sections with the percentage of total visitors in a day
        //17:00 - 20:00  36%
        //12:00 - 14:00  28%
        // 9:00 - 12:00  20%
        //14:00 - 17:00  12%
        // 8:00 -  9:00  4%
        
        int currentHour = simulatedDate.getHours();
        
        if(currentHour >= store.openingHour && currentHour <= store.closingHour)//is the store open
        {
            float chancePerSecond = 0;
            
            if(currentHour >= 8 && currentHour <= 9)
            {
                chancePerSecond = (0.04f * AverageCustomersOnADay) / (60 * 60); // 8 to 9 is 60 * 60 seconds
            }
            
            if(currentHour > 14 && currentHour <= 17)
            {
                chancePerSecond = (0.12f * AverageCustomersOnADay) / (3 * 60 * 60); // 14 to 17 is 3 * 60 * 60 seconds
            }
            
            if(currentHour > 9 && currentHour <= 12)
            {
                chancePerSecond = (0.20f * AverageCustomersOnADay) / (3 * 60 * 60); // 9 to 12 is 3 * 60 * 60 seconds
            }
            
            if(currentHour > 12 && currentHour <= 14)
            {
                chancePerSecond = (0.28f * AverageCustomersOnADay) / (2 * 60 * 60); // 12 to 14 is 60 * 60 seconds
            }
            
            if(currentHour > 17 && currentHour <= 20)
            {
                chancePerSecond = (0.36f * AverageCustomersOnADay) / (3 * 60 * 60); // 17 to 20 is 60 * 60 seconds
            }
            
            float timePassed = (simulatedDate.getTime() - lastTime) / 1000f;
            
            if(random.nextFloat() < chancePerSecond * timePassed ) return true;
        }
        
        return false;
    }
    
    long lastTime = 0;
    public void updateCustomers(Date simulatedDate, float deltatime)
    {
        
        if(simulatedDate.getTime() - lastTime > 1000)//Every virtual second check if a customer has entered the store
        {
            if(hasCustomerEnteredStore(simulatedDate))
            {
                CustomerGroup cg = this.groupLib.getRandomCustomerGroup();
                Customer c = new Customer("Customer " + customerCount,cg, store);
                c.location = store.layout.entrance;

                store.customers.add(c);
                customerCount++;
            }
            lastTime = simulatedDate.getTime();
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
                if(!store.customers.get(i).happy) unhappyCustomerCount++;
                
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
