package sudoku.model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class SudokuGeneratorTest {
	Sudoku testSudoku;
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
	
	@Before
	public void setUp(){
		testSudoku = new Sudoku(testArrayFilled);
	}
	
	@Test
	public void copyArrayShouldCopy(){
		assertArrayEquals("Arrays should be the same", testArrayFilled, testSudoku.copySudokuArray());
	}
	
	@Test
	public void moduluHochzaehlenShouldReturnFrom1to9(){
		assertEquals("0 % 10 should return 1", 1, sudoku.model.SudokuGenerator.moduloHochzaehlen(0));
		assertEquals("1 % 10 should return 2", 2, sudoku.model.SudokuGenerator.moduloHochzaehlen(1));
		assertEquals("2 % 10 should return 3", 3, sudoku.model.SudokuGenerator.moduloHochzaehlen(2));
		assertEquals("3 % 10 should return 4", 4, sudoku.model.SudokuGenerator.moduloHochzaehlen(3));
		assertEquals("4 % 10 should return 5", 5, sudoku.model.SudokuGenerator.moduloHochzaehlen(4));
		assertEquals("5 % 10 should return 6", 6, sudoku.model.SudokuGenerator.moduloHochzaehlen(5));
		assertEquals("6 % 10 should return 7", 7, sudoku.model.SudokuGenerator.moduloHochzaehlen(6));
		assertEquals("7 % 10 should return 8", 8, sudoku.model.SudokuGenerator.moduloHochzaehlen(7));
		assertEquals("8 % 10 should return 9", 9, sudoku.model.SudokuGenerator.moduloHochzaehlen(8));
		assertEquals("9 % 10 should return 1", 1, sudoku.model.SudokuGenerator.moduloHochzaehlen(9));
		assertEquals("10 % 10 should return 1", 1, sudoku.model.SudokuGenerator.moduloHochzaehlen(10));
		assertEquals("1348973 % 10 should return 4", 4, sudoku.model.SudokuGenerator.moduloHochzaehlen(1348973));
		assertEquals("9584 % 10 should return 5", 5, sudoku.model.SudokuGenerator.moduloHochzaehlen(9584));
	}
	
	
	
	
	
	
	
}
/*public void sudokuBefuellen() {

	sudoku[0][0] = 1;
	sudoku[2][0] = 7;
	sudoku[6][0] = 3;
	sudoku[7][0] = 6;
	sudoku[8][0] = 2;
	sudoku[4][1] = 9;
	sudoku[7][1] = 8;
	sudoku[0][2] = 8;
	sudoku[2][2] = 2;
	sudoku[4][2] = 6;
	sudoku[6][2] = 5;
	sudoku[8][2] = 9;
	sudoku[1][3] = 6;
	sudoku[3][3] = 5;
	sudoku[6][3] = 2;
	sudoku[8][3] = 3;
	sudoku[1][4] = 3;
	sudoku[6][4] = 7;
	sudoku[8][4] = 8;
	sudoku[1][5] = 1;
	sudoku[3][5] = 3;
	sudoku[4][5] = 7;
	sudoku[5][5] = 8;
	sudoku[8][5] = 5;
	sudoku[0][6] = 3;
	sudoku[3][6] = 9;
	sudoku[5][6] = 5;
	sudoku[8][6] = 6;
	sudoku[0][7] = 5;
	sudoku[1][7] = 8;
	sudoku[5][7] = 1;
	sudoku[3][8] = 4;
	sudoku[5][8] = 6;
	sudoku[6][8] = 8;
	sudoku[8][8] = 7;

}

public void test() {

	sudoku[0][0] = 3;
	sudoku[4][0] = 2;
	sudoku[5][0] = 8;
	sudoku[2][1] = 1;
	sudoku[3][1] = 7;
	sudoku[7][1] = 9;
	sudoku[8][1] = 4;
	sudoku[0][2] = 7;
	sudoku[1][2] = 8;
	sudoku[5][2] = 6;
	sudoku[6][2] = 5;
	sudoku[3][3] = 3;
	sudoku[4][3] = 5;
	sudoku[8][3] = 8;
	sudoku[1][4] = 9;
	sudoku[2][4] = 8;
	sudoku[6][4] = 7;
	sudoku[7][4] = 3;
	sudoku[0][5] = 4;
	sudoku[4][5] = 1;
	sudoku[5][5] = 9;
	sudoku[2][6] = 4;
	sudoku[3][6] = 6;
	sudoku[7][6] = 7;
	sudoku[8][6] = 5;
	sudoku[0][7] = 8;
	sudoku[1][7] = 3;
	sudoku[5][7] = 4;
	sudoku[6][7] = 1;
	sudoku[3][8] = 2;
	sudoku[4][8] = 7;
	sudoku[8][8] = 9;

}*/