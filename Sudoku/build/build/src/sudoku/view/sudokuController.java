package sudoku.view;

import java.util.HashMap;
import java.util.Optional;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import sudoku.MainApp;

public class sudokuController {

	private MainApp mainApp;
	private HashMap<Integer, Text> mapText = new HashMap<Integer, Text>();
	private HashMap<Integer, Rectangle> mapRect = new HashMap<Integer, Rectangle>();

	int auswahlX = -1;
	int auswahlY = -1;

	boolean editable = true;

	public final EventHandler<KeyEvent> keyEventHandler = new EventHandler<KeyEvent>() {
		public void handle(final KeyEvent keyEvent) {

			try {
				int eingabe = Integer.parseInt(keyEvent.getText());
				handleEingabe(eingabe);
			}

			catch (NumberFormatException e) {
				if (keyEvent.getCode() == KeyCode.DELETE || keyEvent.getCode() == KeyCode.SPACE
						|| keyEvent.getCode() == KeyCode.BACK_SPACE)
					handleEingabe(0);

			}
		}

	};

	@FXML
	private Text t00, t01, t02, t03, t04, t05, t06, t07, t08, t10, t11, t12, t13, t14, t15, t16, t17, t18, t20, t21,
			t22, t23, t24, t25, t26, t27, t28, t30, t31, t32, t33, t34, t35, t36, t37, t38, t40, t41, t42, t43, t44,
			t45, t46, t47, t48, t50, t51, t52, t53, t54, t55, t56, t57, t58, t60, t61, t62, t63, t64, t65, t66, t67,
			t68, t70, t71, t72, t73, t74, t75, t76, t77, t78, t80, t81, t82, t83, t84, t85, t86, t87, t88;

	@FXML
	private Rectangle r00, r01, r02, r03, r04, r05, r06, r07, r08, r10, r11, r12, r13, r14, r15, r16, r17, r18, r20,
			r21, r22, r23, r24, r25, r26, r27, r28, r30, r31, r32, r33, r34, r35, r36, r37, r38, r40, r41, r42, r43,
			r44, r45, r46, r47, r48, r50, r51, r52, r53, r54, r55, r56, r57, r58, r60, r61, r62, r63, r64, r65, r66,
			r67, r68, r70, r71, r72, r73, r74, r75, r76, r77, r78, r80, r81, r82, r83, r84, r85, r86, r87, r88;

	@FXML
	private GridPane gridpane;

	@FXML
	private void initialize() {

		initMaps();
		initializeEventHandler();
	}

	private void initMaps() {

		// Text map wird gefüllt
		mapText.put(0, t00);
		mapText.put(1, t01);
		mapText.put(2, t02);
		mapText.put(3, t03);
		mapText.put(4, t04);
		mapText.put(5, t05);
		mapText.put(6, t06);
		mapText.put(7, t07);
		mapText.put(8, t08);
		mapText.put(9, t10);
		mapText.put(10, t11);
		mapText.put(11, t12);
		mapText.put(12, t13);
		mapText.put(13, t14);
		mapText.put(14, t15);
		mapText.put(15, t16);
		mapText.put(16, t17);
		mapText.put(17, t18);
		mapText.put(18, t20);
		mapText.put(19, t21);
		mapText.put(20, t22);
		mapText.put(21, t23);
		mapText.put(22, t24);
		mapText.put(23, t25);
		mapText.put(24, t26);
		mapText.put(25, t27);
		mapText.put(26, t28);
		mapText.put(27, t30);
		mapText.put(28, t31);
		mapText.put(29, t32);
		mapText.put(30, t33);
		mapText.put(31, t34);
		mapText.put(32, t35);
		mapText.put(33, t36);
		mapText.put(34, t37);
		mapText.put(35, t38);
		mapText.put(36, t40);
		mapText.put(37, t41);
		mapText.put(38, t42);
		mapText.put(39, t43);
		mapText.put(40, t44);
		mapText.put(41, t45);
		mapText.put(42, t46);
		mapText.put(43, t47);
		mapText.put(44, t48);
		mapText.put(45, t50);
		mapText.put(46, t51);
		mapText.put(47, t52);
		mapText.put(48, t53);
		mapText.put(49, t54);
		mapText.put(50, t55);
		mapText.put(51, t56);
		mapText.put(52, t57);
		mapText.put(53, t58);
		mapText.put(54, t60);
		mapText.put(55, t61);
		mapText.put(56, t62);
		mapText.put(57, t63);
		mapText.put(58, t64);
		mapText.put(59, t65);
		mapText.put(60, t66);
		mapText.put(61, t67);
		mapText.put(62, t68);
		mapText.put(63, t70);
		mapText.put(64, t71);
		mapText.put(65, t72);
		mapText.put(66, t73);
		mapText.put(67, t74);
		mapText.put(68, t75);
		mapText.put(69, t76);
		mapText.put(70, t77);
		mapText.put(71, t78);
		mapText.put(72, t80);
		mapText.put(73, t81);
		mapText.put(74, t82);
		mapText.put(75, t83);
		mapText.put(76, t84);
		mapText.put(77, t85);
		mapText.put(78, t86);
		mapText.put(79, t87);
		mapText.put(80, t88);

		// Rechteck map wird gefüllt
		mapRect.put(0, r00);
		mapRect.put(1, r01);
		mapRect.put(2, r02);
		mapRect.put(3, r03);
		mapRect.put(4, r04);
		mapRect.put(5, r05);
		mapRect.put(6, r06);
		mapRect.put(7, r07);
		mapRect.put(8, r08);
		mapRect.put(9, r10);
		mapRect.put(10, r11);
		mapRect.put(11, r12);
		mapRect.put(12, r13);
		mapRect.put(13, r14);
		mapRect.put(14, r15);
		mapRect.put(15, r16);
		mapRect.put(16, r17);
		mapRect.put(17, r18);
		mapRect.put(18, r20);
		mapRect.put(19, r21);
		mapRect.put(20, r22);
		mapRect.put(21, r23);
		mapRect.put(22, r24);
		mapRect.put(23, r25);
		mapRect.put(24, r26);
		mapRect.put(25, r27);
		mapRect.put(26, r28);
		mapRect.put(27, r30);
		mapRect.put(28, r31);
		mapRect.put(29, r32);
		mapRect.put(30, r33);
		mapRect.put(31, r34);
		mapRect.put(32, r35);
		mapRect.put(33, r36);
		mapRect.put(34, r37);
		mapRect.put(35, r38);
		mapRect.put(36, r40);
		mapRect.put(37, r41);
		mapRect.put(38, r42);
		mapRect.put(39, r43);
		mapRect.put(40, r44);
		mapRect.put(41, r45);
		mapRect.put(42, r46);
		mapRect.put(43, r47);
		mapRect.put(44, r48);
		mapRect.put(45, r50);
		mapRect.put(46, r51);
		mapRect.put(47, r52);
		mapRect.put(48, r53);
		mapRect.put(49, r54);
		mapRect.put(50, r55);
		mapRect.put(51, r56);
		mapRect.put(52, r57);
		mapRect.put(53, r58);
		mapRect.put(54, r60);
		mapRect.put(55, r61);
		mapRect.put(56, r62);
		mapRect.put(57, r63);
		mapRect.put(58, r64);
		mapRect.put(59, r65);
		mapRect.put(60, r66);
		mapRect.put(61, r67);
		mapRect.put(62, r68);
		mapRect.put(63, r70);
		mapRect.put(64, r71);
		mapRect.put(65, r72);
		mapRect.put(66, r73);
		mapRect.put(67, r74);
		mapRect.put(68, r75);
		mapRect.put(69, r76);
		mapRect.put(70, r77);
		mapRect.put(71, r78);
		mapRect.put(72, r80);
		mapRect.put(73, r81);
		mapRect.put(74, r82);
		mapRect.put(75, r83);
		mapRect.put(76, r84);
		mapRect.put(77, r85);
		mapRect.put(78, r86);
		mapRect.put(79, r87);
		mapRect.put(80, r88);

	}

	//
	private Text getKoordinate(int vert, int hor) {

		return mapText.get(vert * 9 + hor);

	}

	public void setEditable(boolean wert) {
		editable = wert;

	}

	private void initializeEventHandler() {

		for (int i = 0; i < 81; i++) {
			mapRect.get(i).setOnMousePressed(new EventHandler<MouseEvent>() {

				String sourceIdString;
				int sourceX, sourceY = -1;

				public void handle(MouseEvent me) {
					if (editable) {
						sourceIdString = me.getSource().toString();
						try {
							sourceX = Integer.parseInt(sourceIdString.substring(sourceIdString.indexOf("id=") + 4,
									sourceIdString.indexOf("id=") + 5));
							sourceY = Integer.parseInt(sourceIdString.substring(sourceIdString.indexOf("id=") + 5,
									sourceIdString.indexOf("id=") + 6));
						} catch (Exception e) {
							System.err.println(e.getMessage());
							return;
						}

						// System.out.println("Rectangle " + sourceX + sourceY);
						//
						//
						// getKoordinate(sourceX, sourceY).setText("5");

						// sudokuAuslesen();

						select(sourceX, sourceY);

					}
				}
			});
		}
	}

	private void select(int sourceX, int sourceY) {

		if (sourceX == auswahlX && sourceY == auswahlY) {
			auswahlX = -1;
			auswahlY = -1;
			mapRect.get(9 * sourceX + sourceY).setStroke(Color.TRANSPARENT);

		} else {
			if (auswahlX != -1 && auswahlY != -1)
				mapRect.get(9 * auswahlX + auswahlY).setStroke(Color.TRANSPARENT);
			auswahlX = sourceX;
			auswahlY = sourceY;

			mapRect.get(9 * auswahlX + auswahlY).setStroke(Color.RED);

		}

	}

	// Beim Klick auf Solve
	@FXML
	private void handleSolve() {

		if (mainApp.getSudoku().filled())
			return;

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Solve Confirmation");
		alert.setHeaderText("Solve Sudoku?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {

			sudokuAuslesen();
			mainApp.getSudoku().starten();

			if (!mainApp.getSudoku().filled()) {
				mainApp.error("Unsolvable Sudoku", "The Sodoku you have entered is not solvable.");
			} else
				editable = false;

			// reset der Auwahl
			if (auswahlX != -1 && auswahlY != -1)
				select(auswahlX, auswahlY);

			sudokuAnzeigen();
		}

	}

	public void sudokuChanged() {

		sudokuAnzeigen();

	}

	@FXML
	private void sudokuAnzeigen() {
		Text text;
		int[][] sudokuArray = mainApp.getSudoku().getSudoku();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				text = getKoordinate(i, j);
				if (sudokuArray[i][j] == 0)
					text.setText(" ");
				else
					text.setText(Integer.toString(sudokuArray[i][j]));
			}
		}

	}

	private void handleEingabe(int eingabe) {
		if (eingabe < 10 && eingabe >= 0 && auswahlX != -1 && auswahlY != -1) {
			if (eingabe == 0) {
				getKoordinate(auswahlX, auswahlY).setText("");
			} else {
				getKoordinate(auswahlX, auswahlY).setText(Integer.toString(eingabe));
			}
			sudokuAuslesen();

			// zum zurücksetzen
			if (auswahlX != -1 && auswahlY != -1)
				select(auswahlX, auswahlY);

		}
	}

	// Gibt false zurück wenn das GUI Sudoku eine Regel verletzt -> lädt alten
	// Stand wieder
	private boolean sudokuAuslesen() {

		Text text;
		int[][] sudokuArray;
		sudokuArray = new int[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				text = getKoordinate(i, j);
				String temp = text.getText();
				temp = temp.trim();
				if (temp.equals(""))
					sudokuArray[i][j] = 0;
				else
					sudokuArray[i][j] = Integer.parseInt(temp);
			}
		}
		if (!mainApp.setSudoku(sudokuArray)) {
			// Alten Stand anzeigen
			sudokuAnzeigen();
			mainApp.warning("False input", "You entered a number that can´t be placed at this location");
			return false;
		}

		return true;
	}

	// Beim Klick auf Reset
	@FXML
	private void handleReset() {

		if (mainApp.getSudoku().empty())
			return;

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Reset Confirmation");
		alert.setHeaderText("Reset Sudoku?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			mainApp.getSudoku().sudokuReset();
			sudokuAnzeigen();
			editable = true;

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
