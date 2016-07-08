package sudoku.model;

import java.io.Serializable;

public class Sudoku implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int[][] sudokuArray = new int[9][9];
	private int[][] sudokuSaved = new int[9][9];

	// Für solveIfInTime
	private long solveStarted = -1;
	private int timeToSolve;

	// Gefüllt nach solveCount (0 = unlösbar; 1 = einzigartig lösbar; 2 = nicht
	// eindeutig lösbar)
	protected int solveCounter;
	protected boolean fertig;

	/**
	 * Erzeugt ein neues Sudoku Objekt. Wenn array nicht Regeln entspricht wird
	 * leeres sudokuArray geladen.
	 * 
	 * @param array
	 */
	public Sudoku(int[][] array) {
		if (!setSudokuIfCorrect(array))
			sudokuReset();

	}

	/**
	 * Methode löst das Sudoku ohne Aussage über Anzahl der Lösungen zu treffen.
	 * Bei unlösbarem Sudoku ist sudoku danach in Zustand, wie vor. Nach zehn
	 * Sekunden kann man von nicht lösbar ausgehen. Methodenaufruf
	 */
	public void solve() {

		solveStarted = System.currentTimeMillis();
		timeToSolve = 10;

		fertig = false;
		sudokuBT();

		solveStarted = -1;
	}

	public void solveIfUnderOneSec() {

		solveStarted = System.currentTimeMillis();
		timeToSolve = 1;

		fertig = false;
		sudokuBT();

		solveStarted = -1;

	}

	/**
	 * Löst das sudokuArray mittels sudokuBTCount. Wenn Sudoku nicht lösbar ist
	 * ist es nach Methode unverändert. Nach 10 Sekunden wird Methode
	 * abgebrochen.
	 */
	public void solveCount() {
		solveStarted = System.currentTimeMillis();
		timeToSolve = 10;
		solveCounter = 0;
		fertig = false;
		sudokuBTCount();

		if (solveCounter > 0)
			sudokuArray = copyArray(sudokuSaved);

		solveStarted = -1;

	}

	public int getSolveCounter() {
		return solveCounter;
	}

	public int[][] getSudokuArray() {
		return sudokuArray;

	}

	/**
	 * Wenn sudokuArray den Regeln entspricht wird es gesetzt.
	 * 
	 * 
	 * @param array
	 * @return true wenn gesetzt; false sonst
	 */
	public boolean setSudokuIfCorrect(int[][] array) {
		if (checkIfCorrectSudoku(array)) {
			sudokuArray = array;
			return true;
		}
		return false;
	}

	/**
	 * Überprüft array auf Sudoku Regeln(richtige Größe; nur erlaubte Werte)
	 * 
	 * @param array
	 * @return true wenn es Regeln entspricht; false sonst
	 */
	public boolean checkIfCorrectSudoku(int[][] array) {
		// Prüft prinzipielle Groesse
		if (array.length != 9)
			return false;
		for (int i = 0; i < 9; i++) {
			if (array[i].length != 9)
				return false;
		}

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (array[i][j] > 9 || array[i][j] < 0)
					return false;
			}
		}

		boolean[] zahlVerfügbar = new boolean[9];

		// Prüft ob Zeilen (bis jetzt) nach Sudokuregeln gefüllt wurden
		for (int i = 0; i < 9; i++) {
			// macht alle Zahlen wieder verfügbar
			setBoolarrayAll(zahlVerfügbar, true);
			for (int j = 0; j < 9; j++) {
				int zahl = array[i][j];
				if (zahl != 0) {
					if (!zahlVerfügbar[zahl - 1])
						return false;
					else
						zahlVerfügbar[zahl - 1] = false;
				}
			}
		}

		// Prüft ob Spalten (bis jetzt) nach Sudokuregeln gefüllt wurden
		for (int i = 0; i < 9; i++) {
			// macht alle Zahlen wieder verfügbar
			setBoolarrayAll(zahlVerfügbar, true);
			for (int j = 0; j < 9; j++) {
				int zahl = array[j][i];
				if (zahl != 0) {
					if (!zahlVerfügbar[zahl - 1])
						return false;
					else
						zahlVerfügbar[zahl - 1] = false;
				}
			}
		}

		// Prüft ob die 9er- Kästchen (bis jetzt) nach Sudokuregeln gefüllt
		// wurden

		// Jede Anfangswerte fürs Kästchen
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				setBoolarrayAll(zahlVerfügbar, true);

				for (int a = i * 3; a < (i + 1) * 3; a++) {
					for (int b = j * 3; b < (j + 1) * 3; b++) {
						int zahl = array[a][b];
						if (zahl != 0) {
							if (!zahlVerfügbar[zahl - 1])
								return false;
							else
								zahlVerfügbar[zahl - 1] = false;
						}
					}
				}
			}
		}
		return true;

	}

	/**
	 * 
	 * Setzt alle Werte in boolArray auf value.
	 * 
	 * @param boolArray
	 * @param value
	 */
	private void setBoolarrayAll(boolean[] boolArray, boolean value) {
		for (int i = 0; i < boolArray.length; i++) {
			boolArray[i] = value;

		}

	}

	/**
	 * @param toCopy
	 * @return Neu erstelltes Array mit Werten aus toCopy.
	 */
	private int[][] copyArray(int[][] toCopy) {
		int[][] copied = new int[9][9];
		for (int k = 0; k < 9; k++) {
			for (int j = 0; j < 9; j++) {
				copied[k][j] = toCopy[k][j];
			}
		}
		return copied;

	}

	/**
	 * @return Kopie von sudokuArray
	 */
	public int[][] copySudokuArray() {

		return copyArray(sudokuArray);

	}

	/**
	 * Löst sudokuArray mithilfe von Backtracking. Stoppt bei erster Lösung. Bei
	 * keiner Lösung ist sudokuArray in Anfangszustand.
	 */
	private void sudokuBT() {

		int[] koord = getNextCoordinate();
		int xkoord = koord[0];
		int ykoord = koord[1];

		// beende BT
		if (fertig == true || (solveStarted != -1 && System.currentTimeMillis() > solveStarted + timeToSolve * 1000))
			return;
		// Kein Feld mehr frei, aber alles nach Regeln gelöst -> Sudoku gelöst
		else if (xkoord == -1) // nichts mehr auszufüllen -> fertig
			fertig = true;
		else { // backtracking

			for (int i = 1; i <= 9; i++) {
				if (isSafe(xkoord, ykoord, i)) {
					sudokuArray[xkoord][ykoord] = i;
					/*
					 * if (ausgefuellt()) ausgabe(); else
					 */
					sudokuBT();

					if (!fertig)
						sudokuArray[xkoord][ykoord] = 0;
				}
			}
		}
	}

	/**
	 * Löst sudokuArray mit Backtracking. Speichert die erste Lösung in
	 * sudokuSaved zwischen. Schaut ob es mindestens eine zweite Lösung gibt.
	 * Bricht dann ab. Füllt Count Variable.
	 */
	private void sudokuBTCount() {

		int[] koord = getNextCoordinate();
		int xkoord = koord[0];
		int ykoord = koord[1];

		// beende BT
		if (fertig == true && solveCounter > 1
				|| (solveStarted != -1 && System.currentTimeMillis() > solveStarted + timeToSolve * 1000))
			return;
		// Kein Feld mehr frei, aber alles nach Regeln gelöst -> Sudoku gelöst
		else if (xkoord == -1) // nichts mehr auszufüllen -> fertig
			if (fertig == false) {
				fertig = true;
				sudokuSaved = copyArray(sudokuArray);
				solveCounter++;
			} else {
				solveCounter++;
			}

		else { // backtracking

			for (int i = 1; i <= 9; i++) {
				if (isSafe(xkoord, ykoord, i)) {
					sudokuArray[xkoord][ykoord] = i;
					/*
					 * if (ausgefuellt()) ausgabe(); else
					 */
					sudokuBTCount();

					sudokuArray[xkoord][ykoord] = 0;
				}
			}
		}
	}

	private boolean isSafe(int hor, int ver, int zahl) { // Prüft ob eine Zahl
															// an
															// der Stelle
															// hor/ver
															// eingesetzt werden
															// darf

		for (int i = 0; i < sudokuArray.length; i++) {
			if (sudokuArray[hor][i] == zahl || sudokuArray[i][ver] == zahl)
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
				if (sudokuArray[i][j] == zahl)
					return false;
			}
		}
		return true;
	}

	public void sudokuReset() {

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				sudokuArray[i][j] = 0;
			}
		}

	}

	private int[] getNextCoordinate() {
		for (int j = 0; j < sudokuArray.length; j++) {
			for (int i = 0; i < sudokuArray.length; i++) {
				if (sudokuArray[j][i] == 0)
					return new int[] { j, i }; // nächste koordinate, die
												// probiert werden muss
			}
		}
		return new int[] { -1, -1 }; // nichts mehr auszufüllen -> fertig
	}

	public boolean empty() {
		for (int i = 0; i < sudokuArray.length; i++) {
			for (int j = 0; j < sudokuArray.length; j++) {
				if (sudokuArray[i][j] != 0)
					return false;
			}
		}
		return true;

	}

	public boolean filled() {
		for (int i = 0; i < sudokuArray.length; i++) {
			for (int j = 0; j < sudokuArray.length; j++) {
				if (sudokuArray[i][j] == 0)
					return false;
			}
		}
		return true;
	}

	// checkt, ob es zum gegebenen Sudoku eine einzigartige Loesung gibt
	public int checkUniqueSolvable() {

		int[][] temp = copyArray(sudokuArray);
		Sudoku tempSudoku = new Sudoku(temp);
		tempSudoku.solveCount();
		return tempSudoku.getSolveCounter();

	}
}
