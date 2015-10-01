/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supersim;

import supersim.Product.ProductStorage;
import supersim.persons.Customer;
import supersim.persons.Employee;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Jens
 */
public class Store{
    
    public SuperSim simulator;
    public StoreController controller;
    
    public ProductStorage storage;
    public List<Customer> customers;
    public List<Employee> employees;
    public StoreLayout    layout;
    

    public Store(SuperSim simulator)
    {
        this.simulator  = simulator;
        this.storage    = new ProductStorage(this);
        this.customers  = new ArrayList<Customer>();
        this.employees  = new ArrayList<Employee>();
        this.layout     = new StoreLayout(this);
        
        this.controller = new StoreController(this);
        
    }
   
    
}
