package controllers;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import custom.views.NumberTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;


public class MainController implements Initializable {
	
	@FXML
	private NumberTextField numberOfProcesses;
	
	@FXML
	private ComboBox<String> type;
	
	
	private ObservableList<String> list = FXCollections.observableArrayList(
			"First Come First Served",
            "Shortest Job First (Preemptive)",
            "Shortest Job First (Non-Preemptive)",
            "Priority Scheduling (Preemptive)",
            "Priority Scheduling (Non-Preemptive)",
            "Round Robin"
            );
	
	
	public void Next(ActionEvent action) throws IOException
	{
		String processesCount = numberOfProcesses.getText();
		String schedulerType = type.getValue();
		if(processesCount.isEmpty() || Integer.valueOf(processesCount) == 0)
		{
			System.out.print("Enter Number of Processes\n");
		}
		else if(schedulerType == null)
		{
			System.out.print("Choose Scheduler Type\n");
		}
		else
		{
			goToNextScene(Integer.valueOf(processesCount), schedulerType);
		}
	}
	
	private void goToNextScene(int processesCount, String schedulerType) throws IOException
	{    
		Stage stage=(Stage) type.getScene().getWindow();
		
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/ProcessScene.fxml"));
        Parent root = loader.load();
        
        ProcessSceneController controller = loader.getController();
        controller.setNumberOfProcesses(processesCount);
        controller.setSchedulerType(schedulerType);
        
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		intializeComboBox();
	}
	
	public void intializeComboBox(){
		type.setItems(list);
	}

}
