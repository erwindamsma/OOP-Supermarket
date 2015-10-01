package supersim.StoreObjects;

import java.awt.Color;
import supersim.Product.ProductWrapper;

public class Shelf extends StoreObject{
    private ProductWrapper product;
    public Shelf(ProductWrapper product){
        this.product = product;
        this.color = Color.RED;
    }
    
}
