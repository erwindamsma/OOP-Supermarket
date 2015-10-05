package supersim.CustomerGroup;

import java.util.ArrayList;
import java.util.Random;
import supersim.Product.Product;
import supersim.Product.ProductWrapper;

public class CustomerGroupLibrary {
    
    private static ArrayList<CustomerGroup> groups;
    private static Random rnd = new Random();
    
    public static void initLib()
    {
        if(groups == null)
        {
            groups = new ArrayList<CustomerGroup>();
            
            //The Debug group
            CustomerGroup debugGroup = new CustomerGroup();
            debugGroup.name = "DEBUG CUSTOMER";
            debugGroup.groceryListPrototype = new ArrayList<ProductWrapper>();
            ProductWrapper pw = new ProductWrapper();
            pw.product = new Product();
            pw.product.id = 1;
            pw.product.name = "test";
            pw.product.price = 12.5f;
            pw.amount = 5;
            
            debugGroup.groceryListPrototype.add(pw);
            debugGroup.groceryListPrototype.add(pw);        
            
            groups.add(debugGroup);
        }
    }
    
    public static CustomerGroup getRandomCustomerGroup()
    {
        initLib();
        if(groups == null || groups.size() <= 0) return null;
        
        return groups.get(rnd.nextInt(groups.size()));
    }
    
}
