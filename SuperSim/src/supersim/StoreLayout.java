/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supersim;

import supersim.StoreObjects.StoreObject;

/**
 *
 * @author Jens
 */
public class StoreLayout {
    
    Store store;
    StoreObject[][] matrix;
    
    public StoreLayout(Store s)
    {
        this.store = s;
    }
    
    public void loadLayoutFromFile(String path)
    {
        //Fill matrix from file..
    }
}
