package sudoku.view;

import javafx.stage.Stage;
import javafx.stage.Window;
import sudoku.MainAppInterface;
import sudoku.model.Sudoku;

public class FakeMainApp implements MainAppInterface {

	@Override
	public void start(Stage primaryStage) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initRootLayout() {
		// TODO Auto-generated method stub

	}

	@Override
	public Stage getCurrentStage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void lockScreen() {
		// TODO Auto-generated method stub

	}

	@Override
	public void unlockScreen() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void initSudokuLayout() {
		// TODO Auto-generated method stub

	}

	@Override
	public void warning(String header, String content) {
		// TODO Auto-generated method stub

	}

	@Override
	public void information(String header, String content) {
		// TODO Auto-generated method stub

	}

	@Override
	public void error(String header, String content) {
		// TODO Auto-generated method stub

	}

	@Override
	public SudokuController getSudokuController() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setSudoku(int[][] array) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setSudoku(Sudoku s) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Sudoku getSudoku() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Window getPrimaryStage() {
		// TODO Auto-generated method stub
		return null;
	}



}
