package sudoku.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Diese Klasse verwaltet das Popup-Menu unter About.
 * 
 * @author Tobias Berner, Yvette Labastille, William Riyadi, Florian St�ckl
 */
public class AboutController {
	
	@FXML 
	Button closeButton;
	
	/**
	 * Schlie�t die aktuelle Stage.
	 * @param event - Event
	 */
	@FXML
	public void handleClose(ActionEvent event) {
	    Stage stage = (Stage) closeButton.getScene().getWindow();
	    stage.close();
	}
}
