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
    
    public Store store;
    public StoreObject[][] matrix;
    
    public static final int SIZE = 8;
    
    public StoreLayout(Store s)
    {
        this.store = s;
    }
    
    public void loadLayoutFromFile(String path)
    {
        //Fill matrix from file..
    }
}
