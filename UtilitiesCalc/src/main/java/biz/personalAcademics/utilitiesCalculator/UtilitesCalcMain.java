package biz.personalAcademics.utilitiesCalculator;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UtilitesCalcMain extends Application{
	
	public void start(Stage stage) {
		
		Parent parent = null;
		try{
			
		parent =  FXMLLoader.load(this.getClass().getResource("/resources/UtilitiesCalculatorGUI.fxml"));
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(parent);

		// window title
		stage.setTitle("Utilities Calculator");
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * creates application in memory
	 * 
	 * @param args
	 */
		public static void main(String[] args) {
			launch(args);

		}

	}


