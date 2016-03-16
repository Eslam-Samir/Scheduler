package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import scheduler.Process;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;


public class ProcessSceneController implements Initializable {
	private int numberOfProcesses;
	private String SchedulerType;
	
	@FXML
	private GridPane grid;
	
	@FXML
	private TextField processName;
	
	@FXML
	private TextField arrivalTime;
	
	@FXML
	private TextField burst;
	
	@FXML
	private TextField priority;
	
	@FXML
	private TableView<Process> table;
	@FXML
	private TableColumn<Process, String> nameColumn;
	@FXML
	private TableColumn<Process, Integer> arrivalColumn;
	@FXML
	private TableColumn<Process, Integer> burstColumn;
	@FXML
	private TableColumn<Process, Integer> priorityColumn;
	
	private ObservableList<Process> list = FXCollections.observableArrayList();
	
	public void addProcess(ActionEvent action)
	{
		if(processName.getText().trim().isEmpty() || arrivalTime.getText().isEmpty()
				||burst.getText().isEmpty())
		{
			System.out.print("Please Enter All Fields\n");
		}
		else if((this.SchedulerType.equals("Priority Scheduling (Preemptive)") 
				|| this.SchedulerType.equals("Priority Scheduling (Non-Preemptive)")) && priority.getText().isEmpty())
		{
			System.out.print("Please Enter All Fields\n");
		}
		else
		{
			Process process = new Process(processName.getText(), 
					Integer.valueOf(burst.getText()), Integer.valueOf(arrivalTime.getText()));
			list.add(process);
			table.setItems(list);
		}
	}
	
	
	public int getNumberOfProcesses() {
		return numberOfProcesses;
	}
	public void setNumberOfProcesses(int numberOfProcesses) {
		this.numberOfProcesses = numberOfProcesses;
		
		
	}
	public String getSchedulingType() {
		return SchedulerType;
	}
	public void setSchedulerType(String schedulingType) {
		this.SchedulerType = schedulingType;
		
		if(!this.SchedulerType.equals("Priority Scheduling (Preemptive)") 
				&& !this.SchedulerType.equals("Priority Scheduling (Non-Preemptive)"))
		{
			table.getColumns().remove(3);
			grid.getChildren().remove(6);
			grid.getChildren().remove(6);
		}
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nameColumn.setCellValueFactory(new PropertyValueFactory<Process, String>("name"));
		arrivalColumn.setCellValueFactory(new PropertyValueFactory<Process, Integer>("arrivalTime"));
		burstColumn.setCellValueFactory(new PropertyValueFactory<Process, Integer>("runTime"));
		priorityColumn.setCellValueFactory(new PropertyValueFactory<Process, Integer>(""));
	}
}
