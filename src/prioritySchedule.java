import java.util.LinkedList;

public class prioritySchedule extends Schedular {
	
	prioritySchedule (LinkedList<Process>processes){
		this.processes=processes;		
		this.run();
	}

	private int getPriorityIndexLimited(LinkedList<Process> processes, double timeLimit)
	{
		int min = 0;
		boolean isProcessAvailable = false;
		for(Process process: processes)
		{
			if(process.getArrivalTime() >timeLimit)
				continue;
			else
				isProcessAvailable = true;
			
			if(processes.get(min).getArrivalTime() > timeLimit)
			{
				min = processes.indexOf(process);
			}
			else if(process.getPriority() < processes.get(min).getPriority())
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
	
	private int getFirstJobIndex(LinkedList<Process> processes)
	{
		int min = 0;
		for(Process process: processes)
		{
			if(process.getArrivalTime() < processes.get(min).getArrivalTime())
			{
				min = processes.indexOf(process);
			}		
		}
		return min;
	}

	
	public LinkedList<Process> run()
	{
		double limit = 0;
		while(!processes.isEmpty())
        {	
			//scheduling according to priority
        	int first = getPriorityIndexLimited(processes, limit);
        	if(first == -1)
        	{
        		int next = getFirstJobIndex(processes);
        		double arrival = processes.get(next).getArrivalTime();
        		double idleTime = arrival - limit;
        		Process idle = new Process("idle",idleTime,limit,0);
        		output.addLast(idle);
        		limit = arrival;
        	}
        	else
        	{
	        	output.addLast(processes.get(first));
	        	limit += processes.get(first).getRunTime();
	        	processes.remove(processes.get(first));
        	}
        	
        }
		for(Process x: output)
		{
			System.out.println(x.getName() + "	" + x.getPriority() + "	" + x.getRunTime()  + "	" + x.getArrivalTime());
		}
        return output;
	}	

	@Override
	public double calculateWaitingTime() 
	{
		int size = processes.size();
		double waitingTime=0;
		double totalWaitingTime=0;
		for(int i=0;i<size;i++){
			totalWaitingTime +=waitingTime;
			waitingTime+=(output.get(i)).getRunTime();
		
		}
		return totalWaitingTime;
	}
}