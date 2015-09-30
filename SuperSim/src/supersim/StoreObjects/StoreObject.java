/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supersim.StoreObjects;

import java.awt.Color;
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
}
