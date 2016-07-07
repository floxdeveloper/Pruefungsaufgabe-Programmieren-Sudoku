package sudoku.model;

import static helpElements.HelpElements.*;
import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

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
				  
		testSudokuEmptySU = new Sudoku (testArrayEmpty);
		testSudokuFilledSU = new Sudoku (testArrayFilled);
		testSudokuWithNullSU = new Sudoku (testArrayWithNull);
		testSudokuCountUniqueSolvableSU = new Sudoku (testArrayCountUniqueSolvable);
		testSudokuCountUnSolvableSU = new Sudoku (testArrayCountUnSolvable);
		testSudokuFalseSU = new Sudoku (testArrayFalse);
		testSudokuHalfFilledSU = new Sudoku(testArrayHalfFilled);
		testSudokuFilledNumberTwo = new Sudoku(testArrayFilled);
	}
	
	@Test
	public void emptyShouldReturnCorrectBool()
	{
		assertEquals("Should return true because empty", true,  testSudokuEmptySU.empty());
	    assertEquals("Should return false because full", false,  testSudokuFilledSU.empty());
	    assertEquals("Should return true because full with Nulls = empty", true, testSudokuWithNullSU.empty());
	}
		
	@Test 
	public void filledShouldReturnCorrectBool()
	{
		assertEquals("Should return true because filled and != 0", true,  testSudokuFilledSU.filled());
		assertEquals("Should return false because empty", false,  testSudokuEmptySU.filled());
		assertEquals("Should return false because full with Nulls = empty", false, testSudokuWithNullSU.filled());
	}
	
	@Test
	public void sudokuResetShouldReturnArrayWithNulls()
	{
		//sudokuReset doesn't return anything
		// First reset
		 testSudokuFilledSU.sudokuReset();
		//Then check array
		assertArrayEquals("Should return an Array filled with nulls",  testArrayWithNull,  testArrayFilled);
	}
	
	@Test
	public void getSudokuArrayShouldReturnArray()
	{
		assertTrue("Should be the same",  testArrayFilled ==  testSudokuFilledSU.getSudokuArray());
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
	public void checkUniqueSolvableShouldReturnCorrectBool()
	{
		// check for true = unique solvable
		assertEquals("Should return true = unique solvable", true,  testSudokuCountUniqueSolvableSU.checkUniqueSolvable());	
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
		
		testSudokuHalfFilledSU.solve();
		assertEquals("Should be solved (true)", true, testSudokuHalfFilledSU.fertig);
		
		testSudokuEmptySU.solve();
		assertEquals("Should be solved (true)", true, testSudokuEmptySU.fertig);
	}
	
	
	
	
	
}
	