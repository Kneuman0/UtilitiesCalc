package utilitiesCalculator;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class UtilitiesCalcController {


//    @FXML
//    private TableColumn<Tenant, String> address;

    @FXML
    private ComboBox<String> waterHeater;

    @FXML
    private TextField numberOfRooms;

    @FXML
    private ToggleGroup availability;

    @FXML
    private ToggleGroup sharing;

    @FXML
    private Button submitHouseInfo;

//    @FXML
//    private TableColumn<Tenant, String> tenantColumn;

    @FXML
    private ComboBox<String> trashType;

    @FXML
    private RadioButton seasonalBtn;

    @FXML
    private Tab summaryTable;

//    @FXML
//    private TableColumn<Tenant, String> tenantName;

    @FXML
    private RadioButton fullTimeBtn;

    @FXML
    private TextField roomSize;

    @FXML
    private ComboBox<String> cooling;

    @FXML
    private ComboBox<String> stoveType;

    @FXML
    private TextField numOfTenants;

    @FXML
    private Tab tenantTab;

    @FXML
    private Tab housingTab;

    @FXML
    private ComboBox<String> watertype;

    @FXML
    private ComboBox<String> heating;

    @FXML
    private TextField addressInput;

    @FXML
    private TextField numberOfRooms1;

    @FXML
    private TableView<Tenant> table;

    @FXML
    private ComboBox<String> buildingType;
    
    @FXML
    private Button addTenantButton;
    
    @FXML
    private TextField nameTextBox;
    
    ObservableList<Tenant> tenants;
    
    public void initialize(){
    	//If these values will never be editied by the user we can keep them the way they are
    	// if we choose to make them editable, we should cue them up from the DB
    	// and add the changes to the DB
    	ObservableList<String> heatingOptions = FXCollections.observableArrayList(
    			"Wood", "Natural Gas", "Propane", "Oil/Boiler", "Heat Pump", "Electric Radiator",
    			"Geothermal", "None");
    	heating.setItems(heatingOptions);
    	ObservableList<String> coolingOptions = FXCollections.observableArrayList(
    			"Central Cooling", "Geothermal", "Local AC Unit", "Evaporative Cooling Unit");
    	cooling.setItems(coolingOptions);
    	ObservableList<String> waterSource = FXCollections.observableArrayList(
    			"Municipal", "Well Pump", "Bottled");
    	watertype.setItems(waterSource);
    	ObservableList<String> structures = FXCollections.observableArrayList(
    			"Condo", "Appartment", "House", "Bungalo", "Shed", "Dog House");
    	buildingType.setItems(structures);
    	ObservableList<String> stoveOptions = FXCollections.observableArrayList(
    			"Natural Gas", "Propane", "Wood Buring", "Electric");
    	stoveType.setItems(stoveOptions);
    	ObservableList<String> waterHeaterOptions = FXCollections.observableArrayList(
    			"Boiler", "Natural Gas", "Propane", "Electric");
    	waterHeater.setItems(waterHeaterOptions);
    	ObservableList<String> trashOptions = FXCollections.observableArrayList(
    			"municipal", "Commercial", "Personal");
    	trashType.setItems(trashOptions);
    	//Once database is created, queue up observable list of tenants here
    	tenants = FXCollections.observableArrayList();
//    	for(int i = 0; i < methodThatReturnsArrayListOfTenantFromDB.size(); i++){
//    		tenants.add(methodThatReturnsArrayListOfTenantFromDB.get(i));
//    	}
    	
    }
    
    public void addTenantButtonListener(){
    	String tenant =  nameTextBox.getText();
    	double sqftOfRoom = Double.parseDouble(this.roomSize.getText());
    	Tenant newTenant = new Tenant(tenant, sqftOfRoom);
//    	String addTenant = "SQL code to add new tenant";
//    	methodThatAddsANewTenantToDB();
    	tenants.add(newTenant);
    	
    }
    
    
    
    

}
