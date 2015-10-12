/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supersim.Resources;

import java.io.InputStream;

/**
 *
 * @author Jens
 */
public class Loader {
    public static InputStream getResource(String path)
    {
        return Loader.class.getResourceAsStream(path);
    }
}
