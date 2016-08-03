package sudoku.model;

import javafx.scene.shape.Rectangle;

/**
 * Diese Klasse erlaubt es, den Sudoku-Kästchen eine Position zuzuweisen.
 */
public class RectPos extends Rectangle {

	private int posX = -1;
	private int posY = -1;

	
	
	/**
	 * Setzt horizontale Position des Rechtecks
	 * @param pos - Größe der horizontalen Ausrichtung
	 */
	public void setPosX(int pos) {
		posX = pos;
	}

	/**
	 * Setzt vertikale Position des Rechtecks
	 * @param pos - Größe der vertikalen Ausrichtung
	 */
	public void setPosY(int pos) {
		posY = pos;
	}
	
	/**
	 * Gibt horizontale Position des Rechtecks aus
	 * @return Rechtecksposition x-Achse
	 */
	public int getPosX(){
		return posX;
	}
	
	/**
	 * Gibt vertikale Position des Rechtecks aus
	 * @return Rechtecksposition y-Achse
	 */	
	public int getPosY(){
		return posY;
	}

}
