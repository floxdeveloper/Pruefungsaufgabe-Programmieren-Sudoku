package sudoku.model;

import javafx.scene.shape.Rectangle;

/**
 * Diese Klasse erlaubt es, den Sudoku-Feldern eine Position zuzuweisen.
 * 
 * @author Tobias Berner, Yvette Labastille, William Riyadi, Florian St�ckl
 */
public class RectPos extends Rectangle {

	private int posX = -1;
	private int posY = -1;

	
	/**
	 * Setzt horizontale Position des Rechtecks.
	 * @param pos - Gr��e der horizontalen Ausrichtung
	 */
	public void setPosX(int pos) {
		posX = pos;
	}

	/**
	 * Setzt vertikale Position des Rechtecks.
	 * @param pos - Gr��e der vertikalen Ausrichtung
	 */
	public void setPosY(int pos) {
		posY = pos;
	}
	
	/**
	 * Gibt horizontale Position des Rechtecks zur�ck.
	 * @return Rechtecksposition x-Achse
	 */
	public int getPosX(){
		return posX;
	}
	
	/**
	 * Gibt vertikale Position des Rechtecks zur�ck.
	 * @return Rechtecksposition y-Achse
	 */	
	public int getPosY(){
		return posY;
	}
}
