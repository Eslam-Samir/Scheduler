//import java.util.Collections;
//import java.util.Comparator;
import java.util.LinkedList;



public class prioritySchedule extends Schedular 

{
	prioritySchedule (LinkedList<Process>processes){
		this.processes=processes;		
		this.run();
	}

	private LinkedList<Process> output = new LinkedList<Process>();
	
	
	
	private int getPriorityIndexLimited(LinkedList<Process> processes, int timeLimit)
	{
		int min = 0;
		boolean isProcessAvailable = false;
		for(Process process: processes)
		{
			if(process.getArrivalTime() >timeLimit)
				continue;
			else
				isProcessAvailable = true;
			
			if(process.getPriority() < processes.get(min).getPriority())
			{
				min = processes.indexOf(process);
			}
			else if(process.getPriority() == processes.get(min).getPriority() 
					&& process.getArrivalTime() < processes.get(min).getArrivalTime())
			{
					min = processes.indexOf(process);
			}	
		}
		
		if(isProcessAvailable)
			return min;
		else
			return -1;
	}
	
	private int getNextArrivalTime(LinkedList<Process> processes, int timeLimit)
	{
		int nextArrival = 0;	
		
		for(Process process: processes)
		{
			if(process.getArrivalTime() <= timeLimit)
				continue;				
				
			if(nextArrival > process.getArrivalTime() || nextArrival == 0)
			{
				nextArrival = process.getArrivalTime();
			}	
		}
		return nextArrival;
	}
	
	
	
	
	
	
	public LinkedList<Process> run()
	{
		int limit = 0;
		for(int i =0;i<processes.size();i++)
			
        {	
			//scheduling according to priority
			
        	int first = getPriorityIndexLimited(processes, limit);
        	if(first == -1)
        	{
        		int arrival = getNextArrivalTime(processes,limit);
        		int idleTime = processes.get(arrival).getArrivalTime() -limit;
        		Process idle = new Process("idle",idleTime,limit,processes.get(i).getPriority());
        		output.addLast(idle);
        		limit = getNextArrivalTime(processes, limit);
        		
        	}
        	else{
        	output.addLast(processes.get(first));
        	limit = limit+processes.get(first).getRunTime();
        	processes.remove(processes.get(first));
        	}
        	
        }
			for(Process x: output)
			{
				System.out.println(x.getName() + "   " + x.getPriority() + "   " + x.getArrivalTime());
			}
        return output;
	}
	
	
	
	
	
	
	
	
	
	
	

	@Override
	public int calculateWaitingTime() {
		// TODO Auto-generated method stub
		int size = processes.size();
		int waitingTime=0;
		int totalWaitingTime=0;
		for(int i=0;i<size;i++){
			totalWaitingTime +=waitingTime;
			waitingTime+=(output.get(i)).getRunTime();
		
		}
		return totalWaitingTime;
	}
}