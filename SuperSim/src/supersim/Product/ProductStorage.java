/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supersim.Product;

import java.util.List;
import java.util.Random;
import supersim.Store;

/**
 *
 * @author Jens
 */
public class ProductStorage {
    public List<ProductWrapper> storageList;
    Random rnd = new Random();
    Store store;
    
    public ProductStorage(Store store)
    {
        this.store = store;
    }
    public Product getRandomProductType()
    {
        if(storageList == null) return null;
        
        return storageList.get(rnd.nextInt(storageList.size())).product;
    }
    public int amountInStorage(Product p)
    {
        for(ProductWrapper pw : storageList)
        {
            if(p.id == pw.product.id) return pw.amount;
        }
        
        return 0;
    }
    
    public int TakeFromStorage(ProductWrapper productWrapper)
    {
        for(ProductWrapper pw : storageList)
        {
            if(productWrapper.product.id == pw.product.id)
            {
                int nrOfItemsTaken = Math.min(pw.amount, productWrapper.amount);
                pw.amount -= productWrapper.amount;
                if(pw.amount < 0) pw.amount = 0;
                
                return nrOfItemsTaken; 
            }
        }
        
        return 0;
    }
}
