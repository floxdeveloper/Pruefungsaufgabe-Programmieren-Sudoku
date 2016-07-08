package sudoku.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;

import javafx.scene.input.KeyCode;
import sudoku.MainApp;

@RunWith(JUnit4.class)
public class SudokuControllerTest {
	SudokuController sController;
	public static MainApp mainApp;
	private FxRobot robot;

	@Before
	public void setUp() throws Exception {
		FxToolkit.registerPrimaryStage();
		mainApp = (MainApp) FxToolkit.setupApplication(MainApp.class);
		robot = new FxRobot(); 
		sController = mainApp.getSudokuController();
	}
	
	@Test
	public void testResetNotLocked(){
		sController.auswahlX = 1;
		sController.auswahlY = 1;
		sController.handleEingabe(8);
		sController.editableField[1][1] = false;
		
		sController.auswahlX = 2;
		sController.auswahlY = 2;
		sController.handleEingabe(9);
		
		sController.resetNotLocked();
		
		assertEquals("In 2Z2S soll keine Zahl mehr stehen", " ", sController.getKoordinate(2, 2).getText());
		assertEquals("In 1Z2S soll noch die 8 stehen", "8", sController.getKoordinate(1, 1).getText());
	}
	
	@Test
	public void testResetEdibility(){
		sController.auswahlX = 1;
		sController.auswahlY = 1;
		sController.handleEingabe(8);
		sController.editableField[1][1] = false;
		
		sController.resetEditability();
		assertTrue("Feld 1,1 ist editierbar", sController.editableField[1][1]);
	}
	
	@Test
	public void testHandleEingabe(){		
		
		sController.auswahlX = 1;
		sController.auswahlY = 1;
		sController.handleEingabe(8);		
		assertEquals("8 wurde in der 1. Zeile und 1.Spalte eingegeben", "8" ,sController.getKoordinate(1, 1).getText());
		
		sController.auswahlX = 1;
		sController.auswahlY = 1;
		sController.handleEingabe(9);
		assertEquals("9 wurde in der 1. Zeile und 1.Spalte eingegeben", "9" ,sController.getKoordinate(1, 1).getText());
		
		//warning in falschen Thread -> Testen mit Robot
		robot.clickOn("#r00");
		robot.type(KeyCode.DIGIT9);
		robot.clickOn("OK");
		assertEquals("9 wurde nicht in die 0. Zeile und 0. Spalte eingegeben, da 9 in 1Z1S", " " ,sController.getKoordinate(0,0).getText());
		
		//Nebeneffekt testen bei Löschen aus gefülltem Feld
		robot.clickOn("#r11");
		robot.type(KeyCode.DELETE);
		assertEquals("9 wurde aus 1Z1S gelöscht", " " ,sController.getKoordinate(1,1).getText());
		
		sController.auswahlX = 2;
		sController.auswahlY = 2;
		sController.handleEingabe(0);
		assertEquals("0 führt zu keiner Veränderung im Sudoku", " " ,sController.getKoordinate(2, 2).getText());
		
	}

	@After
	public void clean(){
		try {
			FxToolkit.cleanupApplication(mainApp);
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			FxToolkit.hideStage();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
