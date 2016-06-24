package sudoku.model;

import java.io.Serializable;

public class Sudoku implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int[][] sudoku = new int[9][9];
	private int[][] sudokuSaved = new int[9][9];
	
	//Gef�llt nach solveCount (0 = unl�sbar; 1 = einzigartig l�sbar; 2 = nicht eindeutig l�sbar)
	private int solveCounter;
	private boolean fertig;

	public Sudoku(int[][] array) {

		// Versucht Sudoku nach regeln zu laden, wenn nicht -> leeres Sudoku
		// laden
		if (!setSudoku(array)) {

			sudokuReset();

		}
	}

	// Hauptmethode in Sudoku die zum L�sen ausgef�hrt wird -> sorgt daf�r, dass
	// ein teilweise gef�lltes sudoku korrekt gel�st wird oder im
	// Ausgangszustand bleibt (wenn nicht l�sbar)
	public void solve() {
		
		fertig = false;
		sudokuBT();
	
		
	}
	
	
	//L�st das Sudoku und bef�llt die Count Variable mit: 0 = unl�sbar; 1 = einzigartig l�sbar; 2 = nicht eindeutig l�sbar
	public void solveCount() {
		solveCounter=0;
		fertig = false;
		sudokuBTCount();
		if (solveCounter > 0)
			sudoku = copyArray(sudokuSaved);
		
	}
	public int getSolveCounter(){
		return solveCounter;
	}
	
	
	public int[][] getSudokuArray() {
		return sudoku;

	}

	public boolean setSudoku(int[][] array) {

		boolean[] zahlVerf�gbar = new boolean[9];

		// Pr�ft ob Zeilen (bis jetzt) nach Sudokuregeln gef�llt wurden
		for (int i = 0; i < 9; i++) {
			// macht alle Zahlen wieder verf�gbar
			setBoolarrayAll(zahlVerf�gbar, true);
			for (int j = 0; j < 9; j++) {
				int zahl = array[i][j];
				if (zahl != 0) {
					if (!zahlVerf�gbar[zahl - 1])
						return false;
					else
						zahlVerf�gbar[zahl - 1] = false;
				}
			}
		}

		// Pr�ft ob Spalten (bis jetzt) nach Sudokuregeln gef�llt wurden
		for (int i = 0; i < 9; i++) {
			// macht alle Zahlen wieder verf�gbar
			setBoolarrayAll(zahlVerf�gbar, true);
			for (int j = 0; j < 9; j++) {
				int zahl = array[j][i];
				if (zahl != 0) {
					if (!zahlVerf�gbar[zahl - 1])
						return false;
					else
						zahlVerf�gbar[zahl - 1] = false;
				}
			}
		}

		// Pr�ft ob die 9er- K�stchen (bis jetzt) nach Sudokuregeln gef�llt
		// wurden

		// Jede Anfangswerte f�rs K�stchen
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				setBoolarrayAll(zahlVerf�gbar, true);

				for (int a = i * 3; a < (i + 1) * 3; a++) {
					for (int b = j * 3; b < (j + 1) * 3; b++) {
						int zahl = array[a][b];
						if (zahl != 0) {
							if (!zahlVerf�gbar[zahl - 1])
								return false;
							else
								zahlVerf�gbar[zahl - 1] = false;

						}

					}

				}

			}
		}

		sudoku = array;
		return true;
	}

	private boolean[] setBoolarrayAll(boolean[] boolArray, boolean wert) {
		for (int i = 0; i < boolArray.length; i++) {
			boolArray[i] = wert;

		}
		return boolArray;

	}

	
	
	

	
	private int[][] copyArray(int[][] toCopy){
		int[][] copied = new int[9][9];
		for(int k=0;k<9;k++){
			for(int j=0;j<9;j++){
				copied[k][j]=toCopy[k][j];
			}
		}
		return copied;
		
	}
	
	//Gibt kopiertes SudokuArray zur�ck
	public int[][] copySudokuArray(){
		
		return copyArray(sudoku);
		
		
	}
	
	private void sudokuBT() {

		int[] koord = getNextCoordinate();
		int xkoord = koord[0];
		int ykoord = koord[1];

		// beende BT
		if (fertig == true )
			return;
		// Kein Feld mehr frei, aber alles nach Regeln gel�st -> Sudoku gel�st
		else if (xkoord == -1) // nichts mehr auszuf�llen -> fertig
			fertig = true;		
		else { // backtracking

			for (int i = 1; i <= 9; i++) {
				if (isSafe(xkoord, ykoord, i)) {
					sudoku[xkoord][ykoord] = i;
					/*
					 * if (ausgefuellt()) ausgabe(); else
					 */
					sudokuBT();

						if(!fertig)
						sudoku[xkoord][ykoord] = 0;
				}
			}
		}
	}

	private void sudokuBTCount() {
	
		int[] koord = getNextCoordinate();
		int xkoord = koord[0];
		int ykoord = koord[1];
	
		// beende BT
		if (fertig == true && solveCounter>1)
			return;
		// Kein Feld mehr frei, aber alles nach Regeln gel�st -> Sudoku gel�st
		else if (xkoord == -1) // nichts mehr auszuf�llen -> fertig
			if(fertig == false)
			{
				fertig = true;
				sudokuSaved = copyArray(sudoku);
				solveCounter++;
			}
			else{
				solveCounter++;
			}
			
		else { // backtracking
	
			for (int i = 1; i <= 9; i++) {
				if (isSafe(xkoord, ykoord, i)) {
					sudoku[xkoord][ykoord] = i;
					/*
					 * if (ausgefuellt()) ausgabe(); else
					 */
					sudokuBTCount();
	
					
						sudoku[xkoord][ykoord] = 0;
				}
			}
		}
	}

	private boolean isSafe(int hor, int ver, int zahl) { // Pr�ft ob eine Zahl
															// an
															// der Stelle
															// hor/ver
															// eingesetzt werden
															// darf

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

	public void sudokuReset() {

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				sudoku[i][j] = 0;
			}
		}

	}

	private int[] getNextCoordinate() {
		for (int j = 0; j < sudoku.length; j++) {
			for (int i = 0; i < sudoku.length; i++) {
				if (sudoku[j][i] == 0)
					return new int[] { j, i }; // n�chste koordinate, die
												// probiert werden muss
			}
		}
		return new int[] { -1, -1 }; // nichts mehr auszuf�llen -> fertig
	}

	public boolean empty() {
		for (int i = 0; i < sudoku.length; i++) {
			for (int j = 0; j < sudoku.length; j++) {
				if (sudoku[i][j] != 0)
					return false;
			}
		}
		return true;

	}

	public boolean filled() {
		for (int i = 0; i < sudoku.length; i++) {
			for (int j = 0; j < sudoku.length; j++) {
				if (sudoku[i][j] == 0)
					return false;
			}
		}
		return true;
	}

	
	// checkt, ob es zum gegebenen Sudoku eine einzigartige Loesung gibt
	public boolean checkUniqueSolvable(){
		int[][] temp = copyArray(sudoku);
		Sudoku tempSudoku = new Sudoku(temp);
		tempSudoku.solveCount();
		if(tempSudoku.getSolveCounter()!=1)
			return false;
		else 
			return true;
	}
}
