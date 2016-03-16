
import java.util.LinkedList;


abstract public class Schedular {
	protected LinkedList<Process> processes ;
   // protected ArrayList<Process>queue;
	protected LinkedList<Process> output=new LinkedList<Process>();

    protected int avgWaitingTime;
    
    
   // public void run()
   public void AddProcess(Process p){
	   processes.add(p);
   }
 //  abstract public void run () ;
   abstract public int  calculateWaitingTime ();
    
}
