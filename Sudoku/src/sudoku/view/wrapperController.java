package sudoku.view;

import javafx.fxml.FXML;
import sudoku.MainApp;

public class wrapperController {

	private MainApp mainApp;

	@FXML
	private void initialize() {

	}

	// Beim Klick auf File ->
	@FXML
	private void handleTest() {

		System.out.println("test");

	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

	}

}
