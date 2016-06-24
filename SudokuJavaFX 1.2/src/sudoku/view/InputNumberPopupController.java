package sudoku.view;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sudoku.MainApp;
import sudoku.MainAppTest;

public class InputNumberPopupController {
	
	private MainApp mainApp;
	private PropertyChangeSupport support = new PropertyChangeSupport(this);
	
	@FXML
	private ComboBox<Integer> eingabecb;
	
	

	@FXML
	private void lockScreen(){
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainAppTest.class.getResource("view/WrapperLock.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initStyle(StageStyle.TRANSPARENT);
			dialogStage.initOwner(mainApp.getPrimaryStage());
			dialogStage.setResizable(false);
			dialogStage.setAlwaysOnTop(true);
			dialogStage.setOpacity(0.9);
			Scene scene = new Scene(pane);
			dialogStage.setScene(scene);
			 
			//set Stage boundaries to the lower right corner of the visible bounds of the main screen
			dialogStage.setHeight(mainApp.getPrimaryStage().getHeight());
			dialogStage.setWidth(mainApp.getPrimaryStage().getWidth());
			dialogStage.setX(mainApp.getPrimaryStage().getX());
			dialogStage.setY(mainApp.getPrimaryStage().getY());

			// Show the dialog and wait until the user closes it
			dialogStage.show();
			

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		//TODO auf Englisch ändern 
	/**
	 * 
	 */
	@FXML
	private void initialize(){
		
		//Sets Item of ComboBox
		ObservableList<Integer> a1 = FXCollections.observableArrayList();	
		for(int i = 20;i<81;i++){
		a1.add(i);
		}
		eingabecb.setVisibleRowCount(10);
		eingabecb.setItems(a1);
	
	
	}
	
	/**
	 * Adds a PropertyChangeListener to the PropertyChangeSupport
	 * @param p
	 */
	public void addListener(PropertyChangeListener p){
		support.addPropertyChangeListener(p);
	}
	
	
	private Stage stage;
	public void setStage(Stage stage){
		this.stage=stage;
	}
	
	@FXML
	private void handleAbsenden(){
			//TODO Bei nicht ausgewählter Zahl -> Exception (fixen)
			try{
			support.firePropertyChange("InputNumber", 0, (int) eingabecb.getValue());	
			stage.close();
			lockScreen();
			}catch (Exception noNumberOfHintsSelectedException){
				
				mainApp.warning("Please select a number", "You have not selected a number of hints");
				
			}
			
		}
	
	
	/**
	 * Sets the MainApp Reference
	 * @param m
	 */
	public void setMainApp(MainApp m){	
		mainApp=m;
	}
	
	/**
	 * Handles the user's choice to break
	 */
	@FXML
	private void handleAbbrechen(){
		stage.close();
	}
	
	
}