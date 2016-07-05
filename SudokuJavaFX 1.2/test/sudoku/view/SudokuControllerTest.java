package sudoku.view;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;

import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import sudoku.MainApp;

@RunWith(JUnit4.class)
public class SudokuControllerTest {
	SudokuController sudokuController;
	public static MainApp mainApp;
	private FxRobot robot;

	@Before
	public void setUp() throws Exception {
		FxToolkit.registerPrimaryStage();
		mainApp = (MainApp) FxToolkit.setupApplication(MainApp.class);
		robot = new FxRobot(); 
	}
	
	@Test
	public void testHandleEingabe(){		
		sudokuController = mainApp.getSudokuController();
		sudokuController.auswahlX = 1;
		sudokuController.auswahlY = 1;
		sudokuController.handleEingabe(8);
		for(int i=0; i<81; i++){
			System.out.println(sudokuController.mapText.get(i));
		}
		robot.clickOn("#r01");
		robot.type(KeyCode.DIGIT8);
	}

	
}
