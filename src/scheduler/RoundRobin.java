package scheduler;
import java.util.LinkedList;

import scheduler.Process;


public class RoundRobin extends Scheduler
{
	private int q;
	
	public RoundRobin(LinkedList<Process> processes, int q)
	{
		this.processes = processes;
		this.q = q;
	}
		
	@Override
	public double calculateWaitingTime() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public LinkedList<Process> run()
	{
		
		double remainderTime;
		for(int i = 0; i < processes.size(); i++){
			
			if (processes.get(i).getRunTime()>q){
				remainderTime=processes.get(i).getRunTime()-q; 
				Process p=new Process(processes.get(i).getName(),remainderTime
									,processes.get(i).getArrivalTime(),0);
				processes.addLast(p);
			}
			System.out.println(processes.get(i).getName() + "    " + processes.get(i).getRunTime());
		}
		return output;

	}
		
	  
			
		}
		

