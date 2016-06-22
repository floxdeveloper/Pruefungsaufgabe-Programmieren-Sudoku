package sudoku;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import sudoku.model.Sudoku;
import sudoku.view.SudokuController;
import sudoku.view.WrapperController;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private Sudoku sudoku;
	private SudokuController scontroller;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("SudokuSolver");
		primaryStage.setResizable(false);

		// Set the application icon.
		this.primaryStage.getIcons().add(new Image("file:resources/images/sudoku.png"));

		//Leeres Sudoku in MainApp laden
		sudoku = new Sudoku(new int[9][9]);
		sudoku.sudokuReset();

		initRootLayout();
		initSudokuLayout();

		//Verarbeitet Tastatureingaben -> um Zahlen eingeben zu k�nnen
		primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			public void handle(final KeyEvent keyEvent) {

				try {
					int eingabe = Integer.parseInt(keyEvent.getText());

					// Mit 0 soll man nicht l�schen k�nnen
					if (eingabe != 0)
						scontroller.handleEingabe(eingabe);
				}

				catch (NumberFormatException e) {
					if (keyEvent.getCode() == KeyCode.DELETE || keyEvent.getCode() == KeyCode.SPACE
							|| keyEvent.getCode() == KeyCode.BACK_SPACE)
						scontroller.handleEingabe(0);

				}
			}

		});
	}

	

	// L�dt das Root Layout
	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/Wrapper.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Set controller to Main Stage
			WrapperController controller = loader.getController();
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
			loader.setLocation(MainApp.class.getResource("view/Sudoku.fxml"));
			AnchorPane sudoku = (AnchorPane) loader.load();

			// Set sudoku solver into the center of root layout.
			rootLayout.setCenter(sudoku);

			// Set controller to Main Stage
			SudokuController controller = loader.getController();
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

	public SudokuController getSudokuController() {
		return scontroller;
	}

	public boolean setSudoku(int[][] array) {

		int horizontal = array.length;

		// Pr�ft prinzipielle Groesse
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


		//Setzt Sudoku Array und �berpr�ft ob es den Regeln entspricht -> wenn nicht bleibt alter Stand bestehen
		if (!sudoku.setSudoku(array))
			return false;
		
		
		scontroller.sudokuChanged();
		return true;

	}
	

	public boolean setSudoku(Sudoku s) {
		sudoku = s;
		scontroller.sudokuChanged();
		return true;

	}



	public Sudoku getSudoku() {
		return sudoku;
	}
	
	public static void showRandomSudoku(Sudoku sudoku){
		
	}

	public static void main(String[] args) {
		launch(args);
	}

	public Window getPrimaryStage() {
		return primaryStage;
	}
}
