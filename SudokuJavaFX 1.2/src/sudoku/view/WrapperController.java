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
import sudoku.MainAppTest;
import sudoku.model.Sudoku;
import sudoku.model.SudokuGenerator;

public class WrapperController implements PropertyChangeListener {

	private MainApp mainApp;

	@FXML
	private void initialize() {

	}

	// Beim Klick auf Sudoku -> Generate solvable Sudoku
	@FXML
	private void handleGenerate() {
		// TODO Abfrage nach generate Zahl / Thread (William)

		try {
			FXMLLoader loader = new FXMLLoader();
			// loader.setLocation(new URL("view/InputNumberPopup.fxml"));
			loader.setLocation(new File("src/sudoku/view/InputNumberPopup.fxml").toURI().toURL());
			AnchorPane pane = loader.load();

			Scene scene = new Scene(pane);
			Stage stage = new Stage();
			
			// Set the application icon.
			stage.getIcons().add(new Image("file:resources/images/sudoku.png"));
			
			
			//Eingabe auf Main Fenster nicht mehr zulassen
			stage.initOwner(mainApp.getPrimaryStage());
			stage.initModality(Modality.WINDOW_MODAL);
		 
			stage.setResizable(false);
			stage.setScene(scene);

			InputNumberPopupController iController = loader.getController();
			iController.addListener(this);
			iController.setStage(stage);
			iController.setMainApp(mainApp);
			
			//Wird manuell geschlossen
			stage.show();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Beim Klick auf Sudoku -> Save
	@FXML
	private void handleSave() {
		
		//Man darf keine leeren sudokus speichern
		if (mainApp.getSudoku().empty()) {
			mainApp.warning("Unable to save", "You can not save an empty sudoku.");
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

				mainApp.error("Unable to save", "An error has occurred while saving");

			}

		} else {

			mainApp.warning("Unable to save", "Please select a file");

		}

	}

	// Beim Klick auf Sudoku -> Load
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

				// Alle Textfelder auf schwarz setzen (falls vorher etwas blau
				// gefärbt war)
				mainApp.getSudokuController().colorAllBlack();

				if (mainApp.getSudoku().filled())
					mainApp.getSudokuController().setEditable(false);
				else
					mainApp.getSudokuController().setEditable(true);

			} catch (Exception e) {

				mainApp.error("Unable to load",
						"Please select a valid Sudoku file. This App only supports Files that are saved by itself or other instances.");

			}

		} else {

			mainApp.warning("Unable to load", "Please select a file");

		}

	}

	@FXML
	public void handleAbout() {

		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainAppTest.class.getResource("view/about.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.initStyle(StageStyle.UTILITY);
			dialogStage.initOwner(mainApp.getPrimaryStage());
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void handleCheck(){
		if (mainApp.getSudoku().checkUniqueSolvable())
			System.out.println("Uniquely solvable");
		else 
			System.out.println("Not uniquely solvable");
		
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

	}

	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("InputNumber")) {
			int numberOfClues = (int) evt.getNewValue();
			// Um alle Textfelder auf schwarz zu setzen
			mainApp.getSudokuController().colorAllBlack();
			
			mainApp.getSudokuController().setEditable(true);
			
			Thread t = new Thread() {
				public void run() {
					System.out.println("Generating Sudoku...");
					Sudoku genSudoku = SudokuGenerator.generate(numberOfClues);
					System.out.println("Finished generating Sudoku.");
					mainApp.setSudoku(genSudoku);
				}
			};
			t.start();
		}

	}

}
