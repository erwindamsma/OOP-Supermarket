/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supersim.StoreObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import supersim.persons.Customer;
import supersim.persons.Employee;
import supersim.persons.Employee.Task;
import supersim.persons.Person;

/**
 *
 * @author Jens
 */
public class TaskStation extends StoreObject{
    public Employee employee;
    
    private List<Customer> customerLine = new ArrayList<>();
    public Customer currentCostumer;
    
    public int taskTime = 1; //Time it takes to complete task, in seconds.

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

            currentCostumer = customerLine.get(0);
            //
            customerLine.remove(0);
            this.doTask(virtualDate);      
    }
    
    @Override
    public void onDraw(Graphics g, int panelWidth, int panelHeight)
    {
        super.onDraw(g, panelWidth, panelHeight);
        int cellWidth = panelWidth / store.layout.SIZE;
        int cellHeight = panelHeight / store.layout.SIZE;
        g.setColor(Color.BLACK);
        g.drawString(this.getNrCustomers() + " people in line", this.location.x * cellWidth + cellWidth, this.location.y * cellHeight- 20);
        
    }
    
    
    //This method should be overriden
    public void doTask(Date virtualDate)
    {
        if(currentCostumer != null) currentCostumer.currentState = Person.State.IDLE;
    }
    
}
