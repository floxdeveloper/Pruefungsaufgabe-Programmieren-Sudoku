package sudoku.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
/**
 * Diese Klasse verwaltet das Congratulations-Popup.
 */
public class CongratulationController {

	@FXML
	private Button ok;	
	private Stage stage;
	
	public void setStage(Stage stage){
		this.stage=stage;
	}
	
	/**
	 *  Schlieﬂt das Congratulations-Popup bei einem Knopfdruck auf 'OK'.
	 */
	@FXML
	private void handleOk(){
		stage.close();
	}
}
