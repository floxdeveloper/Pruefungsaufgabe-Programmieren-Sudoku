public class sudoku {

 public static int[][] sudoku = new int[9][9];

 public static void main(String[] args) {

  starten();

 }

 public static void starten() {

  for (int i = 0; i < sudoku.length; i++) {
   for (int j = 0; j < sudoku.length; j++) {
    sudoku[i][j] = 0;
   }
  }

   
  sudokuBefuellen();
  sudokuBT();
 }
 
 
 
 public static void sudokuBefuellen(){
  
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
 
 public static void test(){
  
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
   
  
  
 }

 public static void sudokuBT() {

  int[] koord = naechsteKoordinaten();
  int xkoord = koord[0];
  int ykoord = koord[1];
  
  if(xkoord == -1) // nichts mehr auszuf�llen -> fertig
   ausgabe();  
  else{ //backtracking
   
  for (int i = 1; i <= 9; i++) {  
   if (isSafe(xkoord, ykoord, i)) {
    sudoku[xkoord][ykoord] = i;   
    /*if (ausgefuellt())
     ausgabe();
    else*/
     sudokuBT(); 
     
    sudoku[xkoord][ykoord] = 0;
   }
  }
 }
}

 public static boolean isSafe(int hor, int ver, int zahl) {    // Pr�ft ob eine Zahl an der Stelle hor/ver eingesetzt werden darf

  for (int i = 0; i < sudoku.length; i++) {
   if (sudoku[hor][i] == zahl || sudoku[i][ver] == zahl)
    return false;
  }
  int horizontal;
  int vertikal;
  
  if (hor < 3)
   horizontal = 0;
  else if (hor < 6)
   horizontal = 3;
  else
   horizontal = 6;

  if (ver < 3)
   vertikal = 0;
  else if (ver < 6)
   vertikal = 3;
  else
   vertikal = 6;

  for (int j = vertikal; j < vertikal + 3; j++) {
   for (int i = horizontal; i < horizontal + 3; i++) {
    if (sudoku[i][j] == zahl)
     return false;
   }
  }
  return true;
 }

 public static int[] naechsteKoordinaten() {
   for (int j = 0; j < sudoku.length; j++) {
    for (int i = 0; i < sudoku.length; i++) {
    if (sudoku[i][j] == 0)
     return new int[] { i, j }; // n�chste koordinate, die probiert werden muss
   }
  }
  return new int[] { -1, -1 }; // nichts mehr auszuf�llen -> fertig
 }

 public static boolean ausgefuellt() {
  for (int i = 0; i < sudoku.length; i++) {
   for (int j = 0; j < sudoku.length; j++) {
    if (sudoku[i][j] == 0)
     return false;
   }
  }
  return true;
 }

 public static void ausgabe() {
 
  for (int j = 0; j < sudoku.length; j++) {
   for (int i = 0; i < sudoku.length; i++) {
    System.out.print(sudoku[i][j] + " ");
   }
   System.out.println("");
  }
 
  System.out.println("");
  System.out.println("");
 
 }
}