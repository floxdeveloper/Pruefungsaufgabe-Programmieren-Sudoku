package sudoku.view;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javafx.application.Application;
import javafx.stage.Stage;
import sudoku.MainApp;

public class SudokuControllerTest extends Application{
	SudokuController sudokuController;
	public static MainApp mainApp;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// noop
		
	}
	
	@BeforeClass
	public static void initJFX() {
	    Thread t = new Thread("JavaFX Init Thread") {
	        public void run() {
	            Application.launch(MainApp.class, new String[0]);
//	        	mainApp = new MainApp();
//	        	mainApp.main(null);
	        }
	    };
	    t.setDaemon(true);
	    t.start();
	}
	
	@Before
	public void setUp(){
		mainApp = new MainApp();
	}
	
	@Test
	public void testHandleEingabe(){		
		sudokuController = mainApp.getSudokuController();
		sudokuController.handleEingabe(8);
		for(int i=0; i<81; i++){
			System.out.println(sudokuController.mapText.get(i));
		}
	}

	
}
