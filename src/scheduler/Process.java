package scheduler;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Process {
	private SimpleStringProperty name;
	private SimpleIntegerProperty runTime;
	private SimpleIntegerProperty arrivalTime;
	private SimpleIntegerProperty priority;
	
	public int getArrivalTime() {
		return arrivalTime.get();
	}
	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = new SimpleIntegerProperty(arrivalTime);
	}
	public Process (String name , int runTime, int arrivalTime){
		this.name= new SimpleStringProperty(name);
		this.runTime= new SimpleIntegerProperty(runTime);
		this.arrivalTime= new SimpleIntegerProperty(arrivalTime);
	}
	public String getName() {
		return name.get();
	}
	public void setName(String name) {
		this.name = new SimpleStringProperty(name);
	}
	public int getRunTime() {
		return runTime.get();
	}
	public void setRunTime(int runTime) {
		this.runTime = new SimpleIntegerProperty(runTime);
	}
	
	public int getPriority() {
		return priority.get();
	}
	public void setPriority(int priority) {
		this.priority = new SimpleIntegerProperty(priority);
	}
}
