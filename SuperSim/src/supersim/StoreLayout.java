/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supersim;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import supersim.Resources.Loader;
import supersim.StoreObjects.Shelf;
import supersim.StoreObjects.TaskStation;

/**
 *
 * @author Jens
 */
public class StoreLayout {
    
    public Store store;
    //private StoreObject[][] matrix;
    private Point entrance;
    
    private List<Shelf> shelves;
    private List<TaskStation> taskstations;
    
    
    public BufferedImage floorImage;
    
    public int SIZE = 8;
    
    public StoreLayout(Store s)
    {
        this.store = s;
        
        shelves = new ArrayList<Shelf>();
        taskstations = new ArrayList<TaskStation>();
        
        try {
            this.floorImage = ImageIO.read(Loader.getResource("floortile.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(StoreLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*public void setMatrix(StoreObject[][] matrix)
    {
        this.matrix = matrix;
        for(StoreObject[] soY : store.layout.matrix)
        {
            for(StoreObject soX : soY)
            {
                if(soX != null && soX instanceof TaskStation)
                {
                    this.taskstations.add((TaskStation)soX);
                }
                
                if(soX != null && soX instanceof Shelf)
                {
                    this.shelves.add((Shelf)soX);
                }
                
            }
        }
    }*/
    
    public Point getEntrance()
    {
        return this.entrance;
    }
    
    public void setEntrance(Point entrance)
    {
        this.entrance = entrance;
    }
    
    public List<Shelf> getShelves()
    {
        return this.shelves;
    }
    
    public List<TaskStation> getTaskstations()
    {
        return this.taskstations;
    }
    
    /*public void loadLayoutFromFile(String path)
    {
        try 
        {
            this.matrix = 
            SIZE = matrix.length;
        } 
        catch (IOException ex)
        {
            Logger.getLogger(StoreController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
}
