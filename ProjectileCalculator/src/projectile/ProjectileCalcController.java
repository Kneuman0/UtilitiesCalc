package projectile;

import java.text.DecimalFormat;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class ProjectileCalcController {

    @FXML
    private RadioButton zeroYrdsTog;

    @FXML
    private RadioButton muzzleMtrsPSecTog;

    @FXML
    private RadioButton calcDistYrdsTog;

    @FXML
    private Button calculateButton;

    @FXML
    private Label answerLabel;

    @FXML
    private TextField muzzleVelocText;

    @FXML
    private TextField zeroDistText;

    @FXML
    private ToggleGroup zeroDistance;

    @FXML
    private ToggleGroup calcDistance;

    @FXML
    private RadioButton muzzleFtPSecTog;

    @FXML
    private RadioButton calcDistMtrsTog;

    @FXML
    private TextField calcDistText;

    @FXML
    private RadioButton zeroMtrsTog;

    @FXML
    private ToggleGroup muzzleVelocity;
    
    @FXML
    private Label timeLabel;
    
    ProjectileUtility util;
    DecimalFormat inches;
    
    public void initialize(){
    	util = new ProjectileUtility();
    	inches = new DecimalFormat("0.000");
    }
    
    
    
    public void calcButtonListener(){
    	// convert shooters zero distance to meters
    	double zeroDistanceM = util.convertYardsToMeters(zeroMtrsTog.isSelected(), zeroDistText.getText());
    	// convert muzzle velocity to meters per second
    	double muzzleVelocityM = util.convertFeetToMeters(muzzleMtrsPSecTog.isSelected(), muzzleVelocText.getText());
    	// convert user's specified distance to meters
    	double calcDistanceM = util.convertYardsToMeters(calcDistMtrsTog.isSelected(), calcDistText.getText());
    	// angle of muzzle
    	double theta = util.getThetaUsingZero(zeroDistanceM, muzzleVelocityM);
    	// time it takes for bullet to reach zeroed distance
    	double timeElapsed = util.getTime(calcDistanceM, muzzleVelocityM, theta);
    	// height of bullet at user's 
    	double bulletHeight = util.getHeightOfBulletInInches(calcDistanceM, muzzleVelocityM, theta);
    	// sets the label indicating the time it takes the bullet to travel the user specified distance
    	timeLabel.setText(String.format("It will take %.2f miliseconds to travel %.1f %s", timeElapsed, calcDistanceM, 
    			util.getUnitsInMetersOrYards(calcDistMtrsTog.isSelected())));
    	// sets the label telling the user the height of the specified distance
    	answerLabel.setText(String.format("%.2f inches relative to zeroed distance", bulletHeight));
    	
    }

}
