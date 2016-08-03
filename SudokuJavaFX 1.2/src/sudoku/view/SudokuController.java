package sudoku.view;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sudoku.MainApp;
import sudoku.model.RectPos;
import sudoku.model.Solvability;
import sudoku.model.Sudoku;

/**
 * Diese Klasse verwaltet die GUI des Sudokus. Sie ist zuständig für die Interaktionen
 * des Benutzers mit dem Sudoku und verwaltet zudem die Editierbarkeit der Sudokufelder.
 */
public class SudokuController {

	private MainApp mainApp;
	protected HashMap<Integer, Text> mapText = new HashMap<Integer, Text>();
	protected HashMap<Integer, RectPos> mapRect = new HashMap<Integer, RectPos>();

	protected int auswahlX = -1;
	protected int auswahlY = -1;

	protected boolean[][] editableField = new boolean[9][9];

	private boolean editable = true;

	@FXML
	private Text t00, t01, t02, t03, t04, t05, t06, t07, t08, t10, t11, t12, t13, t14, t15, t16, t17, t18, t20, t21,
			t22, t23, t24, t25, t26, t27, t28, t30, t31, t32, t33, t34, t35, t36, t37, t38, t40, t41, t42, t43, t44,
			t45, t46, t47, t48, t50, t51, t52, t53, t54, t55, t56, t57, t58, t60, t61, t62, t63, t64, t65, t66, t67,
			t68, t70, t71, t72, t73, t74, t75, t76, t77, t78, t80, t81, t82, t83, t84, t85, t86, t87, t88;

	@FXML
	private RectPos r00, r01, r02, r03, r04, r05, r06, r07, r08, r10, r11, r12, r13, r14, r15, r16, r17, r18, r20, r21,
			r22, r23, r24, r25, r26, r27, r28, r30, r31, r32, r33, r34, r35, r36, r37, r38, r40, r41, r42, r43, r44,
			r45, r46, r47, r48, r50, r51, r52, r53, r54, r55, r56, r57, r58, r60, r61, r62, r63, r64, r65, r66, r67,
			r68, r70, r71, r72, r73, r74, r75, r76, r77, r78, r80, r81, r82, r83, r84, r85, r86, r87, r88;

	@FXML
	private GridPane gridpane;

	/**
	 * Gibt angefordertes Textfield zurück.
	 * 
	 * @param hor - Horizontaler Wert des Arrays
	 * @param vert - Vertikaler Wert des Arrays
	 * @return Textfield an Position (hor/vert)
	 */
	protected Text getKoordinate(int hor, int vert) {
		return mapText.get(hor * 9 + vert);	
	}

	/**
	 * Wird von MainApp aufgerufen und setzt den Verweis auf diese.
	 * Zusätzlich wird das Sudoku geladen und dann angezeigt.
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		sudokuAnzeigen();
	}

	/**
	 * Setzt generelle Editierbarkeit.
	 * 
	 * @param wert - true oder false
	 */
	public void setEditable(boolean wert) {
		editable = wert;
	}

	/**
	 * Zusammengefasste Initialisierungsmethode für Maps, EventHandler und die Editierbarkeit.
	 */
	@FXML
	protected void initialize() {
		initMaps();
		initEventHandler();
		allEditable();
	}

	/**
	 * Setzt alle aktuell editierbaren Felder zurück.
	 */
	public void resetNotLocked() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (editableField[i][j]) {
					Text t = getKoordinate(i, j);
					t.setText(" ");
					t.setFill(Color.BLACK);
					setEditable(true);
				}
			}
		}
		sudokuAuslesen();
	}

	/**
	 * Setzt die Editierbarkeit komplett zurück. Alles ist wieder editierbar.
	 * Alle Zahlen werden schwarz zur Visualisierung der Editierbarkeit.
	 */
	public void resetEditability() {
		colorAllBlack();
		setEditable(true);
		allEditable();
		unselect();
	}

	/**
	 * Setzt jedes Feld der Editierbarkeitsmatrix auf 'editierbar'.
	 */
	private void allEditable() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				editableField[i][j] = true;
			}
		}
	}

	/**
	 * Alle aktuell eingetragenen Felder werden in der Editierbarkeitsmatrix auf
	 * 'nicht editierbar' gesetzt. Diese Zahlen werden blau zur Visualisierung der
	 * Nicht-Editierbarkeit.
	 */
	public void lockEnteredFields() {
		unselect();
		setEditable(false);
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (!getKoordinate(i, j).getText().equals(" ")) {
					getKoordinate(i, j).setFill(Color.BLUE);
					editableField[i][j] = false;
				} else {
					// Wenn ein Feld nicht gelockt wird -> prinzipiell
					// editierbar
					editableField[i][j] = true;
					setEditable(true);
				}
			}
		}
	}

	/**
	 * Wenn ein Feld angewählt ist, wird eingabe an diese Position eingetragen.
	 * Ist es die letzte notwendige Eingabe, ist Sudoku gelöst und es wird
	 * zusätzlich überprüft, ob die Eingabe einer Sudokuzahl entspricht.
	 * 
	 * @param eingabe - vom Benutzer ins Feld eingegebene Zahl
	 */
	public void handleEingabe(int eingabe) {
		boolean alreadyFilled = mainApp.getSudoku().isFilled();

		if (eingabe < 10 && eingabe >= 0 && auswahlX != -1 && auswahlY != -1) {
			if (eingabe == 0) {
				getKoordinate(auswahlX, auswahlY).setText(" ");
			} else {
				getKoordinate(auswahlX, auswahlY).setText(Integer.toString(eingabe));
			}

			// Liest GUI aus und schreibt in Sudoku (bei falscher Eingabe wird
			// alter Stand geladen)
			sudokuAuslesen();
			unselect();

			// Wenn Sudoku gelöst ist, Congratulations ausgeben.
			if (mainApp.getSudoku().isFilled() && !alreadyFilled) {
				showCongratulation();
			}
		}
	}

	/**
	 * Zeigt den Congratulations-Bildschirm an.
	 */
	private void showCongratulation() {
		try {
			// Lädt fxml-Datei und erzeugt eine neue stage für das Popup.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/Congratulation.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			page.setId("CongratulationPage");
			// Erzeugt die dialogStage.
			Stage dialogStage = new Stage();
			dialogStage.initStyle(StageStyle.UTILITY);
			dialogStage.initOwner(mainApp.getPrimaryStage());
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Setzt controller auf die dialogStage.
			CongratulationController controller = loader.getController();
			controller.setStage(dialogStage);

			// Zeigt die dialogStage an, bis der Benutzer sie schließt.
			dialogStage.showAndWait();

			mainApp.getSudokuController().setEditable(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Bei Klick auf Reset wird das Sudoku auf leer zurückgesetzt.
	 */
	@FXML
	private void handleReset() {
		// Bei leerem Sudoku ist alert überflüssig.
		if (mainApp.getSudoku().isEmpty())
			return;

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Reset Confirmation");
		alert.setHeaderText("Reset Sudoku?");

		// Holt sich die Stage.
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();

		// Fügt das Logo hinzu.
		stage.getIcons().add(new Image("file:resources/images/sudoku.png"));

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			mainApp.getSudoku().sudokuReset();
			sudokuAnzeigen();
			editable = true;
			// Schwärzt alle Textfelder wieder ein und macht sie editierbar.
			resetEditability();
		}
	}

	/**
	 * Bei Klick auf Solve werden alle aktuell eingefüllten Felder gesperrt und
	 * uneditierbar gemacht. Das Sudoku wird gelöst und angezeigt.
	 */
	@FXML
	private void handleSolve() {
		if (mainApp.getSudoku().isFilled())
			return;

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Solve Confirmation");
		alert.setHeaderText("Solve Sudoku?");

		// Holt sich die Stage.
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();

		// Fügt das Logo hinzu.
		stage.getIcons().add(new Image("file:resources/images/sudoku.png"));

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			unselect();

			// Färbt User Input blau ein.
			lockEnteredFields();

			// Wenn nicht nach Sudoku Regeln: zeigt hier altes Sudoku an (kommt
			// nie vor da nach jeder Eingabe gecheckt wird)
			sudokuAuslesen();

			// Bei unlösbaren Sudokus stoppt Backtracking, ohne ein gefülltes
			// Sudoku zurück zu lassen. Danach kann abgefragt werden, ob es
			// geklappt hat
			Task<Void> task = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					mainApp.getSudoku().solve();
					return null;
				}
			};
			// Lockt den Screen, wenn der Thread läuft.
			task.setOnRunning(e -> {
				mainApp.lockScreen();
			});
			// Unlockt den Screen, wenn der Thread abgelaufen ist.
			task.setOnSucceeded(e -> {
				mainApp.unlockScreen();
				if (!mainApp.getSudoku().isFilled()) {
					// Farbe wieder auf schwarz ändern
					resetNotLocked();

					if (mainApp.getSudoku().getSolvability() == Solvability.notSolvable)
						mainApp.error("Definitely unsolvable Sudoku",
								"The Sudoku you have entered is definitely not solvable.");
					else if (mainApp.getSudoku().getSolvability() == Solvability.probablyNotSolvable)
						mainApp.error("Probably unsolvable Sudoku",
								"The Sudoku you have entered is highly unlikely solvable. We stopped trying.");
					else
						mainApp.error("Unsolvable Sudoku", "The Sudoku you have entered is not solvable.");
				} else {
					editable = false;
				}				
				//Lädt Sudoku in GUI (entweder gelöst oder bei unlösbarem Sudoku wie davor).
				sudokuAnzeigen();
			});

			task.setOnFailed(e -> {
				mainApp.unlockScreen();
				sudokuAnzeigen();
				mainApp.error("Unexpected error while solving.", "");
			});
			new Thread(task).start();
		}
	}

	/**
	 * Trägt bei lösbarem Sudoku einen zufälligen Hinweis ein. Bei unlösbarem
	 * oder vollem Sudoku bleibt Sudoku unverändert.
	 */
	@FXML
	private void handleHint() {
		// Wenn vollständig ausgefüllt, nichts machen.
		if (mainApp.getSudoku().isFilled())
			return;

		int[][] sudokuArray = mainApp.getSudoku().copySudokuArray();
		int[][] sudokuArraySolve = mainApp.getSudoku().copySudokuArray();

		Sudoku sudoku = new Sudoku(sudokuArraySolve);

		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				sudoku.solve();
				return null;
			}
		};

		task.setOnRunning(e -> {
			mainApp.lockScreen();
		});

		task.setOnSucceeded(e -> {
			mainApp.unlockScreen();
			// Sudoku nicht lösbar -> Error
			if (sudoku.isFilled()) {
				boolean hintGiven = false;

				while (!hintGiven) {
					int xKoord = (int) (Math.random() * 9);
					int yKoord = (int) (Math.random() * 9);

					if (sudokuArray[xKoord][yKoord] == 0) {
						sudokuArray[xKoord][yKoord] = sudokuArraySolve[xKoord][yKoord];
						mainApp.setSudoku(sudokuArray);
						hintGiven = true;
						if (mainApp.getSudoku().isFilled()) {
							setEditable(false);
							unselect();
						}
					}
				}
				mainApp.unlockScreen();
			} else {
				mainApp.unlockScreen();
				if (sudoku.getSolvability() == Solvability.notSolvable)
					mainApp.error("Unable to give a hint.", "The Sudoku you have entered is definitely not solvable.");
				else if (sudoku.getSolvability() == Solvability.probablyNotSolvable)
					mainApp.error("Unable to give a hint.", "The Sudoku you have entered is higly unlikely solvable.");
				else
					mainApp.error("Unable to give a hint.", "The Sudoku you have entered is not solvable.");
			}
		});

		task.setOnFailed(e -> {
			mainApp.unlockScreen();
			sudokuAnzeigen();
			mainApp.error("Unexpected error", "Could not give a hint");
		});
		new Thread(task).start();
	}

	/**
	 * Zeigt in der GUI das Sudoku an.
	 */
	public void sudokuAnzeigen() {
		Text text;
		int[][] sudokuArray = mainApp.getSudoku().getSudokuArray();
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

	/**
	 * Setzt, wenn GUI Sudoku den Regeln entspricht. Sonst wird GUI
	 * auf Sudoku zurückgesetzt.
	 * 
	 * @return true - ausgelesenes Sudoku entspricht den Regeln
	 * false - sonst
	 */
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

			mainApp.warning("False input.", "You entered a number that can´t be placed at this location.");
			return false;
		}
		return true;
	}

	/**
	 * Wählt aktuell selektiertes Feld ab.
	 */
	public void unselect() {
		if (auswahlX != -1 && auswahlY != -1)
			select(auswahlX, auswahlY);
	}

	/**
	 * Färbt alle Zahlen schwarz.
	 */
	public void colorAllBlack() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				Text t = getKoordinate(i, j);
				t.setFill(Color.BLACK);
			}
		}
	}

	/**
	 * Selektiert das Feld an Position (sourceX,sourceY). Wenn es bereits
	 * selektiert ist, wird es abgewählt.
	 * 
	 * @param sourceX - horizontale Position des Felds
	 * @param sourceY - vertikale Position des Felds
	 */
	public void select(int sourceX, int sourceY) {
		// Nicht editierbare Felder können nicht selektiert werden.
		if (!editableField[sourceX][sourceY]) {
			return;
		}

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

	/**
	 * Initialisiert die Text- und Rechteckmap.
	 */
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

	/**
	 * Weist jedem Rechteck einen MouseClickListener zu, der beim Klick das
	 * entsprechende Feld selektiert.
	 */
	private void initEventHandler() {
		for (int i = 0; i < 81; i++) {
			RectPos aktRect = mapRect.get(i);

			// Setzt bei jedem Rechteck X und Y Koordinate im Gridpane.
			aktRect.setPosX(i / 9);
			aktRect.setPosY(i % 9);

			// Initialisierung des Klick-EventHandlers auf Rechtecke.
			aktRect.setOnMousePressed(new EventHandler<MouseEvent>() {

				int sourceX, sourceY = -1;

				public void handle(MouseEvent me) {
					if (editable) {
						RectPos clicked = null;
						try {
							clicked = (RectPos) me.getSource();
						} catch (Exception e) {
							System.err.println(e.getMessage());
							return;
						}

						sourceX = clicked.getPosX();
						sourceY = clicked.getPosY();
						select(sourceX, sourceY);
					}
				}
			});
		}
	}
}
