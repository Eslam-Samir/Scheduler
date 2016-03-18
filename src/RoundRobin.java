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

	@Override
	public double calculateWaitingTime() {
		// TODO Auto-generated method stub
		return 0;
	}
	private int getFirstIdle(LinkedList<Process> processes)
	{
		
		for(Process process: processes)
		{
			if(process.getName() =="idle")
			{
				return processes.indexOf(process);
			}
		}
		return -1;
	
	}	
	
	public LinkedList<Process> run()
	{
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
			
			
		double remainderTime;
		double time=0;
		for(int i = 0; i < processes.size(); i++){
			if (processes.get(i).getName()=="idle")continue;
			
			if (processes.get(i).getRunTime()>q){
				
				if(processes.get(i).getArrivalTime()<=time){
					int firstIdle=getFirstIdle(processes);
					remainderTime=processes.get(i).getRunTime()-q; 
					Process p=new Process(processes.get(i).getName(),remainderTime
										,(i+1)*q);
					if(firstIdle !=-1){
						processes.get(firstIdle).setRunTime(processes.get(firstIdle).getRunTime()-q);
						
						processes.add(firstIdle, p);
						
						processes.get(i).setRunTime(q);
						time+=q;
						
					}
					else {
						processes.addLast(p);
						processes.get(i).setRunTime(q);
						time+=q;
					}
				}
				else{
						double idleTime=processes.get(i).getArrivalTime()-time;
						Process idle=new Process("idle",idleTime,time);
						processes.add(i, idle);
						time+=idleTime;
				}
								
			}
			else{
				time+=processes.get(i).getRunTime();
			}
			
		}
		
	
		double ttime=0;
		for(int i=0;i<processes.size();i++){
			if(processes.get(i).getRunTime()<q){
			//	if (processes.get(i).getName()=="idle") continue ;
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

