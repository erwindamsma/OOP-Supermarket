package supersim.StoreObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import supersim.Product.Product;
import supersim.Product.ProductWrapper;
import supersim.Store;

public class Shelf extends StoreObject{
    private ProductWrapper product;
    public int SIZE; //Amount of product this shelf can hold
    public String department;
    public boolean beingFilled = false;
    
    public int refillTime = 20; //20 seconds
    
    public Shelf(ProductWrapper product, Point location){
        this();//Call base constructor
        
        this.product = product;
        this.location = location;
    }
    
    public Shelf()
    {
        this.SIZE = 10;
        this.color = Color.RED;
        this.location = new Point();
    }
    
    //Constructor called in the maploader
    public Shelf(String[] arguments, Store store)
    {
        this();
        this.SIZE = 10;
        this.store = store;
        
        
        
        if(arguments.length > 1)
        {
            this.department = arguments[1];
            
            ProductWrapper pw = new ProductWrapper();
            pw.amount = 0;
            pw.product = store.storage.getProductTypeNotInStore(this.department);
            
            if(pw.product == null) pw.product = store.storage.getRandomProductType(this.department);
            
            this.product = pw;
            
        }
            
        
    }
    
    public int getAmountOfItemsInShelf()
    {
        return product.amount;
    }
    
    public Product getProduct()
    {
        if(product != null)
            return product.product;
        
        return null;
    }
    
    public void putItem(int amount)
    {
        product.amount += amount;
        if(product.amount > 0 ) this.color = Color.blue;
    }
    
    public boolean takeItem(int amount)
    {
        if(amount <= product.amount)
        {
            product.amount -= amount;
            
            if(product.amount == 0) this.color = Color.RED;
            return true;
        }

        return false;
    }
    
    @Override
    public void onDraw(Graphics g, int panelWidth, int panelHeight)
    {
        super.onDraw(g, panelWidth, panelHeight);
        int cellWidth = panelWidth / store.layout.SIZE;
        int cellHeight = panelHeight / store.layout.SIZE;
        g.setColor(Color.BLACK);
        g.drawString(this.product.product.name + " ("+this.product.amount+"/"+this.SIZE+")", this.location.x * cellWidth + cellWidth, this.location.y * cellHeight+ 20);
        
    }
    
}
