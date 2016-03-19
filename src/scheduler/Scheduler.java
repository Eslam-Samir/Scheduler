package scheduler;
import java.util.LinkedList;

import scheduler.Process;

abstract public class Scheduler {
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
