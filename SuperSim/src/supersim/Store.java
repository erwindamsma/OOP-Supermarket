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
    
    SuperSim simulator;
    StoreController controller;
    
    ProductStorage storage;
    List<Customer> customers;
    List<Employee> employees;
    StoreLayout    layout;
    

    public Store(SuperSim simulator)
    {
        this.simulator  = simulator;
        this.controller = new StoreController(this);
        this.customers  = new ArrayList<Customer>();
        this.employees  = new ArrayList<Employee>();
        this.layout     = new StoreLayout(this);
        
    }
   
    
}
