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
			
			for(int i=0;i<processes.size();i++){
				if (processes.get(i).getArrivalTime()%q !=0){
					processes.get(i).setArrivalTime(processes.get(i).getArrivalTime()+(q-processes.get(i).getArrivalTime()%q));
				output.addLast(processes.get(i));
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
			
			double tttime=0;
			int j=0;
			double remainderTime;
			for(int i = 0; i < processes.size(); i++){
				if(processes.get(i).getName()=="idle"){
					tttime+=processes.get(i).getRunTime();
					//processes.get(i+1).setStartTime(processes.get(i+1).getArrivalTime());
					output.get(j).setStartTime(output.get(j).getArrivalTime());
					j++;
					
				}
				else{
					if(processes.get(i).getRunTime()==output.get(j).getRunTime()
							&&processes.get(i).getName()==output.get(j).getName()){
						processes.get(i).setStartTime(tttime);
						output.get(j).setStartTime(tttime);
						j++;
						
					}
					if (processes.get(i).getRunTime()>q){
					  
						int firstIdle= getFirstIdle(processes,i);
						remainderTime=processes.get(i).getRunTime()-q; 
						Process p=new Process(processes.get(i).getName(),remainderTime
											,processes.get(i).getArrivalTime()+q);
						processes.get(i).setRunTime(q);
						tttime+=q;
						if(firstIdle==-1||firstIdle<i){
								processes.addLast(p);
							}
							else{
								processes.add(firstIdle, p);									
							}
						
					}
					else{
						tttime+=processes.get(i).getRunTime();
						 
					}
			}
				
			
			}
			
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
			}
			for(Process x:processes){
				System.out.println(x.getName()+"      "+x.getArrivalTime()+"    "+x.getRunTime()+"               "+x.getStartTime());
			}
			System.out.println("0=========================");
			for(Process x:output){
				System.out.println(x.getName()+"      "+x.getArrivalTime()+"    "+x.getRunTime()+"               "+x.getStartTime());
			}
			return processes;
		  }
		private int getFirstIdle(LinkedList<Process> processes,int i)
		{
			
			for(int j=i;i<processes.size();i++)
			{
				if(processes.get(j).getName() =="idle")
				{
					return processes.indexOf(processes.get(j));
				}
			}
			return -1;
		
		}	
		
		  
	}

