import java.util.LinkedList;




public class Mymain {

	public static void main(String[] args) {
		
	    Process p1 = new Process("P1",20,9);
	    Process p2=new Process("P2",7,13);
	    Process p3=new Process("P3",5,2);
	    Process p4=new Process("P4",3,9);
	    Process p5=new Process("P5",10,8);
	    Process p6=new Process("P6",15,9);
	    LinkedList<Process>processes1=new LinkedList<Process>();
	    processes1.add(p1);
	    processes1.add(p2);
	    processes1.add(p3);
	    processes1.add(p4);
	    processes1.add(p5);
	    processes1.add(p6);
	    FirstComeFirstServed f1=new FirstComeFirstServed(processes1);
	    System.out.println(f1.calculateWaitingTime());
	    ShortestJobFirst f2 =new ShortestJobFirst(processes1, SchedulerType.preemptive);
	    System.out.println(f2.calculateWaitingTime());
	}
	
	

}
