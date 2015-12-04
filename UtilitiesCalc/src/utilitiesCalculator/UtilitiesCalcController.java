package utilitiesCalculator;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class UtilitiesCalcController {

//	@FXML
//	private TableColumn<Tenant, String> tenantName;
//
//	@FXML
//	private TableColumn<Tenant, String> tenantColumn;
//
//	@FXML
//	private TableView<Tenant> table;

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

	ObservableList<String> tenants;

	DatabaseUtility dbUtil;

	public void initialize() {
		dbUtil = new DatabaseUtility();
//		// If these values will never be editied by the user we can keep them
//		// the way they are
//		// if we choose to make them editable, we should cue them up from the DB
//		// and add the changes to the DB
//
//
//		// Queue up Tenants ComboBox
		String allTenantsSQL = "SELECT * FROM tenant";
		ArrayList<Tenant> allTenants = dbUtil
				.fetchTenantSelection(allTenantsSQL);
		tenants = FXCollections.observableArrayList("");
		for (int i = 0; i < allTenants.size(); i++) {
			tenants.add(allTenants.get(i).getName());
		}
		this.tenantsList.setItems(tenants);

		// Queue up sublets ComboBox
		String allSubletTenants = "SELECT * FROM tenant WHERE tenantType = Sublet";
		ArrayList<Tenant> sublets = dbUtil
				.fetchTenantSelection(allSubletTenants);
		ObservableList<String> subs = FXCollections.observableArrayList("");
		for (int i = 0; i < sublets.size(); i++) {
			subs.add(sublets.get(i).getName());
		}
		this.subletTenantList.setItems(subs);
//		

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
	
	public void deleteTenantButtonListener(){
		
	}
	
	public void submitBillButtonListener(){
		
	}
	
	public void saveOccupancyButtonListener(){
		
	}
	
	public void printReceiptButtonListener(){
		
	}

}
