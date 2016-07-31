
package sudoku.model;

public class SudokuGenerator {

	protected static boolean[] alreadyUsed = new boolean[9];
	
	/**
	 * Gibt Zahl zurück, die noch nicht seit dem Reset von alreadyUsed rurückgegeben wurde.
	 * @return int
	 */
	protected static int getNotTriedNumber() {
		//Um Fehler zu vermeiden, wenn alle Zahlen schon verwendet wurden
		boolean error = true;
		for (int i = 0; i < alreadyUsed.length; i++){
			if (alreadyUsed[i]==false)
				error = false;
		}
		if (error)
			return -1;
		
		int digit = 1 + (int) (Math.random()*9);	
		if (!alreadyUsed[digit-1]){
		alreadyUsed[digit-1]=true;
		return digit;
		}	
		return getNotTriedNumber();
	}
	
	protected static void resetAlreadyUsed(){
		for (int i = 0; i < alreadyUsed.length; i++)
			alreadyUsed[i]=false;
	}
	
	
	
	/**
	 * Generiert ein Sudoku mit gefüllten Feldern, deren Anzahl numberOfClues entspricht.
	 * @param numberOfClues - Anzahl der gefüllten Felder
	 * @return Sudoku, das den Regeln entspricht und lösbar ist
	 */
	public static Sudoku generate(int numberOfClues) {		
		
		// IntArray mit leeren Feldern erzeugen
		int[][] arraySudoku = new int[9][9];
		fillArrayWith0(arraySudoku);
		
		int[][] copy = copySudokuArray(arraySudoku);
		Sudoku objectSudoku = new Sudoku(copy);
		int enteredFields = 0;

		while (enteredFields < numberOfClues ) {
			//Alle Zahlen sind für diesen Durchgang möglich
			resetAlreadyUsed();
			
			int xKoord = (int) Math.floor((Math.random() * 9));
			int yKoord = (int) Math.floor((Math.random() * 9));
			int digit = getNotTriedNumber();

			// Wenn Feld frei ist -> muss eine Lösung haben, da es im Schritt davor lösbar war -> alle Zahlen durchprobieren (für Performance)
			if (arraySudoku[xKoord][yKoord] == 0) {
				boolean filledPos = false;
				while (!filledPos) {
					filledPos = true;
					arraySudoku[xKoord][yKoord] = digit;				
					copy = copySudokuArray(arraySudoku);	
					
					// entspricht nicht den Sudoku Regeln -> nächste Zahl probieren
					if (!objectSudoku.setSudokuIfCorrect(copy)) {
						digit = getNotTriedNumber();
						filledPos = false;
					} else {
						//Prüft Lösbarkeit
						objectSudoku.solveIfUnderOneSec();
						// Ist so nicht lösbar -> nächste Zahl probieren
						if (!objectSudoku.filled()) {
							digit = getNotTriedNumber();
							filledPos = false;					
						}
					}	
					if(filledPos)
						enteredFields++;
				}	
			}
		}
		return new Sudoku(arraySudoku);
	}
	
	
	/**
	 * Füllt ein Array der Größe 9x9 mit Nullen
	 * 
	 * @param array
	 */
	private static void fillArrayWith0(int[][] array){	
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				array[i][j] = 0;
			}
		}
	}

	
	
	

	/**
	 * Gibt neu erzeugtes Feld mit Inhalt von array zurück.
	 * 
	 * @param array zweidimensionales Array
	 * @return kopiertes array
	 */
	private static int[][] copySudokuArray(int[][] array) {
		int[][] arrayret = new int[9][9];

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				arrayret[i][j] = array[i][j];
			}
		}
		return arrayret;
	}

}
