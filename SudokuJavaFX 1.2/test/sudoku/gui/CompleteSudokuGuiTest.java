package sudoku.gui;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import org.loadui.testfx.utils.FXTestUtils;

import application.Main;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import sudoku.MainApp;
import sudoku.MainAppInterface;
import sudoku.view.RectPos;

public class CompleteSudokuGuiTest{
	static GuiTest guiTest;
	
	BorderPane rootLayout;
	
	/*The widgets of the gui used for the tests */
	RectPos rect10;

	private Node file;

	private Node sudoku;

	private Node help;

	private Node about;
	public static MainAppInterface mainApp;
	
//	protected Parent getRootNode() {
//		Parent parent = null;
//        try {
//        	FXMLLoader loader = new FXMLLoader();
//			loader.setLocation(MainApp.class.getResource("view/Wrapper.fxml"));
//			rootLayout = (BorderPane) loader.load();
//			
//			// Load person overview.
//						FXMLLoader loader1 = new FXMLLoader();
//						loader1.setLocation(MainApp.class.getResource("view/Sudoku.fxml"));
//						AnchorPane sudoku = (AnchorPane) loader1.load();
//
//						// Set sudoku solver into the center of root layout.
//						rootLayout.setCenter(sudoku);
//			
//			parent = rootLayout;
//			return parent;
//        } catch (IOException ex) {
//           System.err.println("Es konnte kein Parent aus der Sudoku.fxml gezogen werden");
//        }
//        return parent;
//    }
	
	@BeforeClass
    public static void setUpClass() {
            FXTestUtils.launchApp(Main.class);
            mainApp = new MainApp();
            //here is that closure I talked about above, you instantiate the getRootNode abstract method
            //which requires you to return a 'parent' object, luckily for us, getRoot() gives a parent!
            //getRoot() is available from ALL Node objects, which makes it easy. 
            guiTest = new GuiTest() {
                @Override
                protected Parent getRootNode() {
                    return CompleteSudokuGuiTest.mainApp.getPrimaryStage().getScene().getRoot();
                }
            };


        }
	
	
	@Before
	public void setUp(){
		MainApp.main(null);
		file = guiTest.find("#file");
		sudoku = guiTest.find("#sudoku");
		help = guiTest.find("#help");
	}
	
	
	@Test
	public void markCell() {
		rect10 = guiTest.find("#r10");
		guiTest.click(rect10);
	}
	
	
}
