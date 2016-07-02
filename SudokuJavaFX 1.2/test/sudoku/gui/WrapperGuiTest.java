package sudoku.gui;

import org.junit.Before;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;

import sudoku.MainApp;

public class WrapperGuiTest{
	private FxRobot robot;

	@Before
	public void setUp() throws Exception {
		FxToolkit.registerPrimaryStage();
		FxToolkit.setupApplication(MainApp.class);
		robot = new FxRobot();
	}
	
	
	
	
}
