package sudoku.model;

import static helpElements.HelpElements.*;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class SudokuGeneratorTest 
{
	Sudoku testSudokuFilledSU;
	Sudoku testSudokuUniqueSolvableSU;
	Sudoku testSudokuToGenerateSU;
	int[] alreadyUsedNumbers;	
	
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
		alreadyUsedNumbers = new int[9];
	}
	
	@Test
	public void copyArrayShouldCopy(){
		assertArrayEquals("Arrays should be the same", testArrayFilled, testSudokuFilledSU.copySudokuArray());
	}
	
	@Test
	public void getNotTriedNumberShouldReturnInt()
	{
		SudokuGenerator.resetAlreadyUsed();
		
		for (int i = 0; i < 9; i++)
		{
			int intAlreadyUsed = SudokuGenerator.getNotTriedNumber();
			alreadyUsedNumbers[i] = intAlreadyUsed;
		}
		
		Arrays.sort(alreadyUsedNumbers);
		
		assertEquals("Should return 1", 1, alreadyUsedNumbers[0]);
		assertEquals("Should return 2", 2, alreadyUsedNumbers[1]);
		assertEquals("Should return 3", 3, alreadyUsedNumbers[2]);
		assertEquals("Should return 4", 4, alreadyUsedNumbers[3]);
		assertEquals("Should return 5", 5, alreadyUsedNumbers[4]);
		assertEquals("Should return 6", 6, alreadyUsedNumbers[5]);
		assertEquals("Should return 7", 7, alreadyUsedNumbers[6]);
		assertEquals("Should return 8", 8, alreadyUsedNumbers[7]);
		assertEquals("Should return 9", 9, alreadyUsedNumbers[8]);
		
		for (int i = 0; i<9; i++)
		{
			assertFalse("10 should not appear", 10 == alreadyUsedNumbers[i]);
		}
	}
	
	@Test
	public void generateShouldReturnSudoku()
	{
		testSudokuToGenerateSU = SudokuGenerator.generate(81);
		assertEquals("Should return true because full", true, testSudokuToGenerateSU.isFilled());
		
		testSudokuToGenerateSU = SudokuGenerator.generate(20);
		assertEquals("Should return false because not full", false, testSudokuToGenerateSU.isFilled());
		
		testSudokuToGenerateSU = SudokuGenerator.generate(0);
		assertEquals("Should return true because empty", true, testSudokuToGenerateSU.isEmpty());
	}
}