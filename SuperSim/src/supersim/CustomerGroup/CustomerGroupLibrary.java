package supersim.CustomerGroup;

import java.util.ArrayList;
import java.util.Random;
import supersim.Product.Product;
import supersim.Product.ProductWrapper;
import supersim.Store;

public class CustomerGroupLibrary {
    
    private static ArrayList<CustomerGroup> groups;
    private static Random rnd = new Random();
    
    Store store;
    
    public CustomerGroupLibrary(Store store)
    {
        this.store = store;
    }
    
    public void initLib()
    {
        if(groups == null)
        {
            groups = new ArrayList<CustomerGroup>();
            
            ProductWrapper pw = null;//Used in singleton
            CustomerGroup cGroup = null;//Used in singleton
            
            //The Student group
            cGroup = new CustomerGroup();
            cGroup.name = "Student";
            cGroup.groceryListPrototype = new ArrayList<ProductWrapper>();
            pw = new ProductWrapper();
            pw.product = store.storage.getRandomProductTypeInStore("Diepvries");
            pw.amount = 1;
            cGroup.groceryListPrototype.add(pw);
            pw = new ProductWrapper();
            pw.product = store.storage.getRandomProductTypeInStore("Diepvries");
            pw.amount = 1;
            cGroup.groceryListPrototype.add(pw);
            pw = new ProductWrapper();
            pw.product = store.storage.getRandomProductTypeInStore("Drank");
            pw.amount = 1;
            cGroup.groceryListPrototype.add(pw);
            pw = new ProductWrapper();
            pw.product = store.storage.getRandomProductType("Bakkerij");
            pw.amount = 1;
            cGroup.groceryListPrototype.add(pw);
            groups.add(cGroup);
            
            //The Housemom group
            cGroup = new CustomerGroup();
            cGroup.name = "House mom 1";
            cGroup.groceryListPrototype = new ArrayList<ProductWrapper>();
            pw = new ProductWrapper();
            pw.product = store.storage.getRandomProductType("Vlees");
            pw.amount = 2;
            cGroup.groceryListPrototype.add(pw);
            pw = new ProductWrapper();
            pw.product = store.storage.getRandomProductTypeInStore("Groente");
            pw.amount = 2;
            cGroup.groceryListPrototype.add(pw);
            pw = new ProductWrapper();
            pw.product = store.storage.getRandomProductType("Kaas");
            pw.amount = 1;
            cGroup.groceryListPrototype.add(pw);
            pw = new ProductWrapper();
            pw.product = store.storage.getRandomProductTypeInStore("Zuivel");
            pw.amount = 1;
            cGroup.groceryListPrototype.add(pw);
            pw = new ProductWrapper();
            pw.product = store.storage.getRandomProductType("Bakkerij");
            pw.amount = 1;
            cGroup.groceryListPrototype.add(pw);
            pw = new ProductWrapper();
            pw.product = store.storage.getRandomProductType("Bakkerij");
            pw.amount = 1;
            cGroup.groceryListPrototype.add(pw);
            pw = new ProductWrapper();
            pw.product = store.storage.getRandomProductType("Fruit");
            pw.amount = 2;
            cGroup.groceryListPrototype.add(pw);
            groups.add(cGroup);
            
            //The dude group
            cGroup = new CustomerGroup();
            cGroup.name = "Dude";
            cGroup.groceryListPrototype = new ArrayList<ProductWrapper>();
            pw = new ProductWrapper();
            pw.product = store.storage.getRandomProductTypeInStore("Drank");
            pw.amount = 1;
            cGroup.groceryListPrototype.add(pw);
            pw = new ProductWrapper();
            pw.product = store.storage.getRandomProductTypeInStore("Drank");
            pw.amount = 1;
            cGroup.groceryListPrototype.add(pw);
            groups.add(cGroup);
        }
    }
    
    public CustomerGroup getRandomCustomerGroup()
    {
        initLib();
        if(groups == null || groups.size() <= 0) return null;
        
        return groups.get(rnd.nextInt(groups.size()));
    }
    
}
