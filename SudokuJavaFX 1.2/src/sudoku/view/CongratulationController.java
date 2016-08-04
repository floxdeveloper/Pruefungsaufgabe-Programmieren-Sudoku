package sudoku.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
/**
 * Diese Klasse verwaltet das Congratulations-Popup.
 * 
 * @author Tobias Berner, Yvette Labastille, William Riyadi, Florian St�ckl
 */
public class CongratulationController {

	@FXML
	private Button ok;	
	private Stage stage;
	
	public void setStage(Stage stage){
		this.stage=stage;
	}
	
	/**
	 *  Schlie�t das Congratulations-Popup bei einem Knopfdruck auf 'OK'.
	 */
	@FXML
	private void handleOk(){
		stage.close();
	}
}
