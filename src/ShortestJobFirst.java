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
			while(!processes.isEmpty())
			{
				int min = getShortestJobIndex(processes);
				Process currentProcess = processes.get(min);
				int index = gapIndex();
				
				if(!output.isEmpty() && currentProcess.getArrivalTime() < output.get(index).getArrivalTime())
				{
					int freeTime = getFreeTime(index);
					
					if(freeTime < currentProcess.getRunTime() && output.get(index).getArrivalTime() - currentProcess.getArrivalTime() < freeTime)
					{
						insertToGap(currentProcess, output.get(index).getArrivalTime() - currentProcess.getArrivalTime(), index);
					}
					else if(freeTime < currentProcess.getRunTime())
					{
						insertToGap(currentProcess, freeTime, index);
					}
					else
					{
						currentProcess.setStartedTime(currentProcess.getArrivalTime());
						output.add(index,currentProcess);
						processes.remove(currentProcess);						
					}
				}
				else
				{
					currentProcess.setStartedTime(currentProcess.getArrivalTime());
					output.addLast(currentProcess);
					processes.remove(currentProcess);
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
	
	private int getShortestJobIndex(LinkedList<Process> processes)
	{
		int min = 0;
		for(Process process: processes)
		{
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
		return min;
	}

	private void insertToGap(Process currentProcess, int runTime, int index)
	{
		Process startProcess = new Process(currentProcess.getName(), 
				runTime, 
				currentProcess.getArrivalTime());
		startProcess.setStartedTime(output.get(index).getArrivalTime() - startProcess.getRunTime());
		currentProcess.setRunTime(currentProcess.getRunTime() - runTime);
		output.add(index,startProcess);
		if(currentProcess.getRunTime() == 0)
			processes.remove(currentProcess);
	}
	
	private int getFreeTime(int index)
	{
		if(index == 0)
			return output.get(index).getArrivalTime();
		else
			return output.get(index).getArrivalTime() - (output.get(index-1).getArrivalTime()+output.get(index-1).getRunTime());
	}
	
	private int gapIndex()
	{
		for(int i = 1; i < output.size(); i++)
		{
			if(output.get(i).getStartedTime() - output.get(i-1).getStartedTime() > output.get(i-1).getRunTime())
			{
				return i;
			}
		}
		return 0;
	}
}
