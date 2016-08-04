package sudoku.view;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import sudoku.MainApp;

/**
 * Diese Klasse verwaltet das Popup, welches sich beim Generieren eines Sudokus öffnet.
 * Um die gewünschte Anzahl an vorgegebenen Zahlen an den Wrapper zu schicken, wird auf
 * einen PropertyChange zurückgegriffen.
 * 
 * @author Tobias Berner, Yvette Labastille, William Riyadi, Florian Stöckl
 */
public class InputNumberPopupController {

	private MainApp mainApp;
	private PropertyChangeSupport support = new PropertyChangeSupport(this);
	private Stage stage;
	@FXML
	private ComboBox<Integer> eingabecb;

	/**
	 * Setzt die Stage.
	 * 
	 * @param stage - zu setzende Stage
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Fügt ins Dropdownmenü die passenden Verknüpfungen mit den auswählbaren Zahlen zwischen 20 und 80 ein.
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
	 * Fügt PropertyChangeListener zu PropertyChangeSupport hinzu.
	 * 
	 * @param p - PropertyChangeListener
	 */
	public void addListener(PropertyChangeListener p) {
		support.addPropertyChangeListener(p);
	}

	/**
	 * Überprüft die eingegebene Zahl an bereits gefüllten Feldern des zu generierenden Sudokus.
	 * Wirft eine Fehlermeldung aus, falls keine Zahl gewählt wurde.
	 */
	@FXML
	private void handleAbsenden() {
		try {
			support.firePropertyChange("InputNumber", 0, (int) eingabecb.getValue());
			stage.close();
		} catch	(NullPointerException noNumberOfHintsSelectedException) {
			mainApp.warning("Please select a number", "You have not selected a number of hints");
		}
	}

	/**
	 * Setzt die MainApp-Referenz.
	 * 
	 * @param m - MainApp
	 */
	public void setMainApp(MainApp m) {
		mainApp = m;
	}

	/**
	 * Schließt Fenster.
	 */
	@FXML
	private void handleAbbrechen() {
		stage.close();
	}
}