package sudoku;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import sudoku.gui.CompleteSudokuGuiTest;
import sudoku.model.SudokuGeneratorTest;
import sudoku.model.SudokuTest;
import sudoku.view.RectPosTest;
import sudoku.view.SudokuControllerTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	CompleteSudokuGuiTest.class,
	SudokuGeneratorTest.class,
	SudokuTest.class,
	RectPosTest.class,
	SudokuControllerTest.class
})

public class AllTests {

}
