/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supersim;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import supersim.StoreObjects.StoreObject;

/**
 *
 * @author Jens
 */
public class StoreLayout {
    
    public Store store;
    public StoreObject[][] matrix;
    
    public int SIZE = 8;
    
    public StoreLayout(Store s)
    {
        this.store = s;
    }
    
    public void loadLayoutFromFile(String path)
    {
        try 
        {
            matrix = LayoutLoader.generateStoreLayout(path, store);
            SIZE = matrix.length;
        } 
        catch (IOException ex)
        {
            Logger.getLogger(StoreController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
