

package sudoku.model;


public class SudokuGenerator {
	
	public static void main(String[] args) {
		generate();
	}

	public static Sudoku generate() {

		// IntArray mit leeren Feldern erzeugen
		int[][] arraySudoku = new int[9][9];

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {

				arraySudoku[i][j] = 0;

			}

		}

		Sudoku objectSudoku = new Sudoku(arraySudoku);
		int enteredFields = 0;

		while (enteredFields < 21) {

			int xKoord = (int) (Math.random() * 8);
			int yKoord = (int) (Math.random() * 8);
			int digit = (int) ((Math.random() * 8) + 1);

			// Wenn Feld frei ist
			if (arraySudoku[xKoord][yKoord] == 0) {

				arraySudoku[xKoord][yKoord] = digit;
				enteredFields++;

				// entspricht nicht den Sudoku Regeln
				if (!objectSudoku.setSudoku(arraySudoku)) {
					arraySudoku[xKoord][yKoord] = 0;
					enteredFields--;
				} else {

					objectSudoku.solve();

					// Ist so nicht lösbar -> zurücksetzen
					if (!objectSudoku.filled()) {
						arraySudoku[xKoord][yKoord] = 0;
						enteredFields--;

					}

				}

			}

		}

		return new Sudoku(arraySudoku);

	}

}
