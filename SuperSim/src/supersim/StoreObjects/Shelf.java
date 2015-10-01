package supersim.StoreObjects;

import java.awt.Color;
import java.awt.Point;
import supersim.Product.ProductWrapper;

public class Shelf extends StoreObject{
    private ProductWrapper product;
    public Shelf(ProductWrapper product, Point location){
        this.product = product;
        this.color = Color.RED;
        
        this.location = location;
    }
    
}
