/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supersim;

import java.sql.SQLException;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Jens
 */
public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
           Calendar c = Calendar.getInstance();
           c.setTime(new Date());
           c.set(Calendar.HOUR_OF_DAY, 8);
            SuperSim sim = new SuperSim(c.getTime());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "SQL Server not running!");
        }
    }
}
