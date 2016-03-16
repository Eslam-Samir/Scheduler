//import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import com.sun.scenario.effect.impl.prism.PrCropPeer;

public class FirstComeFirstServed extends  Schedular {
	FirstComeFirstServed(LinkedList<Process>processes){
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
	 private LinkedList<Process> run() {
			// TODO Auto-generated method stub
			LinkedList<Process> pWithSameAT=new LinkedList<Process>();
			int time=0;
			while(!processes.isEmpty())
	           {
			     int first=getFirstJobIndex(processes);

	              if(processes.get(first).getArrivalTime()<=time){
			          output.addLast(processes.get(first));
			          time+=processes.get(first).getRunTime();
			          processes.remove(processes.get(first));
	              	}
	               else{   	
	            	  int idleTime= processes.get(first).getArrivalTime()-time;
	            	  Process idle=new Process("idle",idleTime,time);
	            	  output.addLast(idle);
	            	  time+=idleTime;  
	            	   }
	          }			
		
			for(Process x:output){
				System.out.println(x.getName()+"    "+x.getRunTime());
				
			}
			return output;
	 }
	/*	Collections.sort(processes, new Comparator<Process>(){
			   @Override
			   public int compare(Process o1, Process o2){
			        if(o1.getArrivalTime() < o2.getArrivalTime()){
			           return -11; 
			        }
			        if(o1.getArrivalTime() > o2.getArrivalTime()){
			           return 1; 
			        }
			        return 0;
			   }
			}); */
		
		
	

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
