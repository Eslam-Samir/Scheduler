package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import custom.views.GanttChart.ExtraData;
import custom.views.GanttChart;
import extras.Utility;
import scheduler.Process;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
	private Button add;
	
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
	
	private int numberOfProcesses;
	private String SchedulerType;
	private ObservableList<Process> list = FXCollections.observableArrayList();
	
	public void addProcess(ActionEvent action)
	{
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
		else if((this.SchedulerType.equals("Priority Scheduling (Preemptive)") 
				|| this.SchedulerType.equals("Priority Scheduling (Non-Preemptive)")) && priority.getText().isEmpty())
		{
			Utility.createAlert("Please Enter All Fields");
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
			Process process = new Process(processName.getText(), 
					Integer.valueOf(burst.getText()), Integer.valueOf(arrivalTime.getText()));
			list.add(process);
			table.setItems(list);
			if(list.size() == numberOfProcesses)
			{
				grid.setDisable(true);
			}
		}
		processName.clear();
		arrivalTime.clear();
		burst.clear();
		priority.clear();
	}
	
	public void startScheduling(ActionEvent action)
	{
		if(list.size() < this.numberOfProcesses)
		{
			Utility.createAlert("You have not entered all processes");
			return;
		}
		Stage stage = new Stage();
		stage.setTitle("Gantt Chart");

        final NumberAxis xAxis = new NumberAxis();
        final CategoryAxis yAxis = new CategoryAxis();

        //TODO style this
        VBox root = new VBox();
        Label label = new Label("average waiting time:  ");
      
        root.getChildren().add(label);
        
        final GanttChart<Number,String> chart = new GanttChart<Number,String>(xAxis,yAxis);
        
        xAxis.setLabel("");
        xAxis.setTickLabelFill(Color.CHOCOLATE);
        xAxis.setMinorTickCount(4);

        yAxis.setLabel("");
        yAxis.setTickLabelFill(Color.CHOCOLATE);
        yAxis.setTickLabelGap(10);

        chart.setTitle(this.SchedulerType);
        chart.setLegendVisible(false);
        chart.setBlockHeight(20);

        while(!list.isEmpty())
        {
        	XYChart.Series series = new XYChart.Series();
        	Process process = list.get(0);
        	int i = 0;
        	while(i < list.size())
        	{
        		Process x = list.get(i);
        		if(x.getName().equals("idle"))
        		{
        			series.getData().add(new XYChart.Data(x.getArrivalTime()
	        				, x.getName(), new ExtraData(x.getRunTime(), "status-red")));
	        		list.remove(x);
        		}
        		else if(x.getName().equals(process.getName()))
        		{
	        		series.getData().add(new XYChart.Data(x.getArrivalTime()
	        				, x.getName(), new ExtraData(x.getRunTime(), "status-green")));
	        		list.remove(x);
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
        Scene scene  = new Scene(root,620,350);
        
        stage.setScene(scene);
        stage.show();
        
        grid.setDisable(false);
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
		type.setText(type.getText() + schedulingType);
		
	}
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nameColumn.setCellValueFactory(new PropertyValueFactory<Process, String>("name"));
		arrivalColumn.setCellValueFactory(new PropertyValueFactory<Process, Integer>("arrivalTime"));
		burstColumn.setCellValueFactory(new PropertyValueFactory<Process, Integer>("runTime"));
		priorityColumn.setCellValueFactory(new PropertyValueFactory<Process, Integer>("priority"));
	}
}
