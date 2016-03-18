
public class Process {
	private String name;
	private double runTime;
	private double arrivalTime;
	private double startTime;
	
	
	Process(){
		this.name= " ";
		this.runTime=0;
		this.arrivalTime=0;
		this.startTime=0;
	}
	
	Process (String name, double runTime, double arrivalTime){
		this.name= name;
		this.runTime=runTime;
		this.arrivalTime=arrivalTime;	
	}

	public double getStartTime() {
		return startTime;
	}
	public void setStartTime(double startTime) {
		this.startTime = startTime;
	}
	public double getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(double arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getRunTime() {
		return runTime;
	}
	public void setRunTime(double runTime) {
		this.runTime = runTime;
	}
	

}
