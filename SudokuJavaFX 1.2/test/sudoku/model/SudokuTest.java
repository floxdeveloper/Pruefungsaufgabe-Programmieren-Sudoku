package sudoku.model;

import static helpElements.HelpElements.testArray10x10;
import static helpElements.HelpElements.testArray9x7;
import static helpElements.HelpElements.testArrayCountUnSolvable;
import static helpElements.HelpElements.testArrayCountUniqueSolvable;
import static helpElements.HelpElements.testArrayEmpty;
import static helpElements.HelpElements.testArrayFalse;
import static helpElements.HelpElements.testArrayFilled;
import static helpElements.HelpElements.testArrayHalfFilled;
import static helpElements.HelpElements.testArrayUnSolvable;
import static helpElements.HelpElements.testArrayWithANumber10;
import static helpElements.HelpElements.testArrayWithNull;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import helpElements.HelpElements;

public class SudokuTest 
{ 	
	
	Sudoku testSudokuEmptySU;
	Sudoku testSudokuFilledSU;
	Sudoku testSudokuWithNullSU;
	Sudoku testSudokuCountUniqueSolvableSU;
	Sudoku testSudokuCountUnSolvableSU;
	Sudoku testSudokuFalseSU;
	Sudoku testSudokuHalfFilledSU;
	Sudoku testSudokuFilledNumberTwo;
	Sudoku testSudokuUnSolvableSU;
	Sudoku testSudoku10x10SU;
	Sudoku testSudoku9x7SU;
	Sudoku testSudokuWithA10SU;
	
	@Before
	public void setUp()
	{
		// Die beiden jedesmal neu befüllen, da sie während Tests geändert werden, 
		// sonst funktionieren emtpy und filled nicht mehr
		testArrayEmpty = new int[9][9];
		testArrayFilled = new int[][] 
				 {{5,3,4,6,7,8,9,1,2},
				  {6,7,2,1,9,5,3,4,8},
				  {1,9,8,3,4,2,5,6,7},
				  {8,5,9,7,6,1,4,2,3},
				  {4,2,6,8,5,3,7,9,1},
				  {7,1,3,9,2,4,8,5,6},
				  {9,6,1,5,3,7,2,8,4},
				  {2,8,7,4,1,9,6,3,5},
				  {3,4,5,2,8,6,1,7,9}}; 
				  
		testSudokuEmptySU = new Sudoku (HelpElements.copySudokuArray(testArrayEmpty));
		testSudokuFilledSU = new Sudoku (HelpElements.copySudokuArray(testArrayFilled));
		testSudokuWithNullSU = new Sudoku (HelpElements.copySudokuArray(testArrayWithNull));
		testSudokuCountUniqueSolvableSU = new Sudoku (HelpElements.copySudokuArray(testArrayCountUniqueSolvable));
		testSudokuCountUnSolvableSU = new Sudoku (HelpElements.copySudokuArray(testArrayCountUnSolvable));
		testSudokuFalseSU = new Sudoku (HelpElements.copySudokuArray(testArrayFalse));
		testSudokuHalfFilledSU = new Sudoku(HelpElements.copySudokuArray(testArrayHalfFilled));
		testSudokuFilledNumberTwo = new Sudoku(HelpElements.copySudokuArray(testArrayFilled));
		testSudokuUnSolvableSU = new Sudoku(HelpElements.copySudokuArray(testArrayUnSolvable));
		testSudoku10x10SU = new Sudoku(HelpElements.copySudokuArray(testArray10x10));
		testSudoku9x7SU = new Sudoku(HelpElements.copySudokuArray(testArray9x7));
		testSudokuWithA10SU = new Sudoku(HelpElements.copySudokuArray(testArrayWithANumber10));
	}
	
	@Test
	public void emptyShouldReturnCorrectBool()
	{
		assertEquals("Should return true because empty", true,  testSudokuEmptySU.isEmpty());
	    assertEquals("Should return false because full", false,  testSudokuFilledSU.isEmpty());
	    assertEquals("Should return true because full with Nulls = empty", true, testSudokuWithNullSU.isEmpty());
	}
		
	@Test 
	public void filledShouldReturnCorrectBool()
	{
		assertEquals("Should return true because filled and != 0", true,  testSudokuFilledSU.isFilled());
		assertEquals("Should return false because empty", false,  testSudokuEmptySU.isFilled());
		assertEquals("Should return false because full with Nulls = empty", false, testSudokuWithNullSU.isFilled());
	}
	
	@Test
	public void sudokuResetShouldReturnArrayWithNulls()
	{
		// First reset
		 testSudokuFilledSU.sudokuReset();
		//Then check array
		assertArrayEquals("Should return an Array filled with nulls",  testArrayWithNull,  testSudokuFilledSU.getSudokuArray());
	}
	
	@Test
	public void getSudokuArrayShouldReturnArray()
	{
		assertArrayEquals("Should be the same",  testArrayFilled, testSudokuFilledSU.getSudokuArray());
	}
	
	@Test
	public void copySudokuArrayShouldReturnCopiedSudoku()
	{
		assertArrayEquals("Should return copied Sudoku",  testArrayFilled,  testSudokuFilledSU.copySudokuArray());
	}
	
	
	@Test 
	public void solveCountShouldReturnCorrectInteger()
	{
		// check for 1 = unique solvable
		testSudokuCountUniqueSolvableSU.solveCount();
		assertEquals("Should return 1 = unique solvable", 1,  testSudokuCountUniqueSolvableSU.solveCounter);
		
		// check for 2 = not clearly solvable
		testSudokuWithNullSU.solveCount();
		assertEquals("Should return 2 = not clearly solvable", 2,  testSudokuWithNullSU.solveCounter);
		
		/*
		// check for 0 = unsolvable - takes a lot of time... 
		testSudokuCountUnSolvableSU.solveCount();
		assertEquals("Should return 0 = unsolvable", 0, testSudokuCountUnSolvableSU.solveCounter);
		*/
	}
	
	
	@Test
	public void checkIfCorrectSudokuShoudlReturnCorrectBool()
	{
		// check if available in row, column and 3x3 square
		assertEquals("Should return true", true, testSudokuCountUniqueSolvableSU.checkIfCorrectSudoku(testArrayCountUniqueSolvable));
		assertEquals("Should return true", true, testSudokuFilledSU.checkIfCorrectSudoku(testArrayFilled));
		assertEquals("Should return true", true, testSudokuEmptySU.checkIfCorrectSudoku(testArrayEmpty));
	
		assertEquals("Should return false", false,  testSudokuFalseSU.checkIfCorrectSudoku(testArrayFalse));
	}
	
	
	@Test
	public void setSudokuIfCorrectShouldReturnCorrectBool()
	{
		// check if Sudoku is correct
		assertEquals("Should return true", true, testSudokuCountUniqueSolvableSU.setSudokuIfCorrect(testArrayCountUniqueSolvable));
		assertEquals("Should return true", true, testSudokuFilledSU.setSudokuIfCorrect(testArrayFilled));
		assertEquals("Should return true", true, testSudokuEmptySU.setSudokuIfCorrect(testArrayEmpty));
	
		assertEquals("Should return false", false,  testSudokuFalseSU.setSudokuIfCorrect(testArrayFalse));
	}
	
	
	@Test
	public void solveShouldSolveSudoku()
	{
		assertEquals("Should not be solved (false)", false, testSudokuCountUniqueSolvableSU.fertig);
		testSudokuCountUniqueSolvableSU.solve();
		assertEquals("Should be solved (true)", true, testSudokuCountUniqueSolvableSU.fertig);
		
		assertEquals("Should not be solved (false)", false, testSudokuHalfFilledSU.fertig);
		testSudokuHalfFilledSU.solve();
		assertEquals("Should be solved (true)", true, testSudokuHalfFilledSU.fertig);
		
		assertEquals("Should not be solved (false)", false, testSudokuEmptySU.fertig);
		testSudokuEmptySU.solve();
		assertEquals("Should be solved (true)", true, testSudokuEmptySU.fertig);
		
		assertEquals("Should not be solved (false)", false, testSudokuFilledSU.fertig);
		testSudokuFilledSU.solve(); // fertig = true
		assertEquals("Should be solved (true)", true, testSudokuFilledSU.fertig);
	}
	
	@Test
	public void solveShouldSetSolvability()
	{
		assertEquals("Should be 'not evaluated'", Solvability.notEvaluated, testSudokuFilledSU.getSolvability());
		testSudokuFilledSU.solve();
		assertEquals("Should be solvable", Solvability.solvable, testSudokuFilledSU.getSolvability());
		
		testSudokuCountUnSolvableSU.solve();
		assertEquals("Should be 'probably not solvable'", Solvability.probablyNotSolvable, testSudokuCountUnSolvableSU.getSolvability());
		
		testSudokuFilledSU.solve();
		assertEquals("Should be 'solvable'", Solvability.solvable, testSudokuFilledSU.getSolvability());
		
		testSudokuUnSolvableSU.solve();
		assertEquals("Should be 'not solvable'", Solvability.notSolvable, testSudokuUnSolvableSU.getSolvability());
		
	}
	
	@Test
	public void solveCountShouldReturnCorrectSolvability()
	{
		assertEquals("Should return 'not evaluated'", Solvability.notEvaluated, testSudokuUnSolvableSU.getSolvability());
		testSudokuUnSolvableSU.solveCount();
		assertEquals("Should return 'not solvable'", Solvability.notSolvable, testSudokuUnSolvableSU.getSolvability());
		
		
	}
	
	@Test
	public void checkUniqueSolvableShouldReturnSolvability()
	{
		assertEquals("Should be 'uniquely solvable'", Solvability.uniquelySolvable, testSudokuFilledSU.checkUniqueSolvable());
		assertEquals("Should be 'probably not solvable'", Solvability.probablyNotSolvable, testSudokuCountUnSolvableSU.checkUniqueSolvable());
		assertTrue("Should be 'uniquely solvable'", Solvability.uniquelySolvable == testSudokuCountUniqueSolvableSU.checkUniqueSolvable());
	}
	
	@Test
	public void checkIfCorrectSudokuShouldReturnCorrectBool()
	{
		assertEquals("Should return false because 10x10", false, testSudoku10x10SU.checkIfCorrectSudoku(testArray10x10));
		assertEquals("Should return false because 8x7", false, testSudoku9x7SU.checkIfCorrectSudoku(testArray9x7));
		assertEquals("Should return true because 9x9", true, testSudokuEmptySU.checkIfCorrectSudoku(testArrayEmpty));
		assertEquals("Should return false because there is a 10", false, testSudokuWithA10SU.checkIfCorrectSudoku(testArrayWithANumber10));
	}
	
	@Test
	public void solveIfUnderOneSecondShouldSolveSudoku()
	{
		// Checks if Sudoku is solvable under one second
		testSudokuCountUniqueSolvableSU.solveIfUnderOneSec();
		assertEquals("Should return 'not evaluated'", Solvability.notEvaluated, testSudokuCountUnSolvableSU.getSolvability());
	}
}
	