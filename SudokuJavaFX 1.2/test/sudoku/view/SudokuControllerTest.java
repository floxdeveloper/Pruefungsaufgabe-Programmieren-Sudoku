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
import org.testfx.service.finder.impl.WindowFinderImpl;

import helpElements.HelpElements;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import sudoku.MainApp;
import sudoku.model.Sudoku;

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

	@Test
	public void testHandleReset(){
		sController.auswahlX = 1;
		sController.auswahlY = 1;
		
		sController.handleEingabe(9);
		
		robot.clickOn("#r01");
		
		robot.clickOn("#reset");
		robot.clickOn("OK");
		
		assertEquals("In dem Feld 1,1 darf muss die 9 entfernt worden sein", " ", sController.getKoordinate(1, 1).getText());
		
		//Nach dem Reset sind auch die Felder wieder editierbar -> Test
		
		sController.auswahlX = 1;
		sController.auswahlY = 1;
		
		sController.handleEingabe(9);
		
		assertEquals("In dem Feld konnte eine 9 eingegeben werden", "9", sController.getKoordinate(1, 1).getText());
	}
	
	@Test 
	public void testhandleSolveWithFilledSudoku(){
	}
	
	@Test
	public void testHandleSolveWithEmptySuduko(){
		Sudoku empty = (Sudoku) (HelpElements.testSudokuEmpty);
	}
	
	@Test
	public void testCongratulationDialogAfterLastSuccessfulEingabe(){
		mainApp.getSudoku().setSudokuIfCorrect(HelpElements.testArrayCountUniqueSolvable);
		sController.sudokuAnzeigen();
		
		robot.clickOn("#r08");
		robot.type(KeyCode.DIGIT2);
		
		assertEquals("Nach selbstst. Lösen öffnet sich Congratulation Dialog", "CongratulationPage", new WindowFinderImpl().listWindows().get(1).getScene().getRoot().getId());
		
	}
	
	@Test
	public void testLockEnteredFields(){
		sController.auswahlX = 1;
		sController.auswahlY = 1;
		sController.handleEingabe(8);
		
		sController.lockEnteredFields();
		assertTrue("Feld 1,1 ist gesperrt", sController.editableField[1][1] == false);
		
		sController.auswahlX = 2;
		sController.auswahlY = 2;
		sController.handleEingabe(9);
		assertTrue("Feld 2,2 ist editierbar", sController.editableField[2][2] == true);
	}
	
	@Test
	public void testSudokuChanged(){
		mainApp.getSudoku().setSudokuIfCorrect(HelpElements.testArrayCountUniqueSolvable);
		
		assertEquals("Testfeld und alle anderen Felder sind leer", " ", sController.mapText.get(0).getText());
		
		sController.sudokuAnzeigen();
		
		assertEquals("Testfeld ist 5 und alle anderen Felder sind gefüllt", "5", sController.mapText.get(0).getText());
	}
	
	@Test
	public void testUnselect(){
		//setze das Rechteck r00 auf select
		sController.auswahlX = 0;
		sController.auswahlY = 0;
		sController.mapRect.get(9 * 0 + 0).setStroke(Color.RED);
		
		sController.auswahlX = 0;
		sController.auswahlY = 0;		
		sController.unselect();
		
		assertEquals("Das Rechteck r00 ist abgewählt", Color.TRANSPARENT, sController.mapRect.get(0).getStroke());
	}

	@Test
	public void testSelect(){
		sController.auswahlX = 1;
		sController.auswahlY = 1;
		
		sController.select(0, 0);
		
		assertEquals("Gewähltes Feld ist rot umrandet", Color.RED, sController.mapRect.get(0*9 + 0).getStroke());
	}
	
	@Test
	public void testColorAllBlack(){
		sController.auswahlX = 1;
		sController.auswahlY = 1;
		sController.handleEingabe(8);
		sController.getKoordinate(1, 1).setFill(Color.RED);
		sController.getKoordinate(0, 0).setFill(Color.BLUE);
		
		sController.colorAllBlack();
		
		assertEquals("8 in r11 wieder schwarz, statt rot", Color.BLACK, sController.mapText.get(1*9 + 1).getFill());
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
