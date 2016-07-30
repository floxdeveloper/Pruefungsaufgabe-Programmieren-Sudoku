package sudoku.view;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import sudoku.MainApp;

public class InputNumberPopupController {

	private MainApp mainApp;
	private PropertyChangeSupport support = new PropertyChangeSupport(this);

	@FXML
	private ComboBox<Integer> eingabecb;

	// TODO auf Englisch ‰ndern
	/**
	 * 
	 */
	@FXML
	private void initialize() {

		// Sets Item of ComboBox
		ObservableList<Integer> a1 = FXCollections.observableArrayList();
		for (int i = 20; i < 81; i++) {
			a1.add(i);
		}
		eingabecb.setVisibleRowCount(10);
		eingabecb.setItems(a1);

	}

	/**
	 * F¸gt PropertyChangeListener zu ProbertyChangeSupport hinzu
	 * 
	 * @param p
	 */
	public void addListener(PropertyChangeListener p) {
		support.addPropertyChangeListener(p);
	}

	private Stage stage;

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@FXML
	private void handleAbsenden() {
		// TODO Bei nicht ausgew‰hlter Zahl -> Exception (fixen)
		try {
			support.firePropertyChange("InputNumber", 0, (int) eingabecb.getValue());
			stage.close();

		} catch	(NullPointerException noNumberOfHintsSelectedException) {

			mainApp.warning("Please select a number", "You have not selected a number of hints");

		}

	}

	/**
	 * Setzt MainApp Referenz
	 * 
	 * @param m
	 */
	public void setMainApp(MainApp m) {
		mainApp = m;
	}

	/**
	 * Schlieﬂt Fenster 
	 */
	@FXML
	private void handleAbbrechen() {
		stage.close();
	}

}