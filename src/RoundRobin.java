import java.util.Collections;
import java.util.LinkedList;
import java.util.Comparator;

public class RoundRobin extends Schedular
{
	private int q;
	RoundRobin(LinkedList<Process> processes, int q)
	{
		this.processes = processes;
		this.q = q;
		
     	this.run();
	}
	
	
	double idleTime  = 0;
	double time = 0;
	
	
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
			
			if(processes.get(min).getArrivalTime() > timeLimit)
			
				min = processes.indexOf(process);
			
			
		}
		
		if(isProcessAvailable)
			return min;
		else
			return -1;
		
	}
	
	
	
	
	
	
	

	@Override
	public double calculateWaitingTime() 
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
	Process idle=new Process("idle",idleTime,time);
	
	
	
	public LinkedList<Process> run()
	{
		
		double remainderTime;
		for(int i = 0; i < processes.size(); i++){
			int first = nextJob(processes,time);
			
			
			//has waiting time 
			if(first == -1)
			{
				int firstAvailable = getFirstJobIndex(processes);
				double idleTime = processes.get(firstAvailable).getArrivalTime() - time;
				Process idle = new Process("idle",idleTime,time);
				output.addLast(idle);
				time +=idleTime;
				
			}
			
			
			// no waiting time but run time smaller then q so small part will b idle
			else if (processes.get(i).getRunTime()<q)

			{
				double idleTime = q-processes.get(i).getRunTime();
				//processes.add(idle);
				Process idle = new Process("idle",idleTime,time);
				idle.setStartTime(time);
				output.addLast(idle);
				time += q;
								
			}
			
			//no waiting time and the process run time is larger then q
			else if (processes.get(i).getRunTime()>q){
				remainderTime=processes.get(i).getRunTime()-q; 
				Process p=new Process(processes.get(i).getName(),remainderTime
									,(i+1)*q);
				//output.push
				processes.addLast(p);
				processes.get(i).setRunTime(q);
				time += q;
			}
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		}
		
		
		
		/*/Collections.sort(processes, new Comparator<Process>(){
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
			
			
		double time =0;
		for(int i =0;i<processes.size();i++){
			if(processes.get(i).getArrivalTime()>time){
				double idleTime=processes.get(i).getArrivalTime()-time;
				Process idle=new Process("idle",idleTime,time);
				processes.add(i, idle);
				time+=idleTime;
			
			}
			else{
				time+=processes.get(i).getRunTime();
			}
			
		}
		double ttime=0;
		for(int i=0;i<processes.size();i++){
			if(processes.get(i).getRunTime()<q){
				if (processes.get(i).getName()=="idle") continue ;
			  double idleTime=q-processes.get(i).getRunTime();
			  Process idle=new Process("idle",idleTime,ttime+processes.get(i).getRunTime());
			  processes.add(i+1, idle);
			  i++;
			  time+=q;
			  
			}
			else {
				time+=processes.get(i).getRunTime();
			}
		}/*/
		for(Process x:processes){
			System.out.println(x.getName()+"      "+x.getArrivalTime()+"    "+x.getRunTime());
		}
		return processes;
	  }
		
	  
}
