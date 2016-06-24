package sudoku.gui;

import java.io.IOException;

import org.junit.Test;
import org.loadui.testfx.GuiTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import sudoku.view.RectPos;

public class SudokuGuiTest extends GuiTest{
	
	/*The widgets of the gui used for the tests */
	RectPos rect10;
	
	protected Parent getRootNode() {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("Sudoku.fxml"));
            return parent;
        } catch (IOException ex) {
           System.err.println("Es konnte kein Parent aus der Sudoku.fxml gezogen werden");
        }
        return parent;
    }
	
	@Test
	public void markCell() {
		rect10 = find("#r10");
		click(rect10);
	}
}
