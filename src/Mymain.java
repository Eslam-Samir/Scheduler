import java.util.LinkedList;


public class Mymain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	    Process p1 = new Process("P1",20,5,3);
	    Process p2=new Process("P2",10,6,2);
	    Process p3=new Process("P3",5,2,4);
	    Process p4=new Process("P4",15,0,1);
	    LinkedList<Process>processes1=new LinkedList<Process>();
	    processes1.add(p1);
	    processes1.add(p2);
	    processes1.add(p3);
	    processes1.add(p4);
	    prioritySchedule f1 = new prioritySchedule(processes1);
	    
/*	    System.out.println(f1.calculateWaitingTime());
	    ShortestJobFirst f2 =new ShortestJobFirst(processes1);
	    System.out.println(f2.calculateWaitingTime());*/
	}
	
	

}
