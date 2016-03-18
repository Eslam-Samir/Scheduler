package scheduler;
import java.util.LinkedList;

import scheduler.Process;
import extras.Utility;

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
		double timeLimit = 0;
		switch (type) 
		{
		case preemptive:
			while(!processes.isEmpty())
			{
				int min = Utility.getShortestJobIndexLimited(processes, timeLimit);
				double nextArrival = Utility.getNextArrivalTime(processes, timeLimit);
				
				if(min == -1)
				{
					Process idleProcess = new Process("idle", nextArrival - timeLimit, timeLimit);
					idleProcess.setStartTime(timeLimit);
					output.addLast(idleProcess);
					timeLimit = nextArrival;
				}
				else if(nextArrival == 0 || timeLimit + processes.get(min).getRunTime() <= nextArrival)
				{
					processes.get(min).setStartTime(timeLimit);
					output.addLast(processes.get(min));
					timeLimit += processes.get(min).getRunTime();
					processes.remove(processes.get(min));
				}
				else if(timeLimit + processes.get(min).getRunTime() > nextArrival)
				{
					Process currentProcess = processes.get(min);
					int nextMin = Utility.getShortestJobIndexLimited(processes, nextArrival);
					if(nextMin == min)
					{
						currentProcess.setStartTime(timeLimit);
						output.addLast(currentProcess);
						timeLimit += currentProcess.getRunTime();
						processes.remove(currentProcess);
					}
					else
					{
						Process pausedProcess = new Process(currentProcess.getName()
								, nextArrival - timeLimit, currentProcess.getArrivalTime());
						currentProcess.setRunTime(currentProcess.getRunTime()-pausedProcess.getRunTime());
						pausedProcess.setStartTime(timeLimit);
						if(currentProcess.getRunTime() == 0)
							processes.remove(currentProcess);
						output.addLast(pausedProcess);
						timeLimit = nextArrival;
					}
				}
			}
		break;
		case non_preemptive:
			while(!processes.isEmpty())
			{	
				int first = Utility.getShortestJobIndexLimited(processes, timeLimit);
				if (first == -1)
				{
					int firstAvailable = Utility.getFirstJobIndex(processes);
					double idleTime = processes.get(firstAvailable).getArrivalTime() - timeLimit;
					Process idle = new Process("idle",idleTime,timeLimit);
					idle.setStartTime(timeLimit);
					output.addLast(idle);
					timeLimit += idleTime;
				}
				else
				{
					processes.get(first).setStartTime(timeLimit);
					output.addLast(processes.get(first));
					timeLimit += processes.get(first).getRunTime();
					processes.remove(first);
				}
			}
		break;
		}
		for(Process x:output)
		{
			System.out.println(x.getName()+"    "+x.getRunTime());	
		}
		return output;
	}

	@Override
	public double calculateWaitingTime() 
	{
		int size = output.size();
		double waitingTime=0;
		double totalWaitingTime=0;
		switch (this.type) 
		{
		case preemptive:
			for(int i = 0;i<size;i++)
			{
				if(output.get(i).getName().equals("idle"))
					continue;
				int previousOccurance = Utility.getPrevious(output, i - 1, output.get(i).getName());
				if(previousOccurance == -1)
				{
					waitingTime = output.get(i).getStartTime() - output.get(i).getArrivalTime();
				}
				else
				{
					waitingTime = output.get(i).getStartTime() 
							- (output.get(previousOccurance).getStartTime() + output.get(previousOccurance).getRunTime());
				}
				totalWaitingTime += waitingTime;
			}
			break;
		case non_preemptive:
			for(int i = 0;i<size;i++)
			{
				if(output.get(i).getName().equals("idle"))
					continue;
				waitingTime = output.get(i).getStartTime() - output.get(i).getArrivalTime();
				totalWaitingTime += waitingTime;
			}
			break;	
		}
		if(numberOfProcesses != 0)
			avgWaitingTime = totalWaitingTime/numberOfProcesses;
		return avgWaitingTime;
	}

}
