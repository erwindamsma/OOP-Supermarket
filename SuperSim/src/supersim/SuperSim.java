/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supersim;

import supersim.timer.Timer;
import supersim.database.DatabaseConnection;
import supersim.graphics.StoreRenderer;
import java.util.Date;
import javax.swing.JFrame;

/**
 *
 * @author Jens
 */
public class SuperSim {

    public Timer timer;
    public StoreRenderer renderer;
    public Store store;
    public DatabaseConnection database;
    public JFrame mainWindow;
    
    public SuperSim(Date startTime)
    {
        //Initialze components
        timer       = new Timer(startTime);
        renderer    = new StoreRenderer();
        store       = new Store(this);
        database    = new DatabaseConnection();
        mainWindow  = new JFrame("SUPERSIM");
        
        timer.start();
        timer.speed = 1;
        
        
    }
    
}
