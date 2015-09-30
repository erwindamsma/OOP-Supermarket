/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supersim;

import java.time.Instant;
import java.util.Date;

/**
 *
 * @author Jens
 */
public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SuperSim sim = new SuperSim(Date.from(Instant.EPOCH));
    }
}
