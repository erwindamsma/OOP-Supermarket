/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supersim.persons;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import supersim.CustomerGroup.CustomerGroup;
import supersim.Product.ProductWrapper;
import supersim.Store;
import supersim.StoreObjects.CashRegister;
import supersim.StoreObjects.Shelf;
import supersim.StoreObjects.StoreObject;

/**
 *
 * @author Jens
 */
public class Customer extends Person{
    
    public List<ProductWrapper> shoppingCart;
    public List<ProductWrapper> groceryList;
    
    CustomerGroup group;
    
    public boolean hasLeftStore;
    
    float budget;//Used for buying random extra items;
    
    public Shelf currentShelf;
    
    public Customer(CustomerGroup group, Store store)
    {
        this.currentState = State.IDLE;
        this.group = group;
        
        this.groceryList = new ArrayList<ProductWrapper>(group.groceryListPrototype); //Copy the prototype
        this.shoppingCart = new ArrayList<ProductWrapper>();
        
        this.color = Color.MAGENTA;
        this.store = store;
        
        
    }
    
    public void leaveStore()
    {
        this.hasLeftStore = true;
    }
    
    public Shelf moveToShelf(ProductWrapper productwrapper)
    {
        int x = 0, y = 0;
        for(StoreObject[] soY : store.layout.matrix)
        {
            for(StoreObject soX : soY)
            {
                
                if(soX != null && soX.getClass() == Shelf.class)
                {
                    Shelf s = (Shelf)soX;
                    if(s.getAmountOfItemsInShelf() > 0 && s.getProduct().id == productwrapper.product.id)
                    {
                        this.location = s.location;
                        return s;
                    }
                }
                x++;
            }
            
            y++;
        }
        
        return null;
    }
    
    public CashRegister moveToCashRegister()
    {
        
        CashRegister bestCashRegister = null;
        
        int x = 0, y = 0;
        for(StoreObject[] soY : store.layout.matrix)
        {
            for(StoreObject soX : soY)
            {
                if(soX != null && soX.getClass() == CashRegister.class)
                {
                    CashRegister cr = (CashRegister)soX;
                    if(bestCashRegister == null) bestCashRegister = cr;
                    else
                    {
                        if(bestCashRegister.employee != null)
                            
                            if(bestCashRegister.getNrCustomers() > cr.getNrCustomers()) bestCashRegister = cr;
                    }
                }
                
                x++;
            }
            
            y++;
        }
        this.location = bestCashRegister.location;
        
        return bestCashRegister;
    }
    
    

    @Override
    public void update(Date simulatedDate, float deltatime) {
        super.update(simulatedDate, deltatime);
        
        if(simulatedDate.getTime() > nextTaskTime)//Check if its time to take a new task.
        {
            //Called every tick, update position and state
            switch(currentState)
            {
                //When in the IDLE state choose a thing to do next..
                case IDLE:
                    nextTaskTime = (long) (simulatedDate.getTime() + ((30 * 1000) / this.speed)); // Takes 30 seconds to look for a shelf
                    if(this.groceryList.size() > 0)
                    {
                        //Get the next item on the grocery list
                        currentShelf = moveToShelf(this.groceryList.get(0));
                        if(currentShelf != null)
                            this.currentState = State.TAKING_PRODUCT;
                        else
                        {
                           //Could not find product, remove it from grocerylist.
                           //this.groceryList.remove(0); //Remove item from wished items
                           //Stay in the idle state..
                        }
                    }
                    else
                    {
                      //All products on the grocerylist are in the shoppingcart
                      CashRegister register = moveToCashRegister();
                      register.addCustomerToLine(this);
                      this.currentState = State.IN_LINE_CASHREGISTER;
                    }
                    break;

                case TAKING_PRODUCT:
                        nextTaskTime = (long) (simulatedDate.getTime() + ((10 * 1000) / this.speed)); //takes 10 seconds to take an item..
                        if(this.groceryList.size() > 0 && currentShelf.takeItem(this.groceryList.get(0).amount))
                        {
                            this.shoppingCart.add(this.groceryList.get(0));
                            this.groceryList.remove(0);
                            this.currentState = State.IDLE;
                        }
                        else
                        {
                            //Not enough items in the shelfs..
                            //this.groceryList.remove(0); //Remove item from wished items
                            this.currentState = State.IDLE;
                        }
                    break;

                case IN_LINE_CASHREGISTER:
                    //do nothing, wait till helped and removed from store
                    break;
            }
        }
    }
    
}
