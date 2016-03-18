import java.util.LinkedList;


public class Mymain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	    Process p1 = new Process("P1",20,5,3);
	    Process p2=new Process("P2",10,6,2);
	    Process p3=new Process("P3",5,2,4);
	    Process p4=new Process("P4",15,37,1);
	    LinkedList<Process>processes1=new LinkedList<Process>();
	    processes1.add(p1);
	    processes1.add(p2);
	    processes1.add(p3);
	    processes1.add(p4);
	    prioritySchedule f1 = new prioritySchedule(processes1);
	    System.out.println(f1.calculateWaitingTime());
	   /* ShortestJobFirst f2 =new ShortestJobFirst(processes1);
	    System.out.println(f2.calculateWaitingTime());*/
	
		Process p5 = new Process("P1",6,0);
		Process p6=new Process("P2",8,0);
		Process p7=new Process("P3",7,23);
		Process p8=new Process("P4",3,0);
			    
			    
		LinkedList<Process>processes2=new LinkedList<Process>();
		processes2.add(p1);
		processes2.add(p2);
		processes2.add(p3);
		processes2.add(p4);
		//FirstComeFirstServed f3=new FirstComeFirstServed(processes2);
		//System.out.println(f3.calculateWaitingTime());
		//ShortestJobFirst f4 = new ShortestJobFirst(processes2);
		//System.out.println(f4.calculateWaitingTime());
	}
}
