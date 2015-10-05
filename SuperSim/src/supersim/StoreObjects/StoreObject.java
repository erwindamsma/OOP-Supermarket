/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supersim.StoreObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import supersim.Store;

/**
 *
 * @author Jens
 */
public class StoreObject {
    public Point location; // the indecies in the store layout matrix.
    public Color color;
    public Image bitmap;
    public Store store;
        
    public void onDraw(Graphics g, int panelWidth, int panelHeight){
        int cellWidth = panelWidth / store.layout.SIZE;
        int cellHeight = panelHeight / store.layout.SIZE;
        
        if (bitmap != null){
            g.drawImage(bitmap, cellWidth * location.x, cellHeight * location.y, null);
        }
        else {
            g.setColor(color);
            g.fillRect(cellWidth * location.x, cellHeight * location.y, cellWidth, cellHeight);
        }
    }
}
