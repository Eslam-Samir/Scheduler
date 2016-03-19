package scheduler;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import extras.Constants;
import extras.Utility;
import scheduler.Process;


public class RoundRobin extends Scheduler
{
	private double q;
	public RoundRobin(LinkedList<Process> processes, double q)
	{
		this.processes = processes;
		this.numberOfProcesses = processes.size();
		this.q = q;
	}
	
	private int nextJob(LinkedList<Process> processes, double timeLimit)
	{
		int min = 0;
		boolean isProcessAvailable = false;
		for(Process process: processes)
		{
			if(process.getArrivalTime() > timeLimit)
				continue;
			else
				isProcessAvailable = true;
			
			if(processes.get(min).getArrivalTime() < timeLimit)
				min = processes.indexOf(process);
		}
		
		if(isProcessAvailable)
			return min;
		else
			return -1;
	}
	
	public LinkedList<Process> run()
	{
		double timeLimit = 0;
		
		// sort the linkedlist according to arrival time
		Collections.sort(processes, new Comparator<Process>() {
			@Override
			public int compare(Process o1, Process o2) {
				if(o1.getArrivalTime() < o2.getArrivalTime()){
					return -1; 
			    }
			    if(o1.getArrivalTime() > o2.getArrivalTime()){
			    	return 1; 
			    }
				return 0;
			}
		});
		
		while(!processes.isEmpty())
		{
			int next = nextJob(processes, timeLimit);
			if(next == -1)
			{
				Process idle = new Process(0, Constants.IDLE, q, timeLimit);
				idle.setStartTime(idle.getArrivalTime());
				output.addLast(idle);
				timeLimit += q;
				continue;
			}
			
			for(int i = 0; i < processes.size(); i++)
			{
				// because the linkedlist is sorted if the arrival time of a process > limit
				// then the rest have arrival time > limit
				if(processes.get(i).getArrivalTime() > timeLimit)
					break;
				else
				{
					Process current = processes.get(i);
					if(current.getRunTime() < q)
					{
						current.setStartTime(timeLimit);
						output.addLast(current);
						timeLimit += current.getRunTime();
						processes.remove(current);
						i--;
					}
					else if(current.getRunTime() == q)
					{
						current.setStartTime(timeLimit);
						output.addLast(current);
						timeLimit += q;
						processes.remove(current);
						i--;
					}
					else
					{
						Process currentPart = new Process(current.getPid(), current.getName(),
								q, current.getArrivalTime());
						currentPart.setStartTime(timeLimit);
						output.addLast(currentPart);
						timeLimit += q;
						current.setRunTime(current.getRunTime()-q);
					}
				}
			}
		}
				
		for(Process x:output)
		{
			System.out.println(x.getName()+"      "+x.getStartTime()+"    "+x.getRunTime());
		}
		return output;
	  }
	
	@Override
	public double calculateWaitingTime() 
	{
		int size = output.size();
		double waitingTime=0;
		double totalWaitingTime=0;
		for(int i = 0;i<size;i++)
		{
			if(output.get(i).getPid() == 0)
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
		if(numberOfProcesses != 0)
			avgWaitingTime = totalWaitingTime/numberOfProcesses;
		return avgWaitingTime;
	}
			
}
		

