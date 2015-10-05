/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supersim.StoreObjects;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import supersim.persons.Customer;
import supersim.persons.Employee;
import supersim.persons.Employee.Task;

/**
 *
 * @author Jens
 */
public class TaskStation extends StoreObject{
    public Employee employee;
    
    private List<Customer> customerLine = new ArrayList<>();
    public Customer currentCostumer;
    
    public int taskTime = 1; //Time it takes to complete task, in seconds.
    
    private long startTime;
    
    public void addCustomerToLine(Customer c)
    {
        customerLine.add(c);
    }
    
    public int getNrCustomers()
    {
        return customerLine.size();
    }
    
    public void CheckTask(Date virtualDate)
    {
        employee.currentTask = Task.CASH_REGISTER;
        
        if(startTime+taskTime < virtualDate.getTime() && customerLine.size() > 0)
        {
            currentCostumer = customerLine.get(0);
            customerLine.remove(0);
            
            this.doTask(virtualDate);
        }
        
    }
    //This method should be overriden
    public void doTask(Date virtualDate){}
    
}
