import java.util.LinkedList;

abstract public class Schedular {
	protected LinkedList<Process> processes ;
	protected LinkedList<Process> output = new LinkedList<Process>();

    protected double avgWaitingTime = 0;
    protected int numberOfProcesses;
    
    public void AddProcess(Process p)
    {
    	processes.add(p);
    	numberOfProcesses++;
    }
    
    abstract public LinkedList<Process> run();
    abstract public double calculateWaitingTime ();
    
}
