package sudoku.model;

import javafx.scene.shape.Rectangle;

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
	 * Setzt vertikale Positino des Rechtecks
	 * @param pos - Größe der vertikalen Ausrichtung
	 */
	public void setPosY(int pos) {
		posY = pos;
	}

	public int getPosX(){
		return posX;
	}
	
	public int getPosY(){
		return posY;
	}

}
