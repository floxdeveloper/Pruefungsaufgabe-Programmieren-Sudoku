package sudoku.gui;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.loadui.testfx.GuiTest;

import javafx.collections.ObservableMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import sudoku.MainApp;
import sudoku.view.RectPos;

public class WrapperGuiTest extends GuiTest{
	/*The widgets of the gui used for the tests */
	BorderPane rootLayout;
	RectPos rect10;
	Node file;
	Node sudoku;
	Node help;
	Node about;
	
	@Override
	protected Parent getRootNode() {
		Parent parent = null;
		
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/Wrapper.fxml"));
			rootLayout = (BorderPane) loader.load();
			parent = rootLayout;
			return parent;
		}catch (IOException ex){
			System.err.println("Der Wrapper konnte nicht aus Wrapper.fxml gezogen werden" );	
		}
		return parent;
	}
	
	@Before
	public void setUp(){
		rootLayout = find("#rootLayout");
		file = find("#file");
		sudoku = find("#sudoku");
		help = find("#help");
	}
	
	@Test
	public void clickAllMenuItems(){
		//All Menus are selected and checked, if their MenuItems can be found by id
		
		click(file);
		
		click(sudoku);
		click(help);
		about = find("#about");
		
	}
	
	
	
	
}
