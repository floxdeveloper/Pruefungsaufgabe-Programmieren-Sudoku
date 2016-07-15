package sudoku;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import sudoku.gui.CompleteSudokuGuiTest;
import sudoku.model.SudokuGeneratorTest;
import sudoku.model.SudokuTest;
import sudoku.view.InputNumberPopupControllerTest;
import sudoku.view.RectPosTest;
import sudoku.view.SudokuControllerTest;
import sudoku.view.WrapperControllerTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	CompleteSudokuGuiTest.class,
	SudokuGeneratorTest.class,
	SudokuTest.class,
	InputNumberPopupControllerTest.class,
	RectPosTest.class,
	SudokuControllerTest.class,
	WrapperControllerTest.class
})
public class AllTests {

}
