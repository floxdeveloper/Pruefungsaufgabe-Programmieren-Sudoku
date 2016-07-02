package sudoku.gui;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import sudoku.MainApp;
import sudoku.MainAppInterface;
import sudoku.view.RectPos;

public class SudokuGuiTest{
	AnchorPane rootLayout;
	
	/*The widgets of the gui used for the tests */
	RectPos rect10;
	MainAppInterface mainApp;
	
	private FxRobot robot;

	@Before
	public void setUp() throws Exception {
		FxToolkit.registerPrimaryStage();
		FxToolkit.setupApplication(MainApp.class);
		robot = new FxRobot();
	}
	
}
