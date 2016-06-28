package sudoku.gui;

import java.io.IOException;

import org.junit.Test;
import org.loadui.testfx.GuiTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import sudoku.MainApp;
import sudoku.view.RectPos;

public class SudokuGuiTest extends GuiTest{
	AnchorPane rootLayout;
	
	/*The widgets of the gui used for the tests */
	RectPos rect10;
	
	protected Parent getRootNode() {
        Parent parent = null;
        try {
        	FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/Sudoku.fxml"));
			rootLayout = (AnchorPane) loader.load();
			parent = rootLayout;
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
