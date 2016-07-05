
package sudoku.model;

public class SudokuGenerator {

	
	private static boolean[] alreadyUsed = new boolean[9];

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
		int enteredFields = 0;
		
		long timestamp = System.currentTimeMillis();

		while (enteredFields < numberOfClues ) {
			
			System.out.println(System.currentTimeMillis());
			if (System.currentTimeMillis()-2000 > timestamp){
				System.out.println("restart");
				return generate(numberOfClues);
				
			}
			
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
						objectSudoku.solve();

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

	protected static int getNotTriedNumber() {

		int digit = 1 + (int) (Math.random()*9);	
		
		if (!alreadyUsed[digit-1]){
			
		alreadyUsed[digit-1]=true;
		return digit;
		}	
		return getNotTriedNumber();
	}
	
	private static void resetAlreadyUsed(){
		
		for (int i = 0; i < alreadyUsed.length; i++)
			alreadyUsed[i]=false;
		
		
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
