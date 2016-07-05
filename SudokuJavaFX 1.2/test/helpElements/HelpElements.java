package helpElements;



import sudoku.model.Sudoku;

public class HelpElements {
	
	public static int[][] testArrayEmpty = new int[9][9];
	public static int[][] testArrayFilled = new int[][] 
		 {{5,3,4,6,7,8,9,1,2},
		  {6,7,2,1,9,5,3,4,8},
		  {1,9,8,3,4,2,5,6,7},
		  {8,5,9,7,6,1,4,2,3},
		  {4,2,6,8,5,3,7,9,1},
		  {7,1,3,9,2,4,8,5,6},
		  {9,6,1,5,3,7,2,8,4},
		  {2,8,7,4,1,9,6,3,5},
		  {3,4,5,2,8,6,1,7,9}}; 
	public static int[][] testArrayWithNull = new int[][]
		 {{0,0,0,0,0,0,0,0,0},
		  {0,0,0,0,0,0,0,0,0}, 
		  {0,0,0,0,0,0,0,0,0},
		  {0,0,0,0,0,0,0,0,0},
		  {0,0,0,0,0,0,0,0,0},
		  {0,0,0,0,0,0,0,0,0},
		  {0,0,0,0,0,0,0,0,0},
		  {0,0,0,0,0,0,0,0,0},
		  {0,0,0,0,0,0,0,0,0}}; 
    public static int[][] testArrayCountUniqueSolvable = new int[][] 
		 {{5,3,4,6,7,8,9,1,0},
		  {6,7,2,1,9,5,3,4,8},
		  {1,9,8,3,4,2,5,6,7},
		  {8,5,9,7,6,1,4,2,3},
		  {4,2,6,8,5,3,7,9,1},
		  {7,1,3,9,2,4,8,5,6},
		  {9,6,1,5,3,7,2,8,4},
		  {2,8,7,4,1,9,6,3,5},
		  {3,4,5,2,8,6,1,7,9}};
	public static int[][] testArrayCountUnSolvable = new int[][]
		 {{0,0,0,1,0,0,0,0,0},
		  {0,0,0,2,0,0,0,0,0}, 
		  {0,0,0,0,0,0,0,0,0},
		  {1,2,0,0,0,0,0,0,0},
		  {0,0,0,0,0,0,0,0,0}, //[5][5] = 1 and 2
		  {0,0,0,0,0,0,1,2,0},
		  {0,0,0,0,0,1,0,0,0},
		  {0,0,0,0,0,2,0,0,0},
		  {0,0,0,0,0,0,0,0,0}}; 
	public static int[][] testArrayFalse = new int[][] 
		 {{5,3,4,6,7,8,9,1,2},
		  {6,7,1,1,9,5,3,4,8}, // [2][3] false
		  {1,9,8,3,4,2,5,6,7},
		  {8,5,9,7,6,1,4,2,3},
		  {4,2,6,8,5,3,7,9,1},
		  {7,1,3,9,2,4,8,5,6},
		  {9,6,1,5,3,7,2,8,4},
		  {2,8,7,4,1,9,6,3,5},
		  {3,4,5,2,8,6,1,7,9}};	
	public static int[][] testArrayHalfFilled = new int[][]
		 {{0,3,0,0,0,0,0,0,0},
		  {0,0,0,1,9,5,0,0,0},
	      {0,0,8,0,0,0,0,6,0},
		  {8,0,0,0,6,0,0,0,0},
		  {4,0,0,8,0,0,0,0,1},
		  {0,0,0,0,2,0,0,0,0},
		  {0,6,0,0,0,0,2,8,0},
		  {0,0,0,4,1,9,0,0,5},
		  {0,0,0,0,0,0,0,7,0}};
		  
	public static Sudoku testSudokuEmpty;
	public static Sudoku testSudokuFilled;
	public static Sudoku testSudokuWithNull;
	public static Sudoku testSudokuCountUniqueSolvable;
	public static Sudoku testSudokuCountUnSolvable;
	public static Sudoku testSudokuFalse;
	public static Sudoku testSudokuHalfFilled;
		  
}
