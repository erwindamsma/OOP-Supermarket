/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supersim.Product;

/**
 *
 * @author Jens
 */
public class Product {
    public int id;
    public String name;
    public float price;
    public String department;
    public boolean isFresh;
    
    public Product()
    {
        this.id         = 0;
        this.name       = "";
        this.price      = 0f;
        this.department = "";
        this.isFresh    = false;
    }
}
