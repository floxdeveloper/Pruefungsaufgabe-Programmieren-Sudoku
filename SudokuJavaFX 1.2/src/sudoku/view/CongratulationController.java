package sudoku.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CongratulationController {

	@FXML
	private Button ok;
	
	private Stage stage;
	
	public void setStage(Stage stage){
		this.stage=stage;
	}
	
	
	@FXML
	private void handleOk(){
		stage.close();
	}
	
}
