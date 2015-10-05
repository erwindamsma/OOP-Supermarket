/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supersim.StoreObjects;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Jens
 */
public class StoreObjectLibrary {
    
    static Map<String, Class> lib;
    
    /* 
    * Gets called by the map loader before it starts reading the file
    */
    public static void createLibrary()
    {
        if(lib == null)
        {
            lib = new HashMap<>();
            lib.put("shelf", Shelf.class);
            lib.put("cashregister", CashRegister.class);
        }
        
    }
    
    /* 
    * Get class is used in the map loader, it takes  a string and returns the class associated to the string
    */
    public static Class getClass(String s)
    {
       if(lib.containsKey(s.toLowerCase())) return lib.get(s.toLowerCase());
       
       return null;
    }
}
