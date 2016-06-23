package sudoku.view;

import java.io.IOException;

import org.junit.Test;
import org.loadui.testfx.GuiTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class SudokuControllerTest extends GuiTest{
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
		RectPos rect10 = find("#r10");
		click(rect10);
	}
}
