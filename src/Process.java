
public class Process {
	private String name;
	private int runTime;
	private int arrivalTime;
	private int startedTime;
	private int finishTime;
	
	
	Process(){
		this.name= " ";
		this.runTime=0;
		this.arrivalTime=0;
		this.startedTime=0;
		this.finishTime=0;
	}
	
	Process (String name , int runTime,int arrivalTime){
		this.name= name;
		this.runTime=runTime;
		this.arrivalTime=arrivalTime;
		this.startedTime=0;
		this.finishTime=0;
	}
	public int getStartedTime() {
		return startedTime;
	}

	public void setStartedTime(int startedTime) {
		this.startedTime = startedTime;
	}

	public int getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(int finishTime) {
		this.finishTime = finishTime;
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
	

}
