
import java.util.LinkedList;

public class ShortestJobFirst extends  Schedular{

	ShortestJobFirst (LinkedList<Process>processes)
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

	public LinkedList<Process> run() 
	{
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
			if(output.get(i).getName().equals("idle"))
				continue;
			waitingTime = output.get(i).getStartTime() - output.get(i).getArrivalTime();
			totalWaitingTime += waitingTime;
		}
		avgWaitingTime = totalWaitingTime/numberOfProcesses;
		return avgWaitingTime;
	}

}
