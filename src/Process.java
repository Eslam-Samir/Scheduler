
public class Process {
	private String name;
	private int runTime;
	private int arrivalTime;
	private int startTime;
	private int priority;
	
	
	Process()
	{
		this.name= " ";
		this.runTime=0;
		this.arrivalTime=0;
		this.startTime=0;
		this.priority = 0;
		
	}
	
	Process (String name , int runTime,int arrivalTime, int priority)
	{
		this.name= name;
		this.runTime=runTime;
		this.arrivalTime=arrivalTime;
		this.priority = priority;
		
	}
	public int getstartTime() {
		return startTime;
	}
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	public int getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRunTime() {
		return runTime;
	}
	public void setRunTime(int runTime) {
		this.runTime = runTime;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	

}
