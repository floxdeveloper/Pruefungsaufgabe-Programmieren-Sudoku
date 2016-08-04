package sudoku.gui;

import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;

import sudoku.MainApp;

@RunWith(JUnit4.class)
public class CompleteSudokuGuiTest{
	private FxRobot robot;

	@Before
	public void setUp() throws Exception {
		FxToolkit.registerPrimaryStage();
		FxToolkit.setupApplication(MainApp.class);
		robot = new FxRobot();
	}
	
	@Test
	public void testIfAboutWindowOpensAndCanBeClosed(){
		robot.clickOn("#help");
		robot.clickOn("#about");
		robot.clickOn("#closeButton");
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testSudokuGenerate(){
		robot.clickOn("#sudoku");
		robot.clickOn("#generate");
		robot.clickOn("#eingabecb");
		robot.moveTo("25");
		robot.scroll(15);
		robot.clickOn("70");
		robot.clickOn("OK");
		
		robot.sleep(10000);
		
		robot.clickOn("Solve");
		robot.clickOn("OK");
	}
	
	@After
	public void clean(){
		try {
			FxToolkit.cleanupStages();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}
}
