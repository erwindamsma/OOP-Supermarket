/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supersim.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import supersim.Store;

/**
 *
 * @author Jens
 */
public class ProductStorage {
    private List<ProductWrapper> storageList;
    private List<ProductWrapper> productsNotInStore;
    private List<ProductWrapper> productsInStore;
    Random rnd = new Random();
    Store store;
    
    public ProductStorage(Store store)
    {
        this.store = store;
    }
    
    public List<ProductWrapper> getStorageList()
    {
        return this.storageList;
    }
    
    public void setStorageList(List<ProductWrapper> storageList)
    {
        this.storageList = storageList;
        this.productsNotInStore = new ArrayList<>(storageList);
        this.productsInStore = new ArrayList<>();
    }
    
    public Product getProductTypeNotInStore(String department)
    {
        ProductWrapper retVal = null;
        for(ProductWrapper pw : productsNotInStore)
        {
            if(pw.product.department.equalsIgnoreCase(department))
            {
                retVal = pw;
                productsInStore.add(pw);
                break;
            }
        }
        if(retVal != null) productsNotInStore.remove(retVal);
        
        if(retVal == null) return null;
        return retVal.product;
    }
    
    public Product getRandomProductTypeInStore(String department)
    {
        if(productsInStore == null) return null;
        
        ArrayList<Product> filteredList = new ArrayList<>();
        
        for(ProductWrapper pw : productsInStore)
        {
            if(pw.product.department.equalsIgnoreCase(department)) filteredList.add(pw.product);
        }
        
        int idx = filteredList.size();
        if(idx <= 0)
            return null;
        return filteredList.get(rnd.nextInt(filteredList.size()));
    }
    
    public Product getRandomProductType(String department)
    {
        if(storageList == null) return null;
        
        ArrayList<Product> filteredList = new ArrayList<>();
        
        for(ProductWrapper pw : storageList)
        {
            if(pw.product.department.equalsIgnoreCase(department)) filteredList.add(pw.product);
        }
        
        int idx = filteredList.size();
        if(idx <= 0)
            return null;
        return filteredList.get(rnd.nextInt(filteredList.size()));
    }
    
    public int amountInStorage(Product p)
    {
        for(ProductWrapper pw : storageList)
        {
            if(p.id == pw.product.id) return pw.amount;
        }
        
        return 0;
    }
    
    public List<Product> getProductsByDepartment(String department)
    {
        ArrayList<Product> retVal = new ArrayList<Product>();
        
        for(ProductWrapper pw : storageList)
        {
            if(department.equalsIgnoreCase(pw.product.department))
                retVal.add(pw.product);
        }
        
        return retVal;
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
