/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supersim.persons;

import java.awt.Color;
import java.awt.Point;
import java.util.Date;
import supersim.Product.ProductWrapper;
import supersim.Store;
import supersim.StoreObjects.FreshProductCounter;
import supersim.StoreObjects.Shelf;
import supersim.StoreObjects.TaskStation;

/**
 *
 * @author Jens
 */
public class Employee extends Person{
    
    public enum Task {NONE, FILL_SHELF, TASK_STATION, HELPING_CUSTOMER};

    public Task currentTask;
    
    public Task dedicatedTask = null;
    
    
    //State currentState;
    //Store store;
    
    public Employee(String name, Store s, Point location)
    {
        this.name = name;
        this.store = s;
        this.currentState = State.IDLE;
        this.currentTask = Task.NONE;
        this.color = Color.GREEN;
        this.location = location;
    }
    
    
    //Find empty shelf where products are still in storage.
    public Shelf findEmptyShelf()
    {
        Shelf bestShelf = null;
        
        for(Shelf s : store.layout.getShelves())
        {
            int itemsinshelf = s.getAmountOfItemsInShelf();
            if(itemsinshelf < s.SIZE && store.storage.amountInStorage(s.getProduct()) != 0 && !s.beingFilled)
            {
                if(bestShelf == null) bestShelf = s;
                else
                {
                    if(this.distance(bestShelf) > this.distance(s))
                    {
                        bestShelf = s;
                    }
                }
            } 
        }
        
        return bestShelf;
    }
    
    public void fillShelf(Shelf shelf)
    {
       //Is the product still in the storage
       if(store.storage.amountInStorage(shelf.getProduct()) != 0)
       {
           ProductWrapper pw = new ProductWrapper();
           pw.amount = shelf.SIZE - shelf.getAmountOfItemsInShelf();
           pw.product = shelf.getProduct();

           int itemsTaken = store.storage.TakeFromStorage(pw);
           shelf.putItem(itemsTaken);
       }
    }
      
    
    public TaskStation findUnmanedTaskStationWithLine()
    {
        TaskStation bestTs = null;
        
        for(TaskStation ts : store.layout.getTaskstations())
        {
            if(ts.employee == null && ts.getNrCustomers() > 0)
            {
                if(bestTs == null) bestTs = ts;
               else
               {
                   if(this.distance(bestTs) > this.distance(ts))
                   {
                       bestTs = ts;
                   }
               }
            }   
        }
        
        return bestTs;
    }
    
    public int nrOfMannedTaskStations()
    {
        int retval = 0;
        
        for(TaskStation ts : store.layout.getTaskstations())
        {
            if(ts.employee != null)
            {
                retval++;
            }
        }
        
        return retval;
    }
    
    public TaskStation findMannedTaskStationWithType(Class c)
    {
         TaskStation bestTs = null;
        
        for(TaskStation ts : store.layout.getTaskstations())
        {
            if(ts.employee != null && c.isInstance(ts))
            {
               if(bestTs == null) bestTs = ts;
               else
               {
                   if(this.distance(bestTs) > this.distance(ts))
                   {
                       bestTs = ts;
                   }
               }
            }
        }
        
        return bestTs;
    }
    
    //find an empty task station with the given type
    public TaskStation findEmptyTaskStationWithType(Class c)
    {
        TaskStation bestTs = null;
        
        for(TaskStation ts : store.layout.getTaskstations())
        {
            if(ts.employee == null && c.isInstance(ts))
            {
               if(bestTs == null) bestTs = ts;
               else
               {
                   if(this.distance(bestTs) > this.distance(ts))
                   {
                       bestTs = ts;
                   }
               }
            }
        }
        
        return bestTs;
    }

    public Shelf assignedShelf;
    public TaskStation currentTaskStation;
    @Override
    public void update(Date simulatedDate, float deltatime) {
        super.update(simulatedDate, deltatime); //Movement is handeled in Person class
        long cTime = simulatedDate.getTime();
        
       
                if(cTime > nextStateUpdateTime)//Check if its time to advance the state.
                {
                    if(dedicatedTask != null) this.currentTask = dedicatedTask;
                    
                    switch(currentTask)
                    {
                        case NONE://Find this employee something to do
                            //this.location = new Point(10,10);

                            
                            TaskStation ts = null;
                                
                            
                            ts = findUnmanedTaskStationWithLine();
                            if(ts == null) //If such a taskstation has not been found
                            {
                                //Check if a new taskstation should be openend
                                for(TaskStation nTs : this.store.layout.getTaskstations())
                                {
                                    if(nTs.getNrCustomers() > 5)//If more than 5 people are in line
                                        ts = findEmptyTaskStationWithType(nTs.getClass());
                                }
                            }
                            
                            if(ts != null) //If a taskstation has been found
                            {
                                manTaskStation(ts); //Assign the employee to the taskstation
                                this.currentTask = Task.TASK_STATION;
                                nextStateUpdateTime = cTime + (long)(this.distance(ts) * 1000); //it takes 1 second per unit of distance to walk to the task station.
                                return;
                            }

                            this.currentTask = Task.FILL_SHELF;//try to fill a shelf
   
                            return;

                        case FILL_SHELF:
                            if(assignedShelf != null){//If a shelf has been assigned to fill
                                fillShelf(assignedShelf);
                                assignedShelf.beingFilled = false;
                                this.assignedShelf = null;
                                
                                this.currentTask = Task.NONE; // find something to do
                                return;
                            }
                            
                            assignedShelf =  this.findEmptyShelf(); //Find shelf to fill
                            if(assignedShelf != null)//If an empty shelf has been found
                            {
                                this.location = assignedShelf.location; //Move to the location of the shelf
                                assignedShelf.beingFilled = true; //Keep other employees from filling this shelf.
                                
                                //TODO: make this call shorter
                                store.simulator.mainWindow.logMessage(this.name + " is filling " + assignedShelf + " with dept: " + assignedShelf.department);
                            }
                            else{//No shelf found, find another task.
                                this.currentTask = Task.NONE;
                                this.nextStateUpdateTime =  (cTime + (long)((10 * 1000) / this.speed));//Takes 10 seconds to find a new task.
                                return;
                            }
                            
                            this.nextStateUpdateTime =  (cTime + (long)((60 * 1000) / this.speed));//Takes 60 seconds to fill a shelf at speed = 1.
                            return;
                            
                        case TASK_STATION:
                            this.location = currentTaskStation.location;
                            
                            if(this.currentTaskStation.getNrCustomers() == 0) //If no people are in the line find another task
                            {
                                this.leaveTaskStation();
                                this.currentTask = Task.NONE;
                                return;
                            }
                            else 
                            {
                                this.store.simulator.mainWindow.logMessage(this.name + " is helping the next customer in line (" + this.currentTaskStation.getNrCustomers() + " people in line)");
                                this.currentTaskStation.CheckTask(simulatedDate); //Help the next customer in line
                            }
                            
                           
                            this.nextStateUpdateTime = (cTime + (long)((this.currentTaskStation.taskTime / this.speed) * 1000));//Takes 'taskTime' seconds to help a customer
                            return;
                    }
                }
        
    }
    
    public void leaveTaskStation()
    {
        if(currentTaskStation != null)
        {
            this.store.simulator.mainWindow.logMessage(this.name + " has left taskStation: " + currentTaskStation);
            this.currentTaskStation.employee = null;
            this.currentTaskStation = null;
        }
    }
    
    public void manTaskStation(TaskStation ts)
    {
        if(ts.employee == null)
        {
            if(this.currentTaskStation != null) leaveTaskStation();
            
            this.store.simulator.mainWindow.logMessage(this.name + " has manned TaskStation: " + ts);
            ts.employee = this;
            this.currentTaskStation = ts;
            //this.location = ts.location;
        }
    }
    
}
