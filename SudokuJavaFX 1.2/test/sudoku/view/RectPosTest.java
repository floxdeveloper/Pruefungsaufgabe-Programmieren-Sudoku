package sudoku.view;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RectPosTest {
	RectPos rectPos;
	int intX, intY;
	double doubleX, doubleY;
	String stringX, stringY;
	
	@Before
	public void setUp(){
		rectPos = new RectPos();
		intX = 50;
		intY = 30;
		doubleX = 50.5;
		doubleY = 30.3;
	}
	
	@Test
	public void setterAndGetterWithInt(){
		
		rectPos.setX(intX);
		rectPos.setY(intY);
		
		assertEquals(intX,rectPos.getX(),0);
		assertEquals(intY,rectPos.getY(),0);
	}
	
	@Test
	public void setterAndGetterWithDouble(){
		rectPos.setX(doubleX);
		rectPos.setY(doubleY);
		
		assertEquals(doubleX, rectPos.getX(),0);
		assertEquals(doubleY, rectPos.getY(),0);
	}
	
	
	

}
