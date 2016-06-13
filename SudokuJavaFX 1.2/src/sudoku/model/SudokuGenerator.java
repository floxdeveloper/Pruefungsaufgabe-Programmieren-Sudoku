
package sudoku.model;

public class SudokuGenerator {

	public static void main(String[] args) {
		generate(40);
	}
	
	
	
	public static Sudoku generate(int numberOfClues) {

		// IntArray mit leeren Feldern erzeugen
		int[][] arraySudoku = new int[9][9];

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {

				arraySudoku[i][j] = 0;

			}

		}
		
		int[][] copy = copySudokuArray(arraySudoku);
		Sudoku objectSudoku = new Sudoku(copy);
		int enteredFields = 1;

		while (enteredFields < numberOfClues ) {

			int xKoord = (int) (Math.random() * 8.99);
			int yKoord = (int) (Math.random() * 8.99);
			int digit = (int) ((Math.random() * 8.99) + 1);

			// Wenn Feld frei ist -> muss eine Lösung haben, da es im Schritt davor lösbar war -> alle Zahlen durchprobieren (für Performance)
			if (arraySudoku[xKoord][yKoord] == 0) {

				boolean filledPos = false;

				while (!filledPos) {

					filledPos = true;
					arraySudoku[xKoord][yKoord] = digit;

					//Um Referenz zu lösen
					objectSudoku.setSudoku(new int[9][9]);
					objectSudoku.sudokuReset();
					
					copy = copySudokuArray(arraySudoku);
			

					// entspricht nicht den Sudoku Regeln -> nächste Zahl probieren
					if (!objectSudoku.setSudoku(copy)) {
						digit = moduloHochzaehlen(digit);
						filledPos = false;
					} else {
						//Prüft Lösbarkeit
						objectSudoku.solve();

						// Ist so nicht lösbar -> nächste Zahl probieren
						if (!objectSudoku.filled()) {
							digit = moduloHochzaehlen(digit);
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

	public static int moduloHochzaehlen(int zahl) {
		zahl = (zahl+1) % 9;
		return zahl++;
	}

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
