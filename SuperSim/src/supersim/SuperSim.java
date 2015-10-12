/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supersim;

import java.sql.SQLException;
import supersim.timer.Timer;
import supersim.database.DatabaseConnection;
import supersim.graphics.StoreRenderer;
import java.util.Date;
import supersim.graphics.MainFrame;
import supersim.graphics.MainWindow;

/**
 *
 * @author Jens
 */
public class SuperSim {

    public Timer timer;
    public StoreRenderer renderer;
    public Store store;
    public DatabaseConnection database;
    public MainWindow mainWindow;
    
    public SuperSim(Date startTime) throws SQLException
    {
        //Initialze components
        timer       = new Timer(startTime);
        //renderer    = new StoreRenderer(this);
        database    = new DatabaseConnection();
        
        store       = new Store(this);
        
        mainWindow  = new MainWindow(this);
        
        
        
        timer.start();
        timer.speed = 1;
        
        mainWindow.setVisible(true);

    }
    
}
