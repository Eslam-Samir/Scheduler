//import java.util.Collections;
//import java.util.Comparator;
import java.util.LinkedList;
//import java.util.ListIterator;


public class ShortestJobFirst extends  Schedular{
	
	//private LinkedList<Process> output=new LinkedList<Process>();

	ShortestJobFirst (LinkedList<Process>processes){
			this.processes=processes;
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
	
	
	

	
	 private LinkedList<Process> run() {
		// TODO Auto-generated method stub
		 int time=0;
		 while(!processes.isEmpty()){
		 
		// int gab;
	
		int first=getShortestJobIndexLimited(processes, time);
		if (first==-1){
			int firstAvailable=getFirstJobIndex(processes);
			int idleTime= processes.get(firstAvailable).getArrivalTime()-time;
	   		Process idle=new Process("Idle",idleTime,time);
	   		output.addLast(idle);
	   		time+=idleTime;
		}
		else{
			output.addLast(processes.get(first));
		    time+=processes.get(first).getRunTime();
		    processes.remove(first);
		}
		 }
		 for(Process x:output){
				System.out.println(x.getName()+"    "+x.getRunTime());
				
			}
		 return output;
 }

	@Override
	public int calculateWaitingTime() {
		// TODO Auto-generated method stub
				int size = output.size();
				int waitingTime=0;
				int totalWaitingTime=0;
				for(int i=0;i<size;i++){
					totalWaitingTime +=waitingTime;
					waitingTime+=(output.get(i)).getRunTime();
					
				}
				return totalWaitingTime;
	}
	

}
