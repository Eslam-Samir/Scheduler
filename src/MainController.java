import java.net.URL;
import java.util.ResourceBundle;

import custom.views.NumberTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;


public class MainController implements Initializable {
	
	@FXML
	private NumberTextField numberOfProcesses;
	
	@FXML
	private ComboBox<String> type;
	
	private String SchedulerType;
	
	private ObservableList<String> list = FXCollections.observableArrayList(
			"First Come First Served",
            "Shortest Job First (Preemptive)",
            "Shortest Job First (Non-Preemptive)",
            "Priority Scheduling (Preemptive)",
            "Priority Scheduling (Non-Preemptive)",
            "Round Robin"
            );
	
	
	public void Next(ActionEvent action)
	{
		String ProcessesCount = numberOfProcesses.getText();
		SchedulerType = type.getValue();
		if(ProcessesCount.equals("") || Integer.valueOf(ProcessesCount) == 0)
		{
			System.out.print("Enter Number of Processes\n");
		}
		else if(SchedulerType == null)
		{
			System.out.print("Choose Scheduler Type\n");
		}
		else
		{
			
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		intializeComboBox();
	}
	
	public void intializeComboBox(){
		type.setItems(list);
	}

}
