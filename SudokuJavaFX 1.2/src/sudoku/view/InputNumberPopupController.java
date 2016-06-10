package sudoku.view;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InputNumberPopupController {
	private PropertyChangeSupport support = new PropertyChangeSupport(this);
	@FXML
	private TextField eingabe;
	
	public void addListener(PropertyChangeListener p){
		support.addPropertyChangeListener(p);
	}
	private Stage stage;
	public void setStage(Stage stage){
		this.stage=stage;
	}
	
	@FXML
	private void handleAbsenden(){
		try{
			if(Integer.parseInt(eingabe.getText())<18 || Integer.parseInt(eingabe.getText())>80){
				throw new NumberFormatException();
			}
			support.firePropertyChange("InputNumber", 0, Integer.parseInt(eingabe.getText()));	
			stage.close();
		}
		catch(NumberFormatException e)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Fehler");
			alert.setContentText("Keinen Integer eingegeben. Bitte geben Sie einen Integer zwischen 18 und 80 ein.");
			alert.showAndWait();
		}
		}
	@FXML
	private void handleAbbrechen(){
		stage.close();
	}
	
	
}
