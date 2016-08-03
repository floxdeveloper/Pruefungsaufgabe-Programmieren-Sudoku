package sudoku;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
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

/**
 * Diese Klasse bildet die Grundlage des Sudoku-Programms. Sie ruft die
 * graphische Oberfl�che auf und verwaltet sie, indem sie einige Elemente sperrt
 * oder entsperrt und verwaltet Sudokus.
 */
public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private Sudoku sudoku;
	private SudokuController scontroller;
	private WrapperController wcontroller;
	private Stage currentStage;

	/**
	 * Gibt den SudokuController aus.
	 * 
	 * @return den SudokuController der MainApp
	 */
	public SudokuController getSudokuController() {
		return scontroller;
	}

	/**
	 * Gibt den WrapperController aus.
	 * 
	 * @return den WrapperController der MainApp
	 */
	public WrapperController getWrapperController() {
		return wcontroller;
	}

	/**
	 * Gibt die aktuelle Stage zum Handling aus.
	 * 
	 * @return die aktuell behandelte Stage der MainApp
	 */
	public Stage getCurrentStage() {
		return this.currentStage;
	}

	/**
	 * Gibt das Sudoku aus.
	 * 
	 * @return das Sudoku der MainApp
	 */
	public Sudoku getSudoku() {
		return sudoku;
	}

	/**
	 * Gibt die PrimaryStage zum Handling aus.
	 * 
	 * @return die PrimaryStage der MainApp
	 */
	public Window getPrimaryStage() {
		return primaryStage;
	}

	/**
	 * Gibt das rootLayout aus.
	 * 
	 * @return das rootLayout der MainApp
	 */
	public Parent getRoot() {
		return rootLayout;
	}

	/**
	 * Setzt Sudoku-Array und �berpr�ft, ob es den Regeln entspricht - wenn
	 * nicht, bleibt der alte Stand bestehen.
	 * 
	 * @param array
	 *            - neues Sudoku vom Typ zweidimensionales Array
	 * @return true - Sudoku konnte gesetzt werden, false - nicht gesetzt, da
	 *         nicht korrekt
	 */
	public boolean setSudoku(int[][] array) {
		if (!sudoku.setSudokuIfCorrect(array))
			return false;

		scontroller.sudokuAnzeigen();
		return true;
	}

	/**
	 * Setzt ein existierendes, regelkonformes Sudoku als neues Sudoku und
	 * updatet GUI.
	 * 
	 * @param s
	 *            neues Sudoku vom Typ Sudoku
	 */
	public void setSudoku(Sudoku s) {
		sudoku = s;
		scontroller.sudokuAnzeigen();
	}

	/**
	 * Primary Stage wird geladen und konfiguriert. Hauptfenster wird mit
	 * Komponenten und Handlern verkn�pft.
	 */
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("SudokuSolver");
		primaryStage.setResizable(false);

		// Setzt das Icon f�r die Applikation.
		this.primaryStage.getIcons().add(new Image("file:resources/images/sudoku.png"));

		// Leeres Sudoku in MainApp laden
		sudoku = new Sudoku(new int[9][9]);
		sudoku.sudokuReset();

		initRootLayout();
		initSudokuLayout();

		// Verarbeitet Tastatureingaben, um Zahlen eingeben zu k�nnen.
		primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			public void handle(final KeyEvent keyEvent) {

				try {
					int eingabe = Integer.parseInt(keyEvent.getText());
					// Mit 0 soll man nicht l�schen k�nnen.
					if (eingabe != 0)
						scontroller.handleEingabe(eingabe);
				}catch (NumberFormatException e) {
					if (keyEvent.getCode() == KeyCode.DELETE || keyEvent.getCode() == KeyCode.SPACE
							|| keyEvent.getCode() == KeyCode.BACK_SPACE)
						scontroller.handleEingabe(0);
				}
			}
		});
	}

	/**
	 * Verbindet das Root-Layout mit der Main-Stage und dem WrapperController.
	 * Zeigt die neue Szene an.
	 */
	public void initRootLayout() {
		try {
			// L�dt das root-Layout von der fxml-Datei.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/Wrapper.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Setzt controller als MainStage.
			wcontroller = loader.getController();
			wcontroller.setMainApp(this);

			// Zeigt die scene, welche das root-Layout beinhaltet.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ffnet WrapperLock als Sperrbildschirm vor der primaryStage.
	 * Fensterelemente sind nicht mehr klickbar: keine Bearbeitung m�glich, bis
	 * die Verarbeitung abgeschlossen ist.
	 */
	public void lockScreen() {
		try {
			// L�dt fxml-Datei und erzeugt eine neue stage f�r das Popup-gif.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/WrapperLock.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initStyle(StageStyle.TRANSPARENT);
			dialogStage.initOwner(this.primaryStage);
			dialogStage.setResizable(false);
			dialogStage.setOpacity(0.9);
			Scene scene = new Scene(pane);
			dialogStage.setScene(scene);

			// Setze Gr��e und setze das gif in die Mitte vom Sudokufeld.
			dialogStage.setHeight(127);
			dialogStage.setWidth(127);
			dialogStage.setX(this.primaryStage.getX() + 144);
			dialogStage.setY(this.primaryStage.getY() + 196);
			this.currentStage = dialogStage;
			// Zeigt das gif an, bis es extern geschlossen wird.
			dialogStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Entsperrt die Anwendung: Bearbeitung wird erm�glicht, Elemente werden
	 * dadurch klickbar.
	 */
	public void unlockScreen() {
		try {
			currentStage.close();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Zeigt den Sudoku-Solver im rootLayout und setzt den SudokuController in
	 * die MainApp. Der Controller erm�glicht Reaktionen auf �nderungen.
	 */
	public void initSudokuLayout() {
		try {
			// L�dt die �bersicht.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/Sudoku.fxml"));
			AnchorPane sudoku = (AnchorPane) loader.load();

			// Setzt den Sudoku-Solver in die Mitte vom root-Layout.
			rootLayout.setCenter(sudoku);

			// Setzt controller als Main Stage.
			SudokuController controller = loader.getController();
			controller.setMainApp(this);

			// Verweis auf Controller um beim setSudoku die GUI neu zu laden
			scontroller = controller;

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gibt eine Warnung mit MainApp als Owner aus.
	 * 
	 * @param header
	 *            gibt den Header der Warnung an
	 * @param content
	 *            gibt den Haupttext der Warnung an
	 */
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
	 * Gibt eine Informationsmeldung mit MainApp als Owner aus.
	 * 
	 * @param header
	 *            gibt den Header der Information an
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
	 * Gibt einen Fehler mit MainApp als Owner aus.
	 * 
	 * @param header
	 *            gibt den Header der Fehlermeldung an
	 * @param content
	 *            gibt den Haupttext der Fehlermeldung an
	 */
	public void error(String header, String content) {

		Alert alert = new Alert(AlertType.ERROR);
		alert.initOwner(primaryStage);
		alert.setTitle("Error");

		if (header.equals(""))
			header = "Ein unerwarteter Fehler ist aufgetreten.";

		alert.setHeaderText(header);
		alert.setContentText(content);

		alert.showAndWait();
	}

	/**
	 * Ruft die JavaFX-Methode launch auf, die die graphische Applikation
	 * vorbereitet.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
