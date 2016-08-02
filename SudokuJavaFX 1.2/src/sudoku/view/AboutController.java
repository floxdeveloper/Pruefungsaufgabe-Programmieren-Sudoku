package sudoku.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AboutController {
	
	@FXML 
	Button closeButton;
	
	/**
	 * Schlieﬂt die aktuelle Stage
	 */
	@FXML
	public void handleClose(ActionEvent event) {
	    Stage stage = (Stage) closeButton.getScene().getWindow();
	    stage.close();
	}
}
