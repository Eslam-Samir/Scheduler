import java.util.LinkedList;

public class ShortestJobFirst extends  Schedular{

	private SchedulerType type;
	ShortestJobFirst (LinkedList<Process>processes, SchedulerType type)
	{
		this.processes=processes;
		this.numberOfProcesses = processes.size();
		this.type = type;
		this.run();
	}
	
	
	public LinkedList<Process> run() {
		switch (type) 
		{
		case preemptive:
			double timeLimit = 0;
			while(!processes.isEmpty())
			{
				int min = getShortestJobIndexLimited(processes, timeLimit);
				double nextArrival = getNextArrivalTime(processes, timeLimit);
				
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
			double time = 0;
			while(!processes.isEmpty())
			{	
				int first = getShortestJobIndexLimited(processes, time);
				if (first == -1)
				{
					int firstAvailable = getFirstJobIndex(processes);
					double idleTime = processes.get(firstAvailable).getArrivalTime() - time;
					Process idle = new Process("idle",idleTime,time);
					idle.setStartTime(time);
					output.addLast(idle);
					time += idleTime;
				}
				else
				{
					processes.get(first).setStartTime(time);
					output.addLast(processes.get(first));
					time += processes.get(first).getRunTime();
					processes.remove(first);
				}
			}
			for(Process x:output)
			{
				System.out.println(x.getName()+"    "+x.getRunTime());	
			}
			break;
		}
			return output;
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

	// return -1 if there is no process to schedule
	private int getShortestJobIndexLimited(LinkedList<Process> processes, double timeLimit)
	{
		int min = 0;
		boolean isProcessAvailable = false;
		for(Process process: processes)
		{
			if(process.getArrivalTime() > timeLimit)
				continue;
			else
				isProcessAvailable = true;
			
			if(processes.get(min).getArrivalTime() > timeLimit)
			{
				min = processes.indexOf(process);
			}
			else if(process.getRunTime() < processes.get(min).getRunTime())
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
	private double getNextArrivalTime(LinkedList<Process> processes, double timeLimit)
	{
		double nextArrival = 0;	
		
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

	@Override
	public double calculateWaitingTime() 
	{
		int size = output.size();
		double waitingTime=0;
		double totalWaitingTime=0;
		for(int i = 0;i<size;i++)
		{
			if(output.get(i).getName().equals("idle"))
				continue;
			waitingTime = output.get(i).getStartTime() - output.get(i).getArrivalTime();
			totalWaitingTime += waitingTime;
		}
		if(numberOfProcesses != 0)
			avgWaitingTime = totalWaitingTime/numberOfProcesses;
		return avgWaitingTime;
	}

}
