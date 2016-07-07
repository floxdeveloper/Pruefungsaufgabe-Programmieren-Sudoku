package sudoku.model;

import static helpElements.HelpElements.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SudokuGeneratorTest 
{
	
	Sudoku testSudokuFilledSU;
	Sudoku testSudokuUniqueSolvableSU;
	Sudoku testSudokuToGenerateSU;
	boolean[] alreadyUsed;
	
	
	@Before
	public void setUp(){
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
		testSudokuFilledSU = new Sudoku(testArrayFilled);
		testSudokuUniqueSolvableSU = new Sudoku (testArrayCountUniqueSolvable);
		testSudokuToGenerateSU = new Sudoku(testArrayEmpty);
		alreadyUsed = new boolean[]
				{true, true, true, true, true, true, true, false, true};
				
	}
	
	@Test
	public void copyArrayShouldCopy(){
		assertArrayEquals("Arrays should be the same", testArrayFilled, testSudokuFilledSU.copySudokuArray());
	}
	
	/*
	@Test
	public void getNotTriedNumberShouldReturnInt()
	{
		int intAlreadyUsed = SudokuGenerator.getNotTriedNumber();
		assertEquals("Should return 2", 2, intAlreadyUsed);
		System.out.println("bla");
	}
	*/
	
	@Test
	public void generateShouldReturnSudoku()
	{
		testSudokuToGenerateSU = SudokuGenerator.generate(81);
		assertEquals("Should return true because full", true, testSudokuToGenerateSU.filled());
		
		testSudokuToGenerateSU = SudokuGenerator.generate(20);
		assertEquals("Should return false because not full", false, testSudokuToGenerateSU.filled());
		
		testSudokuToGenerateSU = SudokuGenerator.generate(0);
		assertEquals("Should return true because empty", true, testSudokuToGenerateSU.empty());
	}
	
	

}