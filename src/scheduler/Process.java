package scheduler;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Process {
	private SimpleStringProperty name;
	private SimpleDoubleProperty runTime;
	private SimpleDoubleProperty arrivalTime;
	private SimpleDoubleProperty startTime;
	private SimpleIntegerProperty priority;
	
	public Process (String name , double runTime, double arrivalTime){
		this.name= new SimpleStringProperty(name);
		this.runTime= new SimpleDoubleProperty(runTime);
		this.arrivalTime= new SimpleDoubleProperty(arrivalTime);
	}
	
	Process (String name , double runTime, double arrivalTime, int priority)
	{
		this.name= new SimpleStringProperty(name);
		this.runTime= new SimpleDoubleProperty(runTime);
		this.arrivalTime= new SimpleDoubleProperty(arrivalTime);
		this.priority = new SimpleIntegerProperty(priority);
	}
	
	public String getName() {
		return name.get();
	}
	public void setName(String name) {
		this.name = new SimpleStringProperty(name);
	}
	
	public double getRunTime() {
		return runTime.get();
	}
	public void setRunTime(double runTime) {
		this.runTime = new SimpleDoubleProperty(runTime);
	}
	
	public double getArrivalTime() {
		return arrivalTime.get();
	}
	public void setArrivalTime(double arrivalTime) {
		this.arrivalTime = new SimpleDoubleProperty(arrivalTime);
	}
	
	public double getStartTime() {
		return startTime.get();
	}
	public void setStartTime(double startTime) {
		this.startTime = new SimpleDoubleProperty(startTime);
	}
	
	public int getPriority() {
		return priority.get();
	}
	public void setPriority(int priority) {
		this.priority = new SimpleIntegerProperty(priority);
	}
}
