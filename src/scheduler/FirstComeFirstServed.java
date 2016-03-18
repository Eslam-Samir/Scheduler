package scheduler;
import java.util.LinkedList;

import scheduler.Process;

public class FirstComeFirstServed extends  Schedular {
	
	FirstComeFirstServed(LinkedList<Process>processes)
	{
		this.processes=processes;
		this.numberOfProcesses = processes.size();
		this.run();
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
	public LinkedList<Process> run() {
		double time=0;
		while(!processes.isEmpty())
		{
			int first=getFirstJobIndex(processes);
			if(processes.get(first).getArrivalTime()<=time)
			{
				processes.get(first).setStartTime(time);
				output.addLast(processes.get(first));
				time += processes.get(first).getRunTime();
				processes.remove(processes.get(first));
	        }
	        else
	        {   	
	        	double idleTime= processes.get(first).getArrivalTime()-time;
		        Process idle=new Process("idle",idleTime,time);
		        idle.setStartTime(time);
		        output.addLast(idle);
		        time += idleTime;  
	        }
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
		double waitingTime = 0;
		double totalWaitingTime = 0;
		for(int i=0; i < size; i++)
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
