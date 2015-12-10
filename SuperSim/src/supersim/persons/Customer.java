/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supersim.persons;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import supersim.CustomerGroup.CustomerGroup;
import supersim.Product.ProductWrapper;
import supersim.Store;
import supersim.StoreObjects.CashRegister;
import supersim.StoreObjects.FreshProductCounter;
import supersim.StoreObjects.Shelf;
import supersim.StoreObjects.StoreObject;
import supersim.StoreObjects.TaskStation;

/**
 *
 * @author Jens
 */
public class Customer extends Person{
    
    public List<ProductWrapper> shoppingCart;
    public List<ProductWrapper> groceryList;
    
    public CustomerGroup group;
    
    public boolean hasLeftStore;
    
    float budget;//Used for buying random extra items;
    
    public Shelf currentShelf;
    public ProductWrapper productLookingFor;
    
    public Customer(String name, CustomerGroup group, Store store)
    {
        this.currentState = State.IDLE;
        this.group = group;
        this.name = name;
        
        this.groceryList = new ArrayList<ProductWrapper>(group.groceryListPrototype); //Copy the prototype
        this.shoppingCart = new ArrayList<ProductWrapper>();
        
        this.color = Color.MAGENTA;
        this.store = store;
        
        
    }
    
    public void leaveStore()
    {
        this.hasLeftStore = true;
    }
    
    
    public Shelf findNearestShelfContainingProductFromGroceryList()
    {
        Shelf bestShelf = null;

        for(Shelf s : store.layout.getShelves())
        {
            if(s.getAmountOfItemsInShelf() > 0)
            {
                for(ProductWrapper pw : this.groceryList)
                {
                    if(s.getProduct().id == pw.product.id)
                    {
                        if(bestShelf == null) bestShelf = s;
                        else
                        {
                            if(this.distance(bestShelf) > this.distance(s)) 
                            {
                                productLookingFor = pw;
                                bestShelf = s;
                            }
                        }
                    }
                }
                
            }
        }
        
        return bestShelf;
    }
    
    public FreshProductCounter findFreshProductCounterContainingProduct(ProductWrapper productwrapper)
    {
        FreshProductCounter bestFreshProductCounter = null;

        for(TaskStation soX : store.layout.getTaskstations())
        {
            if(soX != null && soX instanceof FreshProductCounter)
            {
                FreshProductCounter fpc = (FreshProductCounter)soX;
                if(productwrapper.product.department.equalsIgnoreCase(fpc.department))
                {
                    if(bestFreshProductCounter == null) bestFreshProductCounter = fpc;
                    else
                    {
                        if(bestFreshProductCounter.getNrCustomers() == fpc.getNrCustomers() || fpc.getNrCustomers() < 8)
                        {
                            if(this.distance(bestFreshProductCounter) > this.distance(fpc))
                                bestFreshProductCounter = fpc;
                        }
                        else
                        if(bestFreshProductCounter.getNrCustomers() > fpc.getNrCustomers())
                        {
                            bestFreshProductCounter = fpc;
                        }
                    }
                }
            }
        }
        
        return bestFreshProductCounter;
    }
    
    public CashRegister findCashRegister()
    {
        
        CashRegister bestCashRegister = null;

        for(TaskStation soX : store.layout.getTaskstations())
        {
                if(soX != null && soX instanceof CashRegister)
                {
                    CashRegister cr = (CashRegister)soX;
                    if(bestCashRegister == null) bestCashRegister = cr;
                    else
                    {
                        if(bestCashRegister.getNrCustomers() == cr.getNrCustomers() || bestCashRegister.getNrCustomers() < 8)
                        {
                            if(this.distance(bestCashRegister) > this.distance(cr))
                            {
                                bestCashRegister = cr;
                            }
                        }
                        else
                        if(bestCashRegister.getNrCustomers() > cr.getNrCustomers()) bestCashRegister = cr;              
                    }
                }
        }
        
        
        return bestCashRegister;
    }
       
    

    @Override
    public void update(Date simulatedDate, float deltatime) {
        super.update(simulatedDate, deltatime);
        long cTime = simulatedDate.getTime();
        if(cTime > nextStateUpdateTime)//Check if its time to take a new task.
        {
            
            switch(currentState)
            {
                //When in the IDLE or LOOKING_FOR_PRODUCT state choose a thing to do next..
                
                case IDLE:
                    if(this.groceryList.size() > 0) //If the customer has products to buy
                    {
                        this.store.simulator.mainWindow.logMessage(this.name + " is looking for a new shelf");
                        this.currentState = State.LOOKING_FOR_PRODUCT;
                        nextStateUpdateTime =  (cTime + (long)((10 * 1000) + this.distance(this) / this.speed)); //takes 10 seconds to look for an item..
                        return;                       
                    }
                    else
                    {
                      this.store.simulator.mainWindow.logMessage(this.name + " is Moving to the cashregister");  
                      
                      CashRegister register = findCashRegister();
                      if(register != null)
                      {
                        this.location = register.location;
                        register.addCustomerToLine(this);
                        this.currentState = State.WAITING_IN_LINE;
                      }
                      
                    }
                    return;
                case LOOKING_FOR_PRODUCT:
                        //Get the next item on the grocery list
                        currentShelf = findNearestShelfContainingProductFromGroceryList();
                        if(currentShelf != null)
                        {
                            this.location = currentShelf.location;//move to the shelf
                            this.currentState = State.TAKING_PRODUCT;
                            
                            nextStateUpdateTime =  (cTime + (long)(((30 + this.distance(currentShelf)) * 1000) / this.speed)); //takes 30 + 1 per unit of distance seconds to walk to, and take an item..
                            return;
                        }
                        else
                        {
                            FreshProductCounter c = findFreshProductCounterContainingProduct(this.groceryList.get(0));
                            if(c != null)
                            {
                                c.addCustomerToLine(this);
                                this.location = c.location;
                                this.currentState = State.WAITING_IN_LINE;
                                return;
                            }
                            else
                            {
                                //Could not find product, remove it from grocerylist.
                                this.groceryList.remove(0); //Remove item from wished items
                                this.happy = false; //Customer is not happy because the store didn't have anything stocked.
                                this.currentState = State.IDLE; //Find something to do
                                //nextStateUpdateTime =  (cTime + (long)((20 * 1000) / this.speed)); //takes 20 seconds to not take an item.
                            }
                        }
                        
                    return;

                case TAKING_PRODUCT:
                        
                        if(productLookingFor != null && this.groceryList.size() > 0 && currentShelf.takeItem(productLookingFor.amount))
                        {
                            this.store.simulator.mainWindow.logMessage(this.name + " is taking " + productLookingFor.product.name + " from the store");
                            this.shoppingCart.add(productLookingFor);
                            this.groceryList.remove(0);
                        }
                        else
                        {
                            if(productLookingFor != null)
                            {
                                //Not enough items in the shelfs..
                                this.store.simulator.mainWindow.logMessage(this.name + "can not find enough " + productLookingFor.product.name + " in the store");
                            }
                        }
                        
                        this.currentState = State.IDLE;
                    return;

                case WAITING_IN_LINE:
                    //do nothing untill helped at the taskstation
                    return;
            }
        }
    }
    
}
