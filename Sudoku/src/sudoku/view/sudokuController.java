package sudoku.view;

import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import sudoku.MainApp;
import sudoku.model.Sudoku;

public class sudokuController {

	private MainApp mainApp;
	private HashMap<Integer, Text> map = new HashMap<Integer, Text>();

	@FXML
	private Text t00,t01,t02,t03,t04,t05,t06,t07,t08,t10,t11,t12,t13,t14,t15,t16,t17,t18,t20,t21,t22,t23,t24,t25,t26,t27,t28,t30,t31,t32,t33,t34,t35,t36,t37,t38,t40,t41,t42,t43,t44,t45,t46,t47,t48,t50,t51,t52,t53,t54,t55,t56,t57,t58,t60,t61,t62,t63,t64,t65,t66,t67,t68,t70,t71,t72,t73,t74,t75,t76,t77,t78,t80,t81,t82,t83,t84,t85,t86,t87,t88;

	@FXML
	private GridPane gridpane;

	@FXML
	private void initialize() {
		
		initMap();
	
		
	}
	
	private void initMap(){
		
		//map puts	
				map.put(0, t00);
				map.put(1, t01);
				map.put(2, t02);
				map.put(3, t03);
				map.put(4, t04);
				map.put(5, t05);
				map.put(6, t06);
				map.put(7, t07);
				map.put(8, t08);
				map.put(9, t10);
				map.put(10, t11);
				map.put(11, t12);
				map.put(12, t13);
				map.put(13, t14);
				map.put(14, t15);
				map.put(15, t16);
				map.put(16, t17);
				map.put(17, t18);
				map.put(18, t20);
				map.put(19, t21);
				map.put(20, t22);
				map.put(21, t23);
				map.put(22, t24);
				map.put(23, t25);
				map.put(24, t26);
				map.put(25, t27);
				map.put(26, t28);
				map.put(27, t30);
				map.put(28, t31);
				map.put(29, t32);
				map.put(30, t33);
				map.put(31, t34);
				map.put(32, t35);
				map.put(33, t36);
				map.put(34, t37);
				map.put(35, t38);
				map.put(36, t40);
				map.put(37, t41);
				map.put(38, t42);
				map.put(39, t43);
				map.put(40, t44);
				map.put(41, t45);
				map.put(42, t46);
				map.put(43, t47);
				map.put(44, t48);
				map.put(45, t50);
				map.put(46, t51);
				map.put(47, t52);
				map.put(48, t53);
				map.put(49, t54);
				map.put(50, t55);
				map.put(51, t56);
				map.put(52, t57);
				map.put(53, t58);
				map.put(54, t60);
				map.put(55, t61);
				map.put(56, t62);
				map.put(57, t63);
				map.put(58, t64);
				map.put(59, t65);
				map.put(60, t66);
				map.put(61, t67);
				map.put(62, t68);
				map.put(63, t70);
				map.put(64, t71);
				map.put(65, t72);
				map.put(66, t73);
				map.put(67, t74);
				map.put(68, t75);
				map.put(69, t76);
				map.put(70, t77);
				map.put(71, t78);
				map.put(72, t80);
				map.put(73, t81);
				map.put(74, t82);
				map.put(75, t83);
				map.put(76, t84);
				map.put(77, t85);
				map.put(78, t86);
				map.put(79, t87);
				map.put(80, t88);
		
		
	}

	//
	private Text getKoordinate(int vert, int hor) {

		return map.get(vert * 9 + hor);

	}

	// Beim Klick auf Solve
	@FXML
	private void handleSolve() {
		
		Sudoku sudoku = new Sudoku(sudokuAuslesen());
		sudoku.starten();
		mainApp.setSudoku(sudoku);
		sudokuAnzeigen();

	}
	
	@FXML
	private void sudokuAnzeigen(){
		Text text;
		int[][] sudokuArray = mainApp.getSudoku().getSudoku();
		for(int i=0; i<9; i++){
			for(int j=0; j<9; j++){
				text = getKoordinate(i, j);
				if (sudokuArray[i][j]==0)
					text.setText(" ");
				else
				text.setText(Integer.toString(sudokuArray[i][j]));
			}
		}
		
		
	}
	
	private int[][] sudokuAuslesen(){
		
		Text text;
		int[][] sudokuArray;
		sudokuArray = mainApp.getSudoku().getSudoku();
		for(int i=0; i<9; i++){
			for(int j=0; j<9; j++){
				text = getKoordinate(i, j);
				String temp = text.getText();
				temp = temp.trim();
				if (temp.equals(""))
					sudokuArray[i][j]=0;
				else
				sudokuArray[i][j]=Integer.parseInt(temp);
			}
		}
		return sudokuArray;		
	}

	// Beim Klick auf Label (0/0)
	@FXML
	private void handleTextClick() {
		System.out.println("TEST");
		// String vergleich = l00.getText();
		//
		// l.setText("hi");
		//
		// if(vergleich.equals(""))
		// l00.setText("hallo");
		// else
		// l00.setText("");

	}
	

	// Beim Klick auf Reset
	@FXML
	private void handleReset() {

		Text text;
		int[][] sudokuArray = new int[9][9];		
		for(int i=0; i<9; i++){
			for(int j=0; j<9; j++){
				text = getKoordinate(i, j);
				sudokuArray[i][j]=0;
				text.setText(" ");
			}
		}

	}

	

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		sudokuAnzeigen();

	}

}
