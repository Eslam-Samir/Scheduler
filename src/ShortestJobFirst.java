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
		case primitive:
			
			while(!processes.isEmpty())
			{
				int min = getShortestJobIndex(processes);
				if(!output.isEmpty() && processes.get(min).getArrivalTime() < output.get(0).getArrivalTime())
				{
					Process startProcess = new Process(processes.get(min).getName(), 
							(output.get(0).getArrivalTime() - processes.get(min).getArrivalTime()), 
							processes.get(min).getArrivalTime());
					processes.get(min).setRunTime(processes.get(min).getRunTime() - startProcess.getRunTime());
						
					output.addFirst(startProcess);
					if(processes.get(min).getRunTime() == 0)
						processes.remove(processes.get(min));
				}
				else
				{
					output.addLast(processes.get(min));
					processes.remove(processes.get(min));
				}
				
			}
			
			for(Process x: output)
			{
				System.out.println(x.getName() + "   " + x.getRunTime() + "   " + x.getArrivalTime());
			}
			
			
			
			
			
			
			
			break;

		case non_primitive:
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

}
