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
		try {
			// loads resources when inside runnable jar
			parent = FXMLLoader.load(this.getClass().getResource("/resources/UtilitiesCalculatorGUI.fxml"));
			
		} catch (RuntimeException e) {
			System.out.println("Trying path in IDE");
			try {
				// loads resources when opened inside IDE
				parent = FXMLLoader.load(getClass().getResource("/UtilitiesCalculatorGUI.fxml"));
			} catch (IOException e1) {
				System.out.println("error in parent declaration");
				e1.printStackTrace();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
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


