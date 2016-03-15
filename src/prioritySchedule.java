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

	
	
	
	/*/private int getHighestPriorityIndex(LinkedList<Process> processes)
	{
		int max = 0;
		for(Process process: processes)
		{
			if(process.getPriority() < processes.get(max).getPriority())
			{
				max = processes.indexOf(process);
			}
			else if(process.getPriority() >= processes.get(max).getPriority() 
					&& process.getArrivalTime() < processes.get(max).getArrivalTime())
			{
					max = processes.indexOf(process);
			}	
		}
		return max;
	}	/*/
	
	
	
	
	public void run(){
		
		int max = 0;
		while (!processes.isEmpty())
		{
			for(Process process: processes)
			
			{
				if(process.getPriority() <= processes.get(max).getPriority())
						
				{
						max = processes.indexOf(process);
						
				}	
				
				
				else if(process.getPriority() == processes.get(max).getPriority() 
						&& process.getArrivalTime() < processes.get(max).getArrivalTime())
				{
						max = processes.indexOf(process);
				}	
				output.addFirst(processes.get(max));
				processes.remove(processes.get(max));
			}
		}	
		
		
		for(Process x: output)
		{
			System.out.println(x.getName() + "   " + x.getPriority() + "   " + x.getArrivalTime());
				}
		
		
		
		
		
		

	/*/Collections.sort(processes, new Comparator<Process>(){
			   @Override
			   public int compare(Process o1, Process o2){
			       if(o1.getPriority() < o2.getPriority()){
			          return -1; 
			        }
			       if(o1.getPriority() > o2.getPriority()){
			           return 1; 
			        }
			        return 0;
			   }
		}); /*/

	
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