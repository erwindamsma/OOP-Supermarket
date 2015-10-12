/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supersim;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import supersim.Resources.Loader;
import supersim.StoreObjects.StoreObject;

/**
 *
 * @author Jens
 */
public class StoreLayout {
    
    public Store store;
    public StoreObject[][] matrix;
    public Point entrance;
    
    public BufferedImage floorImage;
    
    public int SIZE = 8;
    
    public StoreLayout(Store s)
    {
        this.store = s;
        try {
            this.floorImage = ImageIO.read(Loader.getResource("floortile.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(StoreLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
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
