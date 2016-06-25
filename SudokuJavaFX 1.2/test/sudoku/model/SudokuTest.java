package sudoku.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SudokuTest {
	Sudoku testSudokuEmpty;
	Sudoku testSudokuFilled;
	Sudoku testSudokuWithNull;
	int[][] testArrayEmpty = new int[9][9];
	int[][] testArrayFilled = new int[][] 
		 {{5,3,4,6,7,8,9,1,2},
		  {6,7,2,1,9,5,3,4,8},
		  {1,9,8,3,4,2,5,6,7},
		  {8,5,9,7,6,1,4,2,3},
		  {4,2,6,8,5,3,7,9,1},
		  {7,1,3,9,2,4,8,5,6},
		  {9,6,1,5,3,7,2,8,4},
		  {2,8,7,4,1,9,6,3,5},
		  {3,4,5,2,8,6,1,7,9}}; 
	int[][] testArrayWithNull = new int[][]
		 {{0,0,0,0,0,0,0,0,0},
		  {0,0,0,0,0,0,0,0,0}, 
		  {0,0,0,0,0,0,0,0,0},
		  {0,0,0,0,0,0,0,0,0},
		  {0,0,0,0,0,0,0,0,0},
		  {0,0,0,0,0,0,0,0,0},
		  {0,0,0,0,0,0,0,0,0},
		  {0,0,0,0,0,0,0,0,0},
		  {0,0,0,0,0,0,0,0,0}}; 
	    
	@Before
	public void setUp(){
		testSudokuEmpty = new Sudoku(testArrayEmpty); //2
		testSudokuFilled = new Sudoku(testArrayFilled); //2
	}
	
		
	@Test
	public void emptyShouldReturnCorrectBool(){
		assertEquals("Should return true because empty", true, testSudokuEmpty.empty());
		assertEquals("Should return false because full", false, testSudokuFilled.empty());
	}
		
	@Test 
	public void filledShouldReturnCorrectBool(){
		assertEquals("Should return true because filled", true, testSudokuFilled.filled());
		assertEquals("Should return false bause empty", false, testSudokuEmpty.filled());
	}
			
	/*
	@Test
	public void getNextCoordinateShouldReturnIntArray(){
		int[] fullSudoku = new int[] {-1,-1};
		assertArrayEquals("[1][2] should retrun next Coordinate [2][1]", fullSudoku, getNextCoordinate());
	}
	method is private
	*/
	
	@Test
	public void sudokuResetShouldReturnArrayWithNulls(){
		//sudokuReset doesn't return anything
		// First reset
		testSudokuFilled.sudokuReset();
		//Then check array
		assertArrayEquals("Should return an Array filled with nulls", testArrayWithNull, testArrayFilled);
	}
	
	
	
}
