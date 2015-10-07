/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supersim.persons;

import java.awt.Color;
import java.awt.Point;
import java.util.Date;
import supersim.Product.ProductWrapper;
import supersim.Store;
import supersim.StoreObjects.CashRegister;
import supersim.StoreObjects.Shelf;
import supersim.StoreObjects.StoreObject;

/**
 *
 * @author Jens
 */
public class Employee extends Person{
    
    public enum Task {NONE, FILL_SHELF, CASH_REGISTER, FRESH_PRODUCT_COUNTER};

    public Task currentTask;
    
    
    //State currentState;
    //Store store;
    
    public Employee(String name, Store s, Point location)
    {
        this.name = name;
        this.store = s;
        this.currentState = State.IDLE;
        this.currentTask = Task.NONE;
        this.color = Color.GREEN;
        this.location = location;
    }
    
    public Shelf moveToEmptyShelf()
    {
        for(StoreObject[] soY : store.layout.matrix)
        {
            for(StoreObject soX : soY)
            {
                if(soX != null && soX.getClass() == Shelf.class)
                {
                    Shelf s = (Shelf)soX;
                    int itemsinshelf = s.getAmountOfItemsInShelf();
                    if(itemsinshelf < s.SIZE && !s.beingFilled)
                    {
                        this.location = s.location;
                        return s;
                    }
                        
                }
            }
        }
        
        return null;
    }
    
    public void fillShelf(Shelf shelf)
    {
       //Is the product still in the storage
       if(store.storage.amountInStorage(shelf.getProduct()) != 0)
       {
           ProductWrapper pw = new ProductWrapper();
           pw.amount = shelf.SIZE;
           pw.product = shelf.getProduct();

           int itemsTaken = store.storage.TakeFromStorage(pw);
           shelf.putItem(itemsTaken);
       }
    }
    
    
    public CashRegister cashRegisterWithShortestLine()
    {
        int lowestLineSize = Integer.MAX_VALUE;
        CashRegister retVal = null;
        
         for(StoreObject[] soY : store.layout.matrix)
        {
            for(StoreObject soX : soY)
            {
                if(soX != null && soX.getClass() == CashRegister.class)
                {
                    CashRegister cr = (CashRegister)soX;
                    
                    if(cr.employee != null && cr.getNrCustomers() < lowestLineSize)
                    {
                        lowestLineSize = cr.getNrCustomers();
                        retVal = cr;
                        
                    }
                        
                }
            }
        }
         
         return retVal;
    }
    
    public CashRegister findUnmanedCashRegister()
    {
        for(StoreObject[] soY : store.layout.matrix)
        {
            for(StoreObject soX : soY)
            {
                if(soX != null && soX.getClass() == CashRegister.class)
                {
                    CashRegister cr = (CashRegister)soX;
                    
                    if(cr.employee == null)
                    {
                        return cr;
                    }
                        
                }
            }
        }
        
        return null;
    }
    
    public int nrOfMannedCashRegisters()
    {
        int retval = 0;
        
        for(StoreObject[] soY : store.layout.matrix)
        {
            for(StoreObject soX : soY)
            {
                if(soX != null && soX.getClass() == CashRegister.class)
                {
                    CashRegister cr = (CashRegister)soX;
                    
                    if(cr.employee != null)
                    {
                        retval++;
                    }
                        
                }
            }
        }
        
        return retval;
    }

    public Shelf currentShelf;
    public CashRegister currentCashRegister;
    @Override
    public void update(Date simulatedDate, float deltatime) {
        super.update(simulatedDate, deltatime); //Movement is handeled in Person class
        
        
        
        //Called every tick, update position and state
        switch(currentState)
        {
            case WORKING_TASK:
                if(simulatedDate.getTime() > nextTaskTime)//Check if its time to take a new task.
                {
                    if(currentShelf != null) currentShelf.beingFilled = false;
                    switch(currentTask)
                    {
                        case FILL_SHELF:
                            this.nextTaskTime = (long) (simulatedDate.getTime() + ((120 * 1000) / this.speed));//Takes 120 seconds to fill a shelf.
                            currentShelf =  this.moveToEmptyShelf();
                            
                            if(currentShelf != null)
                            {
                                System.out.println(this.name + " is filling " + currentShelf + " with dept: " + currentShelf.department);
                                currentShelf.beingFilled = true;
                                fillShelf(currentShelf);
                            }
                            else{
                                if(cashRegisterWithShortestLine().getNrCustomers() > 3) //When there is a cashregister with more than 3 people in line, man an empty cashregister 
                                {
                                    
                                    CashRegister cr = findUnmanedCashRegister();
                                    
                                    if(cr != null)
                                    {
                                        System.out.println(this.name + " has manned CashRegister: " + cr);
                                        cr.employee = this;
                                        this.currentCashRegister = cr;
                                        this.location = cr.location;
                                        this.currentTask = Task.CASH_REGISTER;
                                    }
                                }
                            }
                            break;
                            
                        case CASH_REGISTER:
                            this.nextTaskTime = (long) (simulatedDate.getTime() + ((40 * 1000) / this.speed));//Takes 10 seconds to help a customer
                            if(this.currentCashRegister.getNrCustomers() == 0 && nrOfMannedCashRegisters() > 1)//When the line is empty and there are other manned chashregisters, leave this cashregister.
                            {
                                System.out.println(this.name + " has left CashRegister: " + currentCashRegister);
                                this.currentCashRegister.employee = null;
                                this.currentCashRegister = null;
                                this.currentTask = Task.FILL_SHELF;
                            }
                            else
                            if(this.currentCashRegister.getNrCustomers() > 0)
                            {
                                System.out.println(this.name + " is helping the next customer in line (" + this.currentCashRegister.getNrCustomers() + " people in line)");
                                this.currentCashRegister.CheckTask(simulatedDate);
                            }
                            break;
                    }
                }
                
                
                break;
        }
    }
    
}
