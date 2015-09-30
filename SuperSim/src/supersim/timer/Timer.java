/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supersim.timer;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jens
 */
public class Timer {
    private final TimerThread timerThread;
    private final ArrayList<ITimeable> subscribers;
    public float speed = 1f;
    private final Date simulatedDate;
    private long lastTickTime;
    
    
    
    public Timer(Date startDate)
    {
        subscribers = new ArrayList<ITimeable>();
        timerThread = new TimerThread(this);
        
        simulatedDate = startDate;
        //this.interval = interval;
    }
    
    
    public void start()
    {
        if(!timerThread.shouldRun)
        {
            timerThread.shouldRun = true;
            this.lastTickTime = new Date().getTime();
            timerThread.start();
        }
    }
    
    public void stop()
    {
        if(timerThread.shouldRun)
        {
            timerThread.shouldRun = false;
        }
    }

    public void addSubscriber(ITimeable t)
    {
        System.out.println("Added new subsriber: " + t.toString());
        subscribers.add(t);
    }
    
    public void removeSubscriber(ITimeable t)
    {
        subscribers.remove(t);
    }
    
    public class TimerThread extends Thread
    {
        Timer timer;
        public boolean shouldRun = false;
        
        public TimerThread(Timer timer)
        {
            this.timer = timer;
            this.setName("Time-Simulation-Thread");
        }
        
        @Override
        public void run() {
            while(shouldRun)
            {
                tick();
            }
        } 
        
        float culdelta = 0;
        //Called each time 
        public void tick()
        {
            long now = new Date().getTime();
            float deltaTime = now - lastTickTime;
            lastTickTime = now;

            simulatedDate.setTime((long)(simulatedDate.getTime() + (deltaTime * speed)));

            //We make a copy so the list wont be altered while we loop over it.
            ArrayList<ITimeable> subscopy = (ArrayList<ITimeable>)subscribers.clone();

            //Debugging
            if(culdelta > 1000)
            {
                System.out.println("New time: "+simulatedDate.toString());
                culdelta = 0;
            }
            culdelta += deltaTime;



            for(ITimeable t : subscopy)
            {

                t.onTick(simulatedDate, deltaTime);
            }

        }
        
    }

}


