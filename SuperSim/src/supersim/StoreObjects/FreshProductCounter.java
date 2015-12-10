/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supersim.StoreObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Date;
import supersim.Product.Product;
import supersim.Product.ProductWrapper;
import supersim.Store;
import supersim.persons.Person;

/**
 *
 * @author Jens
 */
public class FreshProductCounter extends TaskStation{
        
    public String department;
    
    public FreshProductCounter()
    {
        this.color = Color.ORANGE;
        this.taskTime = 40; //This task takes 40 seconds per costumer
        
    }
    
    public FreshProductCounter(Point location)
    {
        this();//Call base constructor
        this.location = location;
    }
    
    public FreshProductCounter(String[] args, Store store)
    {
        this();//Call base constructor
        this.store = store;
        
        if(args.length > 1)
        {
            department = args[1];
        }
    }
    
    @Override 
    public void onDraw(Graphics g, int cellWidth, int cellHeight)
    {
      super.onDraw(g, cellWidth, cellHeight);
      g.drawString(this.department, cellWidth * this.location.x, cellHeight * this.location.y);
      
    }
    
    @Override
    public void doTask(Date virtualDate)
    {
        if(currentCostumer != null)
            for(int i = 0; i < currentCostumer.groceryList.size(); i++)
            {
                ProductWrapper pw = currentCostumer.groceryList.get(i);
                for(Product p : this.store.storage.getProductsByDepartment(this.department))
                {
                    if(pw.product.id == p.id)
                    {
                        this.store.storage.TakeFromStorage(pw);

                        currentCostumer.shoppingCart.add(pw);
                        currentCostumer.groceryList.remove(i);
                        i--;//because we removed an entry from the grocerylist.
                        
                    }
                }
                
            }
        
        currentCostumer.currentState = Person.State.IDLE;
        currentCostumer = null;
        
     
    }
}
