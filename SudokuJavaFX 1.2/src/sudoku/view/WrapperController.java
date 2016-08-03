package sudoku.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sudoku.MainApp;
import sudoku.model.Solvability;
import sudoku.model.Sudoku;
import sudoku.model.SudokuGenerator;

/**
 * Diese Klasse verwaltet alle Interaktionen des Benutzers mit den Schaltflächen
 * um das Sudokufeld herum. Sie führt zudem das Speichern und Laden eines Sudokus aus.
 *
 */
public class WrapperController implements PropertyChangeListener {

	private MainApp mainApp;

	/**
	 * Wird von der Main-Applikation aufgerufen. 
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	/**
	 * Setzt die nicht gesperrten Felder zurück.
	 */
	@FXML
	private void handleResetNotLocked() {
		mainApp.getSudokuController().resetNotLocked();
	}

	/**
	 * Öffnet das InputNumberPopup, damit die Einstellungen für das zu generierende 
	 * Sudoku getätigt werden können.
	 */
	@FXML
	private void handleGenerate() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/InputNumberPopup.fxml"));
			AnchorPane pane = loader.load();

			Scene scene = new Scene(pane);
			Stage stage = new Stage();
			
			// Setzt das Icon für das Popup.
			stage.getIcons().add(new Image("file:resources/images/sudoku.png"));
			
			//Lässt Eingabe auf das Main-Fenster nicht mehr zu.
			stage.initOwner(mainApp.getPrimaryStage());
			stage.initModality(Modality.WINDOW_MODAL);
		 
			stage.setResizable(false);
			stage.setScene(scene);

			InputNumberPopupController iController = loader.getController();
			iController.addListener(this);
			iController.setStage(stage);
			iController.setMainApp(mainApp);
			
			//Wird manuell geschlossen.
			stage.show();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Löst das Zurücksetzen der Editierbarkeit über die MainApp aus.
	 */	
	@FXML
	private void handleResetEditability(){
		mainApp.getSudokuController().resetEditability();
	}
	
	/**
	 * Löst die Sperrung der bereits eingegebenen Zahlen über die MainApp aus.
	 */
	@FXML
	private void handleLockEntered(){
		mainApp.getSudokuController().lockEnteredFields();
	}

	/**
	 * Öffnet den Dateimanager des Betriebssystems, um das Sudoku als binäres Objekt zu speichern.
	 */
	@FXML
	private void handleSave() {
		//Man darf keine leeren Sudokus speichern.
		if (mainApp.getSudoku().isEmpty()) {
			mainApp.warning("Unable to save", "You cannot save an empty sudoku.");
			return;
		}
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Sudoku");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Sudokus", "*.sdk"));	
		File selectedFile = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
		
		if (selectedFile != null) {
			try {
				ObjectOutputStream os = new ObjectOutputStream(
						new BufferedOutputStream(new FileOutputStream(selectedFile)));
				os.writeObject(mainApp.getSudoku());
				os.close();
			} catch (Exception e) {
				mainApp.error("Unable to save", "An error has occurred while saving.");
			}
		}
	}

	/**
	 * Öffnet den Dateimanager des Betriebssystems, um das binäre Objekt eines gespeicherten Sudokus zu laden.
	 */
	@FXML
	private void handleLoad() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Load Sudoku");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Sudokus", "*.sdk"),
				new ExtensionFilter("All Files", "*.*"));
		File selectedFile = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

		if (selectedFile != null && selectedFile.exists()) {
			try {
				ObjectInputStream is = new ObjectInputStream(
						new BufferedInputStream(new FileInputStream(selectedFile)));
				Sudoku sudoku = (Sudoku) is.readObject();
				mainApp.setSudoku(sudoku);
				is.close();

				// Setzt alle Textfelder auf schwarz (falls vorher etwas blau
				// gefärbt war).
				mainApp.getSudokuController().resetEditability();

				if (mainApp.getSudoku().isFilled())
					mainApp.getSudokuController().setEditable(false);	
			} catch (Exception e) {
				mainApp.error("Unable to load",
						"Please select a valid Sudoku file. This App only supports Files that are saved by itself or other instances.");
			}
		} 
	}

	/**
	 * Zeigt einen Dialog mit Informationen über die Verfasser und die Entstehung der Anwendung an.
	 */
	@FXML
	public void handleAbout() {
		try {
			// Lädt fxml-Datei und erzeugt eine neue stage für das Popup.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/About.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Erzeugt die dialogStage und stellt sie in den Vordergrund.
			Stage dialogStage = new Stage();
			dialogStage.initStyle(StageStyle.UTILITY);
			dialogStage.initOwner(mainApp.getPrimaryStage());
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Zeigt die dialogStage an, bis der Benutzer sie schließt.
			dialogStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Löst die Überprüfung der Lösbarkeit des eingegeben Sudokus über die MainApp aus.
	 */
	@FXML
	private void handleCheck(){
		Solvability ergebnis = mainApp.getSudoku().checkUniqueSolvable();
		
		if (ergebnis == Solvability.notSolvable)
			mainApp.information("Not uniquely solvable", "The entered Sudoku is definitely not solvable.");
		else if (ergebnis == Solvability.probablyNotSolvable)
			mainApp.information("Not uniquely solvable", "The entered Sudoku is very unlikely solvable. We have stopped trying.");
		else if (ergebnis == Solvability.uniquelySolvable)			
			mainApp.information("Uniquely solvable", "The Sudoku you have entered is uniquely solvable.");
		else if (ergebnis == Solvability.notUniquelySolvable)
			mainApp.information("Not uniquely solvable", "The Sudoku you have entered has two or more valid solutions.");
		else
			mainApp.error("Unexpected Error", "Please try again");
	}

	/**
	 * Wird ausgelöst, sobald man die Anzahl der vorgegebenen Zahlen eingibt. 
	 * Führt generate() von SudokuGenerator aus und setzt das generierte Sudoku.
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("InputNumber")) {
			int numberOfClues = (int) evt.getNewValue();
			// Setzt alle Textfelder auf schwarz.
			mainApp.getSudokuController().colorAllBlack();			
			mainApp.getSudokuController().unselect();
			
			// Erstellt einen neuen Task, der das Sudoku in einem eigenen Thread generiert.
			Task<Void> task = new Task<Void>(){
				@Override
				protected Void call() throws Exception {
					Sudoku genSudoku = SudokuGenerator.generate(numberOfClues);
					mainApp.setSudoku(genSudoku);
					mainApp.getSudokuController().lockEnteredFields();
					return null;
				}
			};
			// Lockt den Screen, wenn der Thread läuft.
			task.setOnRunning(e -> {
				mainApp.lockScreen();
			});
			// Unlockt den Screen, wenn der Thread abgelaufen ist.
			task.setOnSucceeded(e -> {
				mainApp.unlockScreen();
			});
			task.setOnFailed(e -> {
				mainApp.unlockScreen();
			});
			new Thread(task).start();	
			}
	}
}
