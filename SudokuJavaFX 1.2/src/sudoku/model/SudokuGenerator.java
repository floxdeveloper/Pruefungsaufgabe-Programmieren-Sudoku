
package sudoku.model;

public class SudokuGenerator {

	public static Sudoku generate(int numberOfClues) {

		// IntArray mit leeren Feldern erzeugen
		int[][] arraySudoku = new int[9][9];

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {

				arraySudoku[i][j] = 0;

			}

		}
		int[][] copy = copySudokuArray(arraySudoku);
		Sudoku objectSudoku = new Sudoku(arraySudoku);
		int enteredFields = 0;

		while (enteredFields < numberOfClues - 1) {

			int xKoord = (int) (Math.random() * 8);
			int yKoord = (int) (Math.random() * 8);
			int digit = (int) ((Math.random() * 8) + 1);

			// Wenn Feld frei ist -> muss eine Lösung haben, da es im Schritt davor lösbar war -> alle Zahlen durchprobieren (für Performance)
			if (arraySudoku[xKoord][yKoord] == 0) {

				boolean filledPos = false;

				while (!filledPos) {

					filledPos = true;
					arraySudoku[xKoord][yKoord] = digit;

					copy = copySudokuArray(arraySudoku);

					// entspricht nicht den Sudoku Regeln
					if (!objectSudoku.setSudoku(copy)) {
						digit = moduloHochzaehlen(digit);

						filledPos = false;
					} else {

						objectSudoku.solve();

						// Ist so nicht lösbar -> zurücksetzen
						if (!objectSudoku.filled()) {
							digit = moduloHochzaehlen(digit);
							filledPos = false;
						}

					}
				}
				
				enteredFields++;
			}

		}

		return new Sudoku(arraySudoku);

	}

	public static int moduloHochzaehlen(int zahl) {
		zahl--;
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
