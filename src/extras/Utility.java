package extras;
import java.util.LinkedList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import scheduler.Process;

public class Utility {
	
	// return zero if all processes have arrived
	public static double getNextArrivalTime(LinkedList<Process> processes, double timeLimit)
	{
		double nextArrival = 0;	
		
		for(Process process: processes)
		{
			if(process.getArrivalTime() <= timeLimit)
				continue;				
				
			if(nextArrival > process.getArrivalTime() || nextArrival == 0)
			{
				nextArrival = process.getArrivalTime();
			}	
		}
		return nextArrival;
	}
	
	public static int getFirstJobIndex(LinkedList<Process> processes)
	{
		int min = 0;
		for(Process process: processes)
		{
			if(process.getArrivalTime() < processes.get(min).getArrivalTime())
			{
				min = processes.indexOf(process);
			}		
		}
		return min;
	}
	
	public static int getPrevious(LinkedList<Process> processes, int index, String name)
	{
		if(index < 0)
			return -1;
		for(int i = index; i >= 0; i--)
		{
			if(processes.get(i).getName().equals(name))
			{
				return i;
			}		
		}
		return -1;
	}
	
	public static int getPriorityIndexLimited(LinkedList<Process> processes, double timeLimit)
	{
		int min = 0;
		boolean isProcessAvailable = false;
		for(Process process: processes)
		{
			if(process.getArrivalTime() >timeLimit)
				continue;
			else
				isProcessAvailable = true;
			
			if(processes.get(min).getArrivalTime() > timeLimit)
			{
				min = processes.indexOf(process);
			}
			else if(process.getPriority() < processes.get(min).getPriority())
			{
				min = processes.indexOf(process);
			}
			else if(process.getPriority() == processes.get(min).getPriority() 
					&& process.getArrivalTime() < processes.get(min).getArrivalTime())
			{
				min = processes.indexOf(process);
			}	
		}
		
		if(isProcessAvailable)
			return min;
		else
			return -1;
	}
	
	// return -1 if there is no process to schedule
	public static int getShortestJobIndexLimited(LinkedList<Process> processes, double timeLimit)
	{
		int min = 0;
		boolean isProcessAvailable = false;
		for(Process process: processes)
		{
			if(process.getArrivalTime() > timeLimit)
				continue;
			else
				isProcessAvailable = true;
			
			if(processes.get(min).getArrivalTime() > timeLimit)
			{
				min = processes.indexOf(process);
			}
			else if(process.getRunTime() < processes.get(min).getRunTime())
			{
				min = processes.indexOf(process);
			}
			else if(process.getRunTime() == processes.get(min).getRunTime() 
					&& process.getArrivalTime() < processes.get(min).getArrivalTime())
			{
					min = processes.indexOf(process);
				}	
			}
		
		if(isProcessAvailable)
			return min;
		else
			return -1;
	}
	
	public static void createAlert(String warnings)
	{
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Wrong input");
		alert.setHeaderText(warnings);
		alert.show();
	}
	
	public static void createAlert(String warnings, String content)
	{
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Wrong input");
		alert.setHeaderText(warnings);
		alert.setContentText(content);
		alert.show();
	}
}
