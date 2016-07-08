package sudoku;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import sudoku.gui.CompleteSudokuGuiTest;
import sudoku.model.*;
import sudoku.view.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	CompleteSudokuGuiTest.class,
	SudokuGeneratorTest.class,
	SudokuTest.class,
	//FakeMainApp.class,
	InputNumberPopupControllerTest.class,
	RectPosTest.class,
	SudokuControllerTest.class,
	WrapperControllerTest.class
})
public class CompleteSudokuTest 
{
	// Alle Tests werden ausgeführt
}
