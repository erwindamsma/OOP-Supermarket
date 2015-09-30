/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supersim.timer;

import java.util.Date;

/**
 *
 * @author Jens
 */
public interface ITimeable {
    public void onTick(Date simulatedDate, float deltatime);
}
