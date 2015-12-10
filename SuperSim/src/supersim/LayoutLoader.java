/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supersim;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import supersim.Resources.Loader;
import supersim.StoreObjects.CashRegister;
import supersim.StoreObjects.FreshProductCounter;
import supersim.StoreObjects.Shelf;
import supersim.StoreObjects.StoreObject;

/**
 *
 * @author Jens
 */
public class LayoutLoader {
    
    
    public static StoreLayout generateStoreLayout(String filepath, Store store) throws Exception
    {
        //Generate the map of storeobjects
        //StoreObjectLibrary.createLibrary();
        
        int matrixSize = -1;//Will be read from the file..
        BufferedReader br = new BufferedReader(new InputStreamReader(Loader.getResource(filepath), "UTF-8"));//new FileReader(filepath));  
        String line = null;  
        
        //Read the size of the matrix from the first line of the map file
        line = br.readLine();
        if(line.toLowerCase().startsWith("#size:"))
            matrixSize = Integer.decode(line.substring(6));
        
        if(matrixSize == -1) return null;
            
        StoreLayout retVal = new StoreLayout(store);
        retVal.SIZE = matrixSize;
        
        int x = 0, y = 0;
        while ((line = br.readLine()) != null)  
        {  
            for(String s : line.split(","))
            {
                String[] args = s.split(":");
                
                switch(args[0].toLowerCase())
                {
                    case "0": 
                        //skip this object
                        break;
                        
                        
                    case "shelf":
                        Shelf newShelf = new Shelf(args, store);
                        newShelf.location = new Point(x,y);
                        retVal.getShelves().add(newShelf);
                        
                        break;
                        
                    case "cashregister":
                        CashRegister newCashRegister = new CashRegister(args, store);
                        newCashRegister.location = new Point(x,y);
                        retVal.getTaskstations().add(newCashRegister);
                        break;
                        
                    case "freshproductcounter":
                        FreshProductCounter newFreshProductCounter = new FreshProductCounter(args, store);
                        newFreshProductCounter.location = new Point(x,y);
                        retVal.getTaskstations().add(newFreshProductCounter);
                        break;
                        
                    case "entrance":
                        retVal.setEntrance(new Point(x,y));
                        break;
                        
                }
                
                x++;
            }
            x = 0;
            y++;
        } 

        return retVal;
    }
}
