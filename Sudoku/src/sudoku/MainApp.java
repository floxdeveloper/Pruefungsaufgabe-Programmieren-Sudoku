package sudoku;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import sudoku.model.Sudoku;
import sudoku.view.sudokuController;
import sudoku.view.wrapperController;

public class MainApp extends Application {


	private Stage primaryStage;
	private BorderPane rootLayout;
	private Sudoku sudoku;
	private sudokuController scontroller;
	
	

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("SudokuSolver");
		primaryStage.setResizable(false);

		// Set the application icon.
		this.primaryStage.getIcons().add(new Image("file:resources/images/sudoku.png"));

		int[][] sudokuFeld = new int[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				sudokuFeld[i][j] = 0;
			}
		}
		sudoku = new Sudoku(sudokuFeld);

		initRootLayout();
		initSudokuLayout();
		
		primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, scontroller.keyEventHandler);
	
	}

	// Lädt das Root Layout
	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/wrapper.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Set controller to Main Stage
			wrapperController controller = loader.getController();
			controller.setMainApp(this);

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Shows the sudoku solver inside the root layout.
	 */
	public void initSudokuLayout() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/sudoku.fxml"));
			AnchorPane sudoku = (AnchorPane) loader.load();

			// Set sudoku solver into the center of root layout.
			rootLayout.setCenter(sudoku);

			// Set controller to Main Stage
			sudokuController controller = loader.getController();
			controller.setMainApp(this);

			// Verweis auf Controller um beim set Sudoku die GUI neu zu laden
			scontroller = controller;

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Used to send warnings with custom header and content
	public void warning(String header, String content) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.initOwner(primaryStage);
		alert.setTitle("Warning");

		if (header.equals(""))
			header = "Unable to do that";

		alert.setHeaderText(header);
		alert.setContentText(content);

		alert.showAndWait();

	}

	// Used to send error messanges with custom header and content
	public void error(String header, String content) {

		Alert alert = new Alert(AlertType.ERROR);
		alert.initOwner(primaryStage);
		alert.setTitle("Error");

		if (header.equals(""))
			header = "An unexpected error has occured.";

		alert.setHeaderText(header);
		alert.setContentText(content);

		alert.showAndWait();

	}

	public sudokuController getSudokuController(){
		return scontroller;
	}

	public boolean setSudoku(int[][] array) {

		int horizontal = array.length;

		// Prüft prinzipielle Groesse
		if (horizontal != 9)
			return false;
		for (int i = 0; i < 9; i++) {
			if (array[i].length != 9)
				return false;
		}

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (array[i][j] > 9 || array[i][j] < 0)
					return false;
			}
		}

		boolean[] zahlVerfügbar = new boolean[9];

		// Prüft ob Zeilen (bis jetzt) nach Sudokuregeln gefüllt wurden
		for (int i = 0; i < 9; i++) {
			// macht alle Zahlen wieder verfügbar
			setBoolarrayAll(zahlVerfügbar, true);
			for (int j = 0; j < 9; j++) {
				int zahl = array[i][j];
				if (zahl != 0) {
					if (!zahlVerfügbar[zahl - 1])
						return false;
					else
						zahlVerfügbar[zahl - 1] = false;
				}
			}
		}

		// Prüft ob Spalten (bis jetzt) nach Sudokuregeln gefüllt wurden
		for (int i = 0; i < 9; i++) {
			// macht alle Zahlen wieder verfügbar
			setBoolarrayAll(zahlVerfügbar, true);
			for (int j = 0; j < 9; j++) {
				int zahl = array[j][i];
				if (zahl != 0) {
					if (!zahlVerfügbar[zahl - 1])
						return false;
					else
						zahlVerfügbar[zahl - 1] = false;
				}
			}
		}

		// Prüft ob die 9er- Kästchen (bis jetzt) nach Sudokuregeln gefüllt
		// wurden

		// Jede Anfangswerte fürs Kästchen
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				setBoolarrayAll(zahlVerfügbar, true);

				for (int a = i * 3; a < (i + 1) * 3; a++) {
					for (int b = j * 3; b < (j + 1) * 3; b++) {
						int zahl = array[a][b];
						if (zahl != 0) {
							if (!zahlVerfügbar[zahl - 1])
								return false;
							else
								zahlVerfügbar[zahl - 1] = false;

						}

					}

				}

			}
		}

		sudoku.setSudoku(array);
		scontroller.sudokuChanged();
		return true;

	}

	public boolean setSudoku(Sudoku s) {
		sudoku = s;
		scontroller.sudokuChanged();
		return true;

	}

	public boolean[] setBoolarrayAll(boolean[] boolArray, boolean wert) {
		for (int i = 0; i < boolArray.length; i++) {
			boolArray[i] = wert;

		}
		return boolArray;

	}

	public Sudoku getSudoku() {
		return sudoku;
	}

	public static void main(String[] args) {
		launch(args);
	}

	public Window getPrimaryStage() {
		// TODO Auto-generated method stub
		return primaryStage;
	}
}
