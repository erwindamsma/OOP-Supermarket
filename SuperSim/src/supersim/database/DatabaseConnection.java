/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supersim.database;

import supersim.Product.ProductWrapper;
import supersim.Product.Product;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jens
 */
public class DatabaseConnection {
    
    Connection conn;
    
    public DatabaseConnection() throws SQLException
    {
            this.connect();
            getStorage();
    }
    
    public void addProduct(String name, float price)
    {
        Statement s;
        try {
            s = conn.createStatement();
            s.execute("insert into PRODUCT(NAME, PRICE) VALUES('"+name+"', "+price+")");
            //conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
    public List<ProductWrapper> getStorage()
    {
        List<ProductWrapper> retVal = new ArrayList<ProductWrapper>();
        
        Statement s;
        try {
            s = conn.createStatement();
            ResultSet r = s.executeQuery("SELECT * FROM STORAGE");
            
            
            while(r.next())
            {
                ProductWrapper pw = new ProductWrapper();
                Product p = getProductById(r.getInt("PRODUCT_ID"));
                
                pw.product = p;
                pw.amount  = r.getInt("AMOUNT");
                
                retVal.add(pw);
            }
            
            //conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retVal;
    }
    
        
    public Product getProductById(int id)
    {
        Product retval = null;
        Statement s;
        try {
            s = conn.createStatement();
            ResultSet r = s.executeQuery("SELECT * FROM PRODUCT WHERE ID="+id);
            
            while(r.next())
            {
                Product p = new Product();
                
                p.id    = r.getInt("ID");
                p.name  = r.getString("NAME");
                p.price = r.getFloat("PRICE");
                p.department  = r.getString("DEPARTMENT");
                p.isFresh  = r.getBoolean("ISFRESH");
                
                retval = p;
            }
            
            //conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return retval;
    }
    
    public List<Product> getAllProducts()
    {
        List<Product> retVal = new ArrayList<Product>();
        
        Statement s;
        try {
            s = conn.createStatement();
            ResultSet r = s.executeQuery("SELECT * FROM PRODUCT");
            
            
            while(r.next())
            {
                Product p = new Product();
                
                p.id    = r.getInt("ID");
                p.name  = r.getString("NAME");
                p.price = r.getFloat("PRICE");
                
                retVal.add(p);
            }
            
            //conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retVal;
    }
    
    public void connect() throws SQLException
    {
        conn = DriverManager.getConnection("jdbc:derby://localhost:1527/SuperSim;create=true");
    }
}
