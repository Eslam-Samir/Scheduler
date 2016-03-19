package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import custom.views.GanttChart.ExtraData;
import custom.views.GanttChart;
import extras.Constants;
import extras.Utility;
import scheduler.FirstComeFirstServed;
import scheduler.Process;
import scheduler.RoundRobin;
import scheduler.Scheduler;
import scheduler.SchedulerType;
import scheduler.ShortestJobFirst;
import scheduler.prioritySchedule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ProcessSceneController implements Initializable {	
	@FXML
	private GridPane grid;
	@FXML
	private GridPane quantumGrid;
	@FXML
	private Label type;
	@FXML
	private Label number;
	@FXML
	private TextField processName;
	@FXML
	private TextField arrivalTime;
	@FXML
	private TextField burst;
	@FXML
	private TextField priority;
	@FXML
	private TextField quantum;
	@FXML
	private Button add;
	
	@FXML
	private TableView<Process> table;
	@FXML
	private TableColumn<Process, String> nameColumn;
	@FXML
	private TableColumn<Process, Double> arrivalColumn;
	@FXML
	private TableColumn<Process, Double> burstColumn;
	@FXML
	private TableColumn<Process, Integer> priorityColumn;
	
	private int numberOfProcesses;
	private String schedulerType;
	private ObservableList<Process> list = FXCollections.observableArrayList();
	
	private int pid = 1;
	
	public void addProcess(ActionEvent action)
	{
		if((this.schedulerType.equals(Constants.PSP) || this.schedulerType.equals(Constants.PSNP)))
		{
			if(priority.getText().isEmpty())
			{
				Utility.createAlert("Please Enter All Fields");
				return;
			}
			else if(priority.getText().equals("."))
			{
				Utility.createAlert("Wrong Input");
				return;
			}
		}
		
		if(list.size() >= numberOfProcesses) //TODO check if this is required
		{
			Utility.createAlert("You Can't Add More Processes",
					"Go back and change number of processes if you want to add more.");
		}
		else if(processName.getText().trim().isEmpty() || arrivalTime.getText().isEmpty()
				||burst.getText().isEmpty())
		{
			Utility.createAlert("Please Enter All Fields");
		}
		else if(arrivalTime.getText().equals(".") || burst.getText().equals("."))
		{
			Utility.createAlert("Wrong Input");
		}
		else
		{
			for(Process x: list)
			{
				if(x.getName().equals(processName.getText()))
				{
					Utility.createAlert("Process with the same name already exits");
					return;
				}
			}
			
			Process process;
			if((this.schedulerType.equals(Constants.PSP) || this.schedulerType.equals(Constants.PSNP)))
			{
				process = new Process(pid, processName.getText(), Double.valueOf(burst.getText()),
						Double.valueOf(arrivalTime.getText()), Integer.valueOf(priority.getText()));
			}
			else
			{
				process = new Process(pid, processName.getText(),Double.valueOf(burst.getText()), 
						Double.valueOf(arrivalTime.getText()));
			}
			pid++;
			list.add(process);
			table.setItems(list);
			if(list.size() == numberOfProcesses)
			{
				grid.setDisable(true);
			}
			processName.clear();
			arrivalTime.clear();
			burst.clear();
			priority.clear();
		}
	}
	
	public void startScheduling(ActionEvent action)
	{
		if(list.size() < this.numberOfProcesses)
		{
			Utility.createAlert("You have not entered all processes");
			return;
		}
		Scheduler scheduler;
		LinkedList<Process> inputs = new LinkedList<>();
		LinkedList<Process> outputs;
		int pid = 1;
		for(int i = 0; i < numberOfProcesses; i++)
		{
			if((this.schedulerType.equals(Constants.PSP) || this.schedulerType.equals(Constants.PSNP)))
			{
				Process process = new Process(pid, list.get(i).getName(), 
						list.get(i).getRunTime(), list.get(i).getArrivalTime()
						, list.get(i).getPriority());
				inputs.add(process);
			}
			else
			{
				Process process = new Process(pid, list.get(i).getName(), 
						list.get(i).getRunTime(), list.get(i).getArrivalTime());
				inputs.add(process);
			}
			pid++;
		}
		
		if(schedulerType.equals(Constants.FCFS))
		{
			scheduler = new FirstComeFirstServed(inputs);
		}
		else if(schedulerType.equals(Constants.SJFP))
		{
			scheduler = new ShortestJobFirst(inputs, SchedulerType.preemptive);
		}
		else if(schedulerType.equals(Constants.SJFNP))
		{
			scheduler = new ShortestJobFirst(inputs, SchedulerType.non_preemptive);
		}
		else if(schedulerType.equals(Constants.PSP))
		{
			scheduler = new prioritySchedule(inputs, SchedulerType.preemptive);
		}
		else if(schedulerType.equals(Constants.PSNP))
		{
			scheduler = new prioritySchedule(inputs, SchedulerType.non_preemptive);
		}
		else if(schedulerType.equals(Constants.RR))
		{
			if(quantum.getText().isEmpty())
			{
				Utility.createAlert("Please enter the time quantum");
				return;
			}
			else if(quantum.getText().contains("."))
			{
				Utility.createAlert("Wrong Input");
				return;
			}
			else
			{
				scheduler = new RoundRobin(inputs, Double.valueOf(quantum.getText())); 
			}
		}
		else
		{
			return;
		}
		outputs = scheduler.run();
		double avg = scheduler.calculateWaitingTime();
		
		Stage stage = new Stage();
		stage.setTitle("Gantt Chart");

        final NumberAxis xAxis = new NumberAxis();
        final CategoryAxis yAxis = new CategoryAxis();

        //TODO style this
        VBox root = new VBox();
        Label label = new Label("average waiting time:  " + String.valueOf(avg));
      
        root.getChildren().add(label);
        
        final GanttChart<Number,String> chart = new GanttChart<Number,String>(xAxis,yAxis);
        
        Insets inset = new Insets(5, 25, 5, 0);
        chart.setPadding(inset);
        xAxis.setLabel("Time");
        xAxis.setTickLabelFill(Color.BLACK);
        xAxis.setMinorTickCount(10);

        yAxis.setLabel("Process Name");
        yAxis.setTickLabelFill(Color.BLACK);
        yAxis.setTickLabelGap(10);

        chart.setTitle(this.schedulerType);
        chart.setLegendVisible(false);
        chart.setBlockHeight(30);

        while(!outputs.isEmpty())
        {
        	XYChart.Series series = new XYChart.Series();
        	Process process = outputs.get(0);
        	int i = 0;
        	while(i < outputs.size())
        	{
        		Process x = outputs.get(i);
        		if(x.getPid() == 0)
        		{
        			series.getData().add(new XYChart.Data(x.getStartTime()
	        				, x.getName(), new ExtraData(x.getRunTime(), "status-red")));
        			outputs.remove(x);
        		}
        		else if(x.getPid() == process.getPid())
        		{
	        		series.getData().add(new XYChart.Data(x.getStartTime()
	        				, x.getName(), new ExtraData(x.getRunTime(), "status-green")));
	        		outputs.remove(x);
        		}
        		else
        		{
        			i++;
        		}
        	}
        	chart.getData().add(series);
        }   

        root.getStylesheets().add(getClass().getResource("/resources/ganttchart.css").toExternalForm());
        
        root.getChildren().add(chart);
        Scene scene  = new Scene(root,800,500);
        
        stage.setScene(scene);
        stage.show();
	}
	
	public void goBack(ActionEvent action) throws IOException
	{
		Stage stage=(Stage) grid.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Main.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/resources/application.css").toExternalForm());	
		stage.setScene(scene);
		stage.show();		
	}
	
	public void keyPressed(KeyEvent event)
	{
		if (event.getCode().equals( KeyCode.DELETE ) )
	    {
			list.remove(table.getSelectionModel().getSelectedIndex());
			table.setItems(list);
			if(grid.isDisabled())
			{
				grid.setDisable(false);
			}
	    }
	}
	
	
	public int getNumberOfProcesses() {
		return numberOfProcesses;
	}
	public void setNumberOfProcesses(int numberOfProcesses) {
		this.numberOfProcesses = numberOfProcesses;		
		number.setText(number.getText() + String.valueOf(numberOfProcesses));
	}
	public String getSchedulingType() {
		return schedulerType;
	}
	public void setSchedulerType(String schedulingType) {
		this.schedulerType = schedulingType;
		
		if(!this.schedulerType.equals(Constants.PSP) && !this.schedulerType.equals(Constants.PSNP))
		{
			table.getColumns().remove(3);
			grid.getChildren().remove(6);
			grid.getChildren().remove(6);
		}
		if(!this.schedulerType.equals(Constants.RR))
		{
			quantumGrid.getChildren().remove(0);
			quantumGrid.getChildren().remove(0);
		}
		type.setText(type.getText() + schedulingType);
	}
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nameColumn.setCellValueFactory(new PropertyValueFactory<Process, String>("name"));
		arrivalColumn.setCellValueFactory(new PropertyValueFactory<Process, Double>("arrivalTime"));
		burstColumn.setCellValueFactory(new PropertyValueFactory<Process, Double>("runTime"));
		priorityColumn.setCellValueFactory(new PropertyValueFactory<Process, Integer>("priority"));
	}
}
