package sudoku;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sudoku.model.Sudoku;
import sudoku.view.sudokuController;
import sudoku.view.wrapperController;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private Sudoku sudoku;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("SudokuSolver");
		
		// Set the application icon.
        this.primaryStage.getIcons().add(new Image("file:resources/images/sudoku.png"));
        
		int[][] sudokuFeld = new int[9][9];		
		for (int i = 0; i<9;i++){
			for(int j = 0;j<9;j++){
			sudokuFeld[i][j]=0;	
		}
		}
		sudoku = new Sudoku(sudokuFeld);

		initRootLayout();
		initSudokuLayout();
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
	
	public boolean setSudoku(int[][] array){
		
		int horizontal = array.length;
		
		//Prüft prinzipielle Groesse
		if (horizontal!=9)
			return false;
		for (int i = 0; i<9;i++){
			if (array[i].length!=9)
				return false;			
		}
		
		for(int i =0;i<9;i++){
			for(int j = 0;j<9;j++){
				if(array[i][j]>9 || array[i][j] <1)
					return false;		
			}
		}
		
		sudoku.setSudoku(array);
		return true;
		
	}
	
	public boolean setSudoku(Sudoku s){
		sudoku = s;
		return true;
		
	}
	
	public Sudoku getSudoku(){		
		return sudoku;		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
