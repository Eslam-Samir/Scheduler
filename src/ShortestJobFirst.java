import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;


public class ShortestJobFirst extends  Schedular{
	
	
	ShortestJobFirst (LinkedList<Process>processes){
			this.processes=processes;
			this.run();
		}
		
	
	private void run() {
		// TODO Auto-generated method stub
		
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

	}

	@Override
	public int calculateWaitingTime() {
		// TODO Auto-generated method stub
				int size = processes.size();
				int waitingTime=0;
				int totalWaitingTime=0;
				for(int i=0;i<size;i++){
					totalWaitingTime +=waitingTime;
					waitingTime+=(processes.get(i)).getRunTime();
					
				}
				return totalWaitingTime;
	}

}
