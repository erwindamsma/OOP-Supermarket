/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supersim.StoreObjects;

import java.awt.Color;
import java.awt.Point;
import java.util.Date;
import supersim.Product.ProductWrapper;
import supersim.Store;

/**
 *
 * @author Jens
 */
public class CashRegister extends TaskStation{
    
    public float content = 0f;//Amount of money in the register
    
    public CashRegister()
    {
        this.color = Color.RED;
        this.taskTime = 40; //This task takes 40 seconds per costumer
        
    }
    
    public CashRegister(Point location)
    {
        this();//Call base constructor
        this.location = location;
    }
    
    public CashRegister(String[] args, Store store)
    {
        this();//Call base constructor
        this.store = store;
    }
    
    @Override
    public void doTask(Date virtualDate)
    {
        super.doTask(virtualDate);
        
        if(currentCostumer != null)
        {
            for(ProductWrapper pw : currentCostumer.shoppingCart)
            {
                content += pw.amount * pw.product.price;
            }


            currentCostumer.shoppingCart.clear();
            currentCostumer.leaveStore();//Tell the customer to leave the store..
            currentCostumer = null;
        }
    }
}
