import java.util.LinkedList;


public class RoundRobin extends Schedular
{
	private int q;
	RoundRobin(LinkedList<Process> processes, int q)
	{
		this.processes = processes;
		this.q = q;
		
     	this.run();
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
									,(i+1)*q);
				processes.addLast(p);
				processes.get(i).setRunTime(q);
			}
			
		}
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
			  double idleTime=q-processes.get(i).getRunTime();
			  Process idle=new Process("idle",idleTime,ttime+processes.get(i).getRunTime());
			  processes.add(i+1, idle);
			  i++;
			  time+=q;
			  
			}
			else {
				time+=processes.get(i).getRunTime();
			}
		}
		for(Process x:processes){
			System.out.println(x.getName()+"      "+x.getArrivalTime()+"    "+x.getRunTime());
		}
		return processes;
	  }
		
	  
}
