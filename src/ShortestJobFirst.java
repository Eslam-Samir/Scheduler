import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;


public class ShortestJobFirst extends  Schedular{
	
	private SchedulerType type;
	private LinkedList<Process> output; 
	ShortestJobFirst (LinkedList<Process>processes, SchedulerType type){
		this.processes=processes;
		
		this.type = type;
		output = new LinkedList<>();
		
		this.run();
	}
		
	
	private void run() {
		switch (type) {
		case preemptive:
			int timeLimit = 0;
			while(!processes.isEmpty())
			{
				int min = getShortestJobIndexLimited(processes, timeLimit);
				int nextArrival = getNextArrivalTime(processes, timeLimit);
				
				if(min == -1)
				{
					Process idleProcess = new Process("Idle", nextArrival - timeLimit, timeLimit);
					output.addLast(idleProcess);
					timeLimit = nextArrival;
				}
				else if(nextArrival == 0 || timeLimit + processes.get(min).getRunTime() <= nextArrival)
				{
					output.addLast(processes.get(min));
					timeLimit += processes.get(min).getRunTime();
					processes.remove(processes.get(min));
				}
				else if(timeLimit + processes.get(min).getRunTime() > nextArrival)
				{
					Process currentProcess = processes.get(min);
					int nextMin = getShortestJobIndexLimited(processes, nextArrival);
					if(nextMin == min)
					{
						output.addLast(currentProcess);
						timeLimit += currentProcess.getRunTime();
						processes.remove(currentProcess);
					}
					else
					{
						Process pausedProcess = new Process(currentProcess.getName()
								, nextArrival - timeLimit, currentProcess.getArrivalTime());
						currentProcess.setRunTime(currentProcess.getRunTime()-pausedProcess.getRunTime());
						
						if(currentProcess.getRunTime() == 0)
							processes.remove(currentProcess);
						
						output.addLast(pausedProcess);
						timeLimit = nextArrival;
					}
					
				}
				
			}
			
			for(Process x: output)
			{
				System.out.println(x.getName() + "   " + x.getRunTime() + "   " + x.getArrivalTime());
			}
				
			break;

		case non_preemptive:
			Collections.sort(processes, new Comparator<Process>(){
				   @Override
				   public int compare(Process o1, Process o2){
				        if(o1.getRunTime() < o2.getRunTime()){
				           return -1; 
				        }
				        if(o1.getRunTime() > o2.getRunTime()){
				           return 1; 
				        }
				        return 0;
				   }
				}); 
			Collections.sort(processes, new Comparator<Process>(){
				   @Override
				   public int compare(Process o1, Process o2){
				        if(o1.getArrivalTime() < o2.getArrivalTime()){
				           return -1; 
				        }
				        if(o1.getArrivalTime() > o2.getArrivalTime()){
				           return 1; 
				        }
				        return 0;
				   }
				}); 
			break;
		}
		

	}

	@Override
	public int calculateWaitingTime() {
				int size = processes.size();
				int waitingTime=0;
				int totalWaitingTime=0;
				for(int i=0;i<size;i++){
					totalWaitingTime +=waitingTime;
					waitingTime+=(processes.get(i)).getRunTime();
					
				}
				return totalWaitingTime;
	}
	
	// return -1 if there is no process to schedule
	private int getShortestJobIndexLimited(LinkedList<Process> processes, int timeLimit)
	{
		int min = 0;
		boolean isProcessAvailable = false;
		for(Process process: processes)
		{
			if(process.getArrivalTime() > timeLimit)
				continue;
			else
				isProcessAvailable = true;
			
			if(process.getRunTime() < processes.get(min).getRunTime())
			{
				min = processes.indexOf(process);
			}
			else if(process.getRunTime() == processes.get(min).getRunTime() 
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
	
	// return zero if all processes have arrived
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
}
