import java.util.LinkedList;


public class RoundRobin extends Schedular
{
	private int q;
	
	RoundRobin(LinkedList<Process> processes, int q)
	{
		this.processes = processes;
		this.q = q;

		//making an array for the run time of all processes
		
		this.run();
	}
		
	@Override
	public int calculateWaitingTime() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private void run()
	{
		
		int remainderTime;
		for(int i = 0; i < processes.size(); i++){
			
			if (processes.get(i).getRunTime()>q){
				remainderTime=processes.get(i).getRunTime()-q; 
				Process p=new Process(processes.get(i).getName(),remainderTime
									,processes.get(i).getArrivalTime(),0);
				processes.addLast(p);
			}
			System.out.println(processes.get(i).getName() + "    " + processes.get(i).getRunTime());
		}

	}
		
	  
			
		}
		

