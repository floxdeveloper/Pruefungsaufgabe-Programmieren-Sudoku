package sudoku.view;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class InputNumberPopupController {
	private PropertyChangeSupport support = new PropertyChangeSupport(this);
//	@FXML
//	private TextField eingabe;
	
	@FXML
	private ComboBox<Integer> eingabecb;
	
	
	//TODO auf Englisch ändern 
	
	@FXML
	private void initialize(){
		
		//Setzt Items von ComboBox
		ObservableList<Integer> a1 = FXCollections.observableArrayList();	
		for(int i = 20;i<81;i++){
		a1.add(i);
		}
		eingabecb.setVisibleRowCount(10);
		eingabecb.setItems(a1);
	
	
	}
	
	
	public void addListener(PropertyChangeListener p){
		support.addPropertyChangeListener(p);
	}
	private Stage stage;
	public void setStage(Stage stage){
		this.stage=stage;
	}
	
	@FXML
	private void handleAbsenden(){
			support.firePropertyChange("InputNumber", 0, (int) eingabecb.getValue());	
			stage.close();	
		}
	
	
	
	@FXML
	private void handleAbbrechen(){
		stage.close();
	}
	
	
}