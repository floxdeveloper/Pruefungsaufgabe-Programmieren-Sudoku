package sudoku.view;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RectPosTest {
	RectPos rectPos;
	int intX, intY;
	
	@Before
	public void setUp(){
		rectPos = new RectPos();
		intX = 50;
		intY = 30;
	}
	
	@Test
	public void setterAndGetterWithInt(){
		
		rectPos.setX(intX);
		rectPos.setY(intY);
		
		assertEquals(intX,rectPos.getX(),0);
		assertEquals(intY,rectPos.getY(),0);
	}
	
	
	
	
	

}
