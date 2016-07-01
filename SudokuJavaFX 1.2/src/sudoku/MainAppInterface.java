package sudoku;

import javafx.stage.Stage;
import javafx.stage.Window;
import sudoku.model.Sudoku;
import sudoku.view.SudokuController;

public interface MainAppInterface {

	void start(Stage primaryStage);

	// Load RootLayout
	/**
	 * Connects the rootLayout with the MainStage and the WrapperController
	 * Shows the new Scene
	 */
	void initRootLayout();

	Stage getCurrentStage();

	void lockScreen();

	/**
	 * Shows the sudoku solver inside the root layout.
	 */
	void initSudokuLayout();

	// Used to send warnings with custom header and content
	void warning(String header, String content);

	// Used to send warnings with custom header and content
	void information(String header, String content);

	// Used to send error messanges with custom header and content
	void error(String header, String content);

	SudokuController getSudokuController();

	boolean setSudoku(int[][] array);

	boolean setSudoku(Sudoku s);

	Sudoku getSudoku();

	Window getPrimaryStage();

}