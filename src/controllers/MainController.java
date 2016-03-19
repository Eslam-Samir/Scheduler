package controllers;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import custom.views.NumberTextField;
import extras.Constants;
import extras.Utility;
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
			Constants.FCFS,
			Constants.SJFP,
			Constants.SJFNP,
			Constants.PSP,
			Constants.PSNP,
			Constants.RR
            );
	
	
	public void Next(ActionEvent action) throws IOException
	{
		String processesCount = numberOfProcesses.getText();
		String schedulerType = type.getValue();
		if(processesCount.isEmpty())
		{
			Utility.createAlert("Enter Number of Processes");
		}
		else if(schedulerType == null)
		{
			Utility.createAlert("Choose Scheduler Type");
		}
		else if(processesCount.contains(".") || Integer.valueOf(processesCount) == 0)
		{
			Utility.createAlert("Wrong Input");
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
