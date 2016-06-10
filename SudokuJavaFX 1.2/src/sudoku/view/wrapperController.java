package sudoku.view;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sudoku.MainApp;
import sudoku.model.Sudoku;
import sudoku.model.SudokuGenerator;

public class wrapperController {

	private MainApp mainApp;

	@FXML
	private void initialize() {

	}
	
	
	//Beim Klick auf Sudoku -> Generate solvable Sudoku
	@FXML
	private void handleGenerate(){
//TODO Abfrage nach generate Zahl / Thread	(William)	
		
		
		//Um alle Textfelder auf schwarz zu setzen
		mainApp.getSudokuController().colorAllBlack();
		
		
		Sudoku genSudoku = SudokuGenerator.generate(25);
		mainApp.setSudoku(genSudoku);
		
		
		
		
		
		
	}

	// Beim Klick auf Sudoku -> Save
	@FXML
	private void handleSave() {

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
				
				//Alle Textfelder auf schwarz setzen (falls vorher etwas blau gefärbt war)
				mainApp.getSudokuController().colorAllBlack();
				
				if(mainApp.getSudoku().filled())
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
	public void handleAbout(){
		
		try {
	        // Load the fxml file and create a new stage for the popup dialog.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("view/about.fxml"));
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
		
		
		
		
		
	

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

	}

}
