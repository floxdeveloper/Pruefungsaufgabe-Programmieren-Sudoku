package sudoku.view;

import javafx.scene.shape.Rectangle;

public class RectPos extends Rectangle {

	private int posX = -1;

	private int posY = -1;

	public void setPosX(int pos) {

		posX = pos;
	}

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
