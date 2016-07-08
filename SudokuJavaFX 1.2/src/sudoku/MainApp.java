package sudoku;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import sudoku.model.Sudoku;
import sudoku.view.SudokuController;
import sudoku.view.WrapperController;

public class MainApp extends Application implements MainAppInterface {

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

		// Leeres Sudoku in MainApp laden
		sudoku = new Sudoku(new int[9][9]);
		sudoku.sudokuReset();

		initRootLayout();
		initSudokuLayout();

		// Verarbeitet Tastatureingaben -> um Zahlen eingeben zu können
		primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			public void handle(final KeyEvent keyEvent) {

				try {
					int eingabe = Integer.parseInt(keyEvent.getText());

					// Mit 0 soll man nicht löschen können
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

	// Load RootLayout
	
	/**
	 * Verbindet das Root Layout mit der Main Stage und dem WrapperController
	 * Zeigt die neue Szene an
	 */
	@Override
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

	private Stage currentStage;
	
	@Override

	public Stage getCurrentStage() {
		return this.currentStage;
	}

	
	


	/**
	 * Öffnet WrapperLock als Sperrbildschirm vor der primaryStage.
	 */
	@Override
	public void lockScreen() {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainAppTest.class.getResource("view/WrapperLock.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initStyle(StageStyle.TRANSPARENT);
			dialogStage.initOwner(this.primaryStage);
			dialogStage.setResizable(false);
			dialogStage.setAlwaysOnTop(true);
			dialogStage.setOpacity(0.9);
			Scene scene = new Scene(pane);
			dialogStage.setScene(scene);

			// set Stage boundaries to the lower right corner of the visible
			// bounds of the main screen
			dialogStage.setHeight(this.primaryStage.getHeight());
			dialogStage.setWidth(this.primaryStage.getWidth());
			dialogStage.setX(this.primaryStage.getX());
			dialogStage.setY(this.primaryStage.getY());
			this.currentStage = dialogStage;
			// Show the dialog and wait until the user closes it
			dialogStage.show();
			System.out.println("bla");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void unlockScreen() {
		try{
			currentStage.close();
		}
		catch(NullPointerException e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Zeigt den Sudoku Solver im rootLayout und setzt den SudokuController in
	 * die Mainapp.
	 */
	@Override
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

	/**
	 * Gibt eine Warnung mit mainApp als owner aus
	 * 
	 * @param header
	 *            gibt den header der Warnung an
	 * @param content
	 *            gibt den Haupttext der Warnung an
	 */

	@Override
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
		
	/**
	 * Gibt eine Informationsmeldung mit mainApp als owner aus
	 * 
	 * @param header
	 *            gibt den header der Information an
	 * @param content
	 *            gibt den Haupttext der Information an
	 */
	public void information(String header, String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(primaryStage);
		alert.setTitle("Information");

		if (header.equals(""))
			return;

		alert.setHeaderText(header);
		alert.setContentText(content);

		alert.showAndWait();

	}

	/**
	 * Gibt einen Fehler mit mainApp als owner aus
	 * 
	 * @param header
	 *            gibt den header der Fehlermeldung an
	 * @param content
	 *            gibt den Haupttext der Fehlermeldung an
	 */
	@Override
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

	
	@Override
	public SudokuController getSudokuController() {
		return scontroller;
	}

	/**
	 * 
	 * Sudoku wird gesetzt wenn es den Regeln entspricht. Updated GUI.
	 * 
	 * @param array
	 * @return true wenn gesetzt; false sonst
	 */
	@Override
	public boolean setSudoku(int[][] array) {

	
		// Setzt Sudoku Array und überprüft ob es den Regeln entspricht -> wenn
		// nicht bleibt alter Stand bestehen
		if (!sudoku.setSudokuIfCorrect(array))
			return false;
		
		scontroller.sudokuChanged();
		return true;

	}
	
	/**
	 * Man setzt s als neues sudoku und updatet GUI.
	 * 
	 * @param s
	 * @return true, da Sudoku immer Regeln entspricht
	 */
	@Override
	public boolean setSudoku(Sudoku s) {
		sudoku = s;
		scontroller.sudokuChanged();
		return true;

	}


	@Override
	public Sudoku getSudoku() {
		return sudoku;
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public Window getPrimaryStage() {
		return primaryStage;
	}
	
	public Parent getRoot(){
		return rootLayout;
	}
}
