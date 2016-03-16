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
		      int min = getShortestJobIndex(processes);

              if(processes.get(min).getArrivalTime()<=time){
		          output.addLast(processes.get(min));
		          time+=processes.get(min).getRunTime();
		          processes.remove(processes.get(min));
              	}
               else{
            	   	for(int i=0;i<processes.size();i++){
            	   			if (processes.get(i).getArrivalTime()<=time)
            	   		{
            	   	pWithSameAT.add(processes.get(i));
            	   	    }
        	   }
            	   	if(pWithSameAT.isEmpty()){
            	   		int first=getFirstJobIndex(processes);
            	   		int idleTime= processes.get(first).getArrivalTime()-time;
            	   		Process idle=new Process("idle",idleTime,time);
            	   		output.addLast(idle);
            	   		time+=idleTime;
            	   	}
            	   	else{
        		int min1=getShortestJobIndex(pWithSameAT);
        		output.addLast(processes.get(min1));
                time+=processes.get(min1).getRunTime();
        		processes.remove(processes.get(min1));	
            	   	}
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
