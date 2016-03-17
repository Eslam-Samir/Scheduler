//import java.util.Collections;
//import java.util.Comparator;
import java.util.LinkedList;
//import com.sun.scenario.effect.impl.prism.PrCropPeer;


public class prioritySchedule extends Schedular 

{

	prioritySchedule (LinkedList<Process>processes){
		this.processes=processes;		
		this.run();
	}
	
	
	
	
	
	private LinkedList<Process> output = new LinkedList<Process>();
	//private LinkedList<Process> priority = new LinkedList<Process>();
	
	
	
	private int getPriorityIndexLimited(LinkedList<Process> processes, int timeLimit)
	{
		int min = 0;
		boolean isProcessAvailable = false;
		for(Process process: processes)
		{
			if(process.getArrivalTime() >timeLimit)
				continue;
			else
				isProcessAvailable = true;
			
			if(process.getPriority() > processes.get(min).getPriority())
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
	
		
	
	
	
	public void run(){
		int limit = 0;
		while(!processes.isEmpty())
			
        {	
			//scheduling according to priority
			for(int i =0;i<processes.size();i++)
        	
        {	
        	int first = getPriorityIndexLimited(processes, limit);
        	output.addFirst(processes.get(first));
        	limit = limit+processes.get(first).getRunTime();
        	processes.remove(processes.get(first));
        	
        	
        }
			for(Process x: output)
			{
				System.out.println(x.getName() + "   " + x.getPriority() + "   " + x.getArrivalTime());
			}
			
			
			/*/int time = 0;
		     int first=getHighestPriorityIndex(processes);

           if(processes.get(first).getArrivalTime()<=time){
		          output.addFirst(processes.get(first));
		          time+=processes.get(first).getRunTime();
		          processes.remove(processes.get(first));
           	}/*/
            
         	   
	        
        
	
		
		
		
		
		
		
		
		
		
		/*/int max = 0;
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
		}	/*/
		
		
		
        }
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