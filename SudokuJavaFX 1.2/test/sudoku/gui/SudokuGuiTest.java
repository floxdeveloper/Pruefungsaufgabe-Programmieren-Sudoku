package sudoku.gui;

import org.junit.Before;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;

import javafx.scene.layout.AnchorPane;
import sudoku.MainApp;
import sudoku.view.RectPos;

public class SudokuGuiTest{
	AnchorPane rootLayout;
	
	/*The widgets of the gui used for the tests */
	RectPos rect10;
	MainApp mainApp;
	
	private FxRobot robot;

	@Before
	public void setUp() throws Exception {
		FxToolkit.registerPrimaryStage();
		FxToolkit.setupApplication(MainApp.class);
		robot = new FxRobot();
	}
	
}
