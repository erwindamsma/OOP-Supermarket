package supermarket;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.SwingWorker;
import javax.swing.Timer;

public class Worker extends SwingWorker<Integer, Integer> {

    Timer timer = new Timer(100, new MyTimerActionListener());
    
    public Worker(){
    }
    
    @Override
    protected Integer doInBackground() throws Exception {
        timer.start();
        return 1; //Has to return something...
    }
    
    class MyTimerActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            tick();
        }
    }
    
    private void tick(){
        Random random = new Random(0);
        System.out.println(random.nextInt(4));
    }
}