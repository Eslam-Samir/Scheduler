
import java.util.LinkedList;


abstract public class Schedular {
	protected LinkedList<Process> processes ;
	
    protected int avgWaitingTime;
    
    
   // public void run()
   public void AddProcess(Process p){
	   processes.add(p);
   }
 //  abstract public void run () ;
   abstract public int  calculateWaitingTime ();
    
}
