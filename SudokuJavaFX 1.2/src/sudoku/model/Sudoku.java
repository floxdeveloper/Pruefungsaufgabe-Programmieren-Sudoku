package sudoku.model;

import java.io.Serializable;
	/**
	 * Diese Klasse definiert den abstrakten Datentyp Sudoku und seine Operationen.
	 */
public class Sudoku implements Serializable {
	
	private static final long serialVersionUID = 1L;

	protected int[][] sudokuArray = new int[9][9];
	protected int[][] sudokuSaved = new int[9][9];

	// Wird nach solve aus unten stehenden Variablen gebildet
	private Solvability solvability = Solvability.notEvaluated;

	// Variablen für solve
	private long solveStarted = -1;
	private int timeToSolve;
	private boolean ranOutOfTime = false;
	/** 
	* Gefüllt nach solveCount (0 = unlösbar; 1 = einzigartig lösbar; 2 = nicht
	* eindeutig lösbar)
	*/
	protected int solveCounter;
	protected boolean fertig;
	
	/**
	 * Gibt den solveCounter aus.
	 * @return die Anzahl der möglichen Lösungen (2 = zwei oder mehr Lösungen).
	 */
	public int getSolveCounter() {
		return solveCounter;
	}
	
	/**
	 * Gibt das sudokuArray aus.
	 * @return das sudokuArray als Feld
	 */
	public int[][] getSudokuArray() {
		return sudokuArray;
	}
	
	/**
	 * Gibt als String aus, welche Art der Lösbarkeit vorhanden ist.
	 * @return die Art der Lösbarkeit in Form eines Strings
	 */
	public Solvability getSolvability() {
		return solvability;
	}
	
	/**
	 * Hilfsfunktion für sudokuBT und sudokuBTCount. Gibt die nächste 
	 * Koordinate aus, die ausprobiert werden muss.
	 * @return die nächste zu probierende Koordinate oder {-1, -1}, falls komplett durchprobiert
	 */
	private int[] getNextCoordinate() {
		for (int j = 0; j < sudokuArray.length; j++) {
			for (int i = 0; i < sudokuArray.length; i++) {
				if (sudokuArray[j][i] == 0)
					return new int[] { j, i }; // nächste Koordinate, die
												// probiert werden muss
			}
		}
		return new int[] { -1, -1 }; // nichts mehr auszufüllen -> fertig
	}
	
	/**
	 * Entspricht sudokuArray den Regeln, wird es gesetzt.
	 * 
	 * 
	 * @param array 9x9 Array, das Sudoku enthält
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
	 * 
	 * Setzt alle Werte in boolArray auf value.
	 * 
	 * @param boolArray
	 * @param value - true oder false
	 */
	private void setBoolArrayAll(boolean[] boolArray, boolean value) {
		for (int i = 0; i < boolArray.length; i++) {
			boolArray[i] = value;
		}
	}
	
	/**
	 * Erzeugt ein neues Sudoku-Objekt. Wenn array nicht Regeln entspricht wird
	 * leeres sudokuArray geladen.
	 * 
	 * @param array 9x9 Array, welches Sudoku enthält
	 */
	public Sudoku(int[][] array) {
		if (!setSudokuIfCorrect(array))
			sudokuReset();
	}

	/**
	 * Methode löst das Sudoku, ohne Aussage über Anzahl der Lösungen zu
	 * treffen. Bei unlösbarem Sudoku ist sudoku danach im vorigen Zustand. Nach
	 * zehn Sekunden kann man von Unlösbarkeit ausgehen.
	 */
	public void solve() {
		initMaxSolveTime(10);

		fertig = false;
		sudokuBT();
		solveStarted = -1;

		if (ranOutOfTime)
			solvability = Solvability.probablyNotSolvable;
		else if (isFilled())
			solvability = Solvability.solvable;
		else
			solvability = Solvability.notSolvable;
	}
	
	/**
	 * Das Sudoku muss in max. einer Sekunde gelöst werden. Die Lösbarkeit wird nicht evaluiert.
	 */
	public void solveIfUnderOneSec() {
		initMaxSolveTime(1);

		fertig = false;
		sudokuBT();
		solvability = Solvability.notEvaluated;
		solveStarted = -1;
	}

	private void initMaxSolveTime(int timeToSolve) {
		solveStarted = System.currentTimeMillis();
		this.timeToSolve = timeToSolve;
		ranOutOfTime = false;
	}

	/**
	 * Löst das sudokuArray mittels sudokuBTCount. Wenn das Sudoku nicht lösbar ist
	 * ist es nach Methode unverändert. Nach 10 Sekunden wird die Methode
	 * abgebrochen.
	 */
	public void solveCount() {
		initMaxSolveTime(10);
		solveCounter = 0;
		fertig = false;
		sudokuBTCount();

		if (solveCounter > 0)
			sudokuArray = copyArray(sudokuSaved);

		solveStarted = -1;

		if (ranOutOfTime)
			solvability = Solvability.probablyNotSolvable;
		else if (solveCounter == 0)
			solvability = Solvability.notSolvable;
		else if (solveCounter == 1)
			solvability = Solvability.uniquelySolvable;
		else if (solveCounter == 2)
			solvability = Solvability.notUniquelySolvable;
		else
			solvability = Solvability.notEvaluated;
	}

	/**
	 * Überprüft array auf Sudokuregeln (richtige Größe; nur erlaubte Werte).
	 * 
	 * @param array 9x9 Array, das zu überprüfendes Sudoku enthält
	 * @return true: wenn es Regeln entspricht; false: sonst
	 */
	public boolean checkIfCorrectSudoku(int[][] array) {
		// Prüft prinzipielle Groesse.
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

		// Prüft ob Zeilen (bis jetzt) nach Sudokuregeln gefüllt wurden.
		for (int i = 0; i < 9; i++) {
			// macht alle Zahlen wieder verfügbar
			setBoolArrayAll(zahlVerfügbar, true);
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

		// Prüft ob Spalten (bis jetzt) nach Sudokuregeln gefüllt wurden.
		for (int i = 0; i < 9; i++) {
			// macht alle Zahlen wieder verfügbar
			setBoolArrayAll(zahlVerfügbar, true);
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
		// wurden.
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				setBoolArrayAll(zahlVerfügbar, true);

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
	 * @param toCopy - 9x9 Array, das zu kopierendes Sudoku enthält
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
	 * Erzeugt eine referenzungebundene Kopie vom sudokuArray.
	 * @return eine Kopie von sudokuArray
	 */
	public int[][] copySudokuArray() {
		return copyArray(sudokuArray);
	}

	/**
	 * Löst sudokuArray mit Hilfe von Backtracking. Stoppt bei erster Lösung. 
	 * Im worst case liegt die Komplexität bei O(n!).
	 * Bei keiner Lösung bleibt sudokuArray im Anfangszustand.
	 */
	private void sudokuBT() {

		int[] koord = getNextCoordinate();
		int xkoord = koord[0];
		int ykoord = koord[1];

		// beende BT
		if (fertig == true)
			return;
		if (solveStarted != -1 && System.currentTimeMillis() > solveStarted + timeToSolve * 1000) {
			ranOutOfTime = true;
			return;
		}

		// Kein Feld mehr frei, aber alles nach Regeln gelöst -> Sudoku gelöst
		else if (xkoord == -1) // nichts mehr auszufüllen -> fertig
			fertig = true;
		else { // backtracking
			for (int i = 1; i <= 9; i++) {
				if (isSafe(xkoord, ykoord, i)) {
					sudokuArray[xkoord][ykoord] = i;
					sudokuBT();

					if (!fertig)
						sudokuArray[xkoord][ykoord] = 0;
				}
			}
		}
	}

	/**
	 * Löst sudokuArray mit Backtracking und speichert die erste Lösung in
	 * sudokuSaved zwischen. Schaut danach, ob es mindestens eine zweite Lösung gibt 
	 * und bricht dann ab. Füllt Count-Variable.
	 * Komplexität des Algorithmus liegt wieder bei O(n!), 
	 * die durchschnittliche Laufzeit übersteigt allerdings die von sudokuBT
	 */
	private void sudokuBTCount() {

		int[] koord = getNextCoordinate();
		int xkoord = koord[0];
		int ykoord = koord[1];

		// beende BT
		if (fertig == true && solveCounter > 1)
			return;
		if (solveStarted != -1 && System.currentTimeMillis() > solveStarted + timeToSolve * 1000) {
			ranOutOfTime = true;
			return;
		}
		// Kein Feld mehr frei, aber alles nach Regeln gelöst -> Sudoku gelöst
		else if (xkoord == -1){ // nichts mehr auszufüllen -> fertig
			if (fertig == false) {
				fertig = true;
				sudokuSaved = copyArray(sudokuArray);
			}
			solveCounter++;
		}
		else { // backtracking
			for (int i = 1; i <= 9; i++) {
				if (isSafe(xkoord, ykoord, i)) {
					sudokuArray[xkoord][ykoord] = i;
					sudokuBTCount();
					sudokuArray[xkoord][ykoord] = 0;
				}
			}
		}
	}

	/**
	 * Prüft, ob eine Zahl an der Stelle hor/ver eingesetzt werden darf.
	 * @param hor - Horizontaler Wert des zu überprüfenden SudokuArrays
	 * @param ver - Vertikaler Wert des zu überprüfenden SudokuArrays
	 * @param zahl - Die Zahl, die an der Stelle hor/ver eingetragen werden soll
	 * @return true - Zahl @Zahl darf eingesetzt werden, false - sonst
	 */
	private boolean isSafe(int hor, int ver, int zahl) { 

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

	/**
	 * Löscht alle Felder des Sudokus.
	 */
	public void sudokuReset() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				sudokuArray[i][j] = 0;
			}
		}
	}

	/**
	 * Überprüft, ob das Sudoku leer ist.
	 * @return true - Sudoku leer, false - sonst
	 */
	public boolean isEmpty() {
		for (int i = 0; i < sudokuArray.length; i++) {
			for (int j = 0; j < sudokuArray.length; j++) {
				if (sudokuArray[i][j] != 0)
					return false;
			}
		}
		return true;
	}

	
	/**
	 * Überprüft, ob das Sudoku vollständig gefüllt ist.
	 * @return true - Sudoku vollständig gefüllt, false - sonst
	 */
	public boolean isFilled() {
		for (int i = 0; i < sudokuArray.length; i++) {
			for (int j = 0; j < sudokuArray.length; j++) {
				if (sudokuArray[i][j] == 0)
					return false;
			}
		}
		return true;
	}

	/**Konrolliert, ob es zum gegebenen Sudoku eine einzigartige Lösung gibt.
	 * 
	 * @return uniquelySolvable oder notUniquelySolvable
	 */
	public Solvability checkUniqueSolvable() {
		int[][] temp = copyArray(sudokuArray);
		Sudoku tempSudoku = new Sudoku(temp);
		tempSudoku.solveCount();
		return tempSudoku.getSolvability();
	}
}
