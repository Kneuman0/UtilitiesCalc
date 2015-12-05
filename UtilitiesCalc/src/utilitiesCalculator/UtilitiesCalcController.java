package utilitiesCalculator;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class UtilitiesCalcController {

	// @FXML
	// private TableColumn<Tenant, String> tenantName;
	//
	// @FXML
	// private TableColumn<Tenant, String> tenantColumn;
	//
	// @FXML
	// private TableView<Tenant> table;

	@FXML
	private ComboBox<String> subletTenantList;

	@FXML
	private Button submitFTEbutton;

	@FXML
	private ComboBox<String> tenantTypeList;

	@FXML
	private Button deleteTenant;

	@FXML
	private TextField dateReceiptButton;

	@FXML
	private TextField numberOfRooms;

	@FXML
	private TextField billDate;

	@FXML
	private ComboBox<String> tenantsList;

	@FXML
	private Button submitBillAmount;

	@FXML
	private Button addTenantButton;

	@FXML
	private Button submitHouseInfo;

	@FXML
	private TextField billAmount;

	@FXML
	private Tab summaryTable;

	@FXML
	private TextField fte;

	@FXML
	private Tab tenantTab;

	@FXML
	private TextField nameTextBox;

	@FXML
	private Tab housingTab;

	@FXML
	private TextField addressInput;

	@FXML
	private TextField numberOfRooms1;

	@FXML
	private TextField otherBills;

	@FXML
	private TextField fossilFuelBill;

	@FXML
	private Button printReceiptButton;

	@FXML
	private ToggleGroup activeTenant;

	@FXML
	private RadioButton deactivateTenantRadioButton;
	
	@FXML
    private RadioButton activeTenantRadioButton;
	
	@FXML
    private ComboBox<String> activeTenants;
	
	@FXML
    private Button activateDeactivateButton;
	
	@FXML
    private Label userMessageLabelTenant;

	ObservableList<String> tenants;
	ObservableList<String> subs;
	DatabaseUtility dbUtil;

	public void initialize() {
		dbUtil = new DatabaseUtility();
		queueUpTenantTypesComboBox();
		queueUpTenantComboBox();
		queueUpSubletComboBox();

	}

	public void addTenantButtonListener() {
		// String tenant = nameTextBox.getText();
		// double sqftOfRoom = Double.parseDouble(this.roomSize.getText());
		// Tenant newTenant = new Tenant(tenant, sqftOfRoom);
		// // String addTenant = "SQL code to add new tenant";
		// // methodThatAddsANewTenantToDB();
		// tenants.add(newTenant);

		// DatabaseUtility addTenant = new DatabaseUtility();
		// addTenant.addTenant(nameTextBox.getText(), position, payRate, ID);

	}

	public void addHouseInfoListener() {
		// House house = new House(addressInput.getText(),
		// buildingType.getValue(),
		// Integer.parseInt(numberOfRooms.getText()),
		// Integer.parseInt(numOfTenants.getText()),
		// fullTimeBtn.isSelected());
		// dbUtil.addHouseInfo(house);

	}

	public void deleteTenantButtonListener() {
		String deleteTen = tenantsList.getValue();
		dbUtil.deleteTenant(deleteTen);
		userMessageLabelTenant.setText(String.format("%s has been deleted from the database!", deleteTen));
		tenantsList.getItems().setAll(FXCollections.observableArrayList());
		activeTenants.getItems().setAll(FXCollections.observableArrayList());
		subletTenantList.getItems().setAll(FXCollections.observableArrayList());
		queueUpSubletComboBox();
		queueUpTenantComboBox();
		
		}

	public void submitBillButtonListener() {

	}

	public void saveOccupancyButtonListener() {

	}

	public void printReceiptButtonListener() {

	}
	
	public void populateActiveStatus(){
		String getTenant = String.format("SELECT * FROM tenant WHERE name = '%s'",
				activeTenants.getValue());
		
		if(dbUtil.fetchTenantSelection(getTenant).get(0).isActive()){
			activeTenantRadioButton.setSelected(true);
		}else{
			deactivateTenantRadioButton.setSelected(true);
		}
	}
	
	public void activateDeactivateButtonListener(){
		String updateTenant = String.format("UPDATE tenant SET active = %b"
				+ " WHERE name = '%s'", this.activeTenantRadioButton.isSelected(),
				this.activeTenants.getValue());
		dbUtil.modifyDatabase(updateTenant);
	}
	
	
	
	
//----------------------------------controller utility methods-----------------------------------------------

	/**
	 * Loads all tenant names into the combo box that have tenant type of
	 * 'sublet'
	 */
	public void queueUpSubletComboBox() {
		
		String allSubletTenants = "SELECT * FROM tenant WHERE tenantType = 'Sublet'";
		ArrayList<Tenant> sublets = dbUtil
				.fetchTenantSelection(allSubletTenants);
		subs = FXCollections.observableArrayList();
		for (int i = 0; i < sublets.size(); i++) {
			subs.add(sublets.get(i).getName());
		}
		this.subletTenantList.setItems(subs);
	}

	/**
	 * Loads all tenant names into the tenant combo box
	 */
	public void queueUpTenantComboBox() {
		
		String allTenantsSQL = "SELECT * FROM tenant";
		ArrayList<Tenant> allTenants = dbUtil
				.fetchTenantSelection(allTenantsSQL);
		tenants = FXCollections.observableArrayList();
		for (int i = 0; i < allTenants.size(); i++) {
			tenants.add(allTenants.get(i).getName());
			}
		this.tenantsList.getItems().addAll(tenants);
		this.activeTenants.getItems().addAll(tenants);
	}

	/**
	 * Loads the constant values "Landlord", "Full Time Renter", "Sublet"
	 */
	private void queueUpTenantTypesComboBox() {
		ObservableList<String> tenTypesList = FXCollections
				.observableArrayList("Landlord", "Full Time Renter", "Sublet");
		
			this.tenantTypeList.getItems().addAll(tenTypesList);
		
//		this.tenantTypeList.setItems(tenTypesList);
		
	}

}
