package utilitiesCalculator;

import java.util.ArrayList;
import java.util.StringTokenizer;

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
	private TextField squareFootage;

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

	@FXML
	private RadioButton newTenantInactiveRadioButton;

	@FXML
	private RadioButton newTenantActiveRadioButton;

	@FXML
	private ToggleGroup initalTenantActive;

	ObservableList<String> tenants;
	ObservableList<String> subs;
	DatabaseUtility dbUtil;
	ArrayList<Tenant> utilityParticipants;

	public void initialize() {
		dbUtil = new DatabaseUtility();
		utilityParticipants = new ArrayList<Tenant>();

		queueUpTenantTypesComboBox();
		queueUpTenantComboBox();
		queueUpSubletComboBox();
		queueNONLandlordArrayList();

	}

	public void addTenantButtonListener() {
		Tenant tenant = new Tenant(nameTextBox.getText(),
				newTenantActiveRadioButton.isSelected(),
				tenantTypeList.getValue());
		dbUtil.addTenant(tenant);
		resetComboBoxes();

	}

	public void addHouseInfoListener() {
		House house = new House(addressInput.getText(),
				Integer.parseInt(squareFootage.getText()),
				Integer.parseInt(numberOfRooms.getText()));
		dbUtil.addHouseInfo(house);
	}

	/**
	 * Need to figure out how to delete all bills by this tenant as well
	 */
	public void deleteTenantButtonListener() {
		String deleteTen = tenantsList.getValue();

		dbUtil.deleteTenant(deleteTen);
		userMessageLabelTenant.setText(String.format(
				"%s has been deleted from the database!", deleteTen));
		resetComboBoxes();
	}

	/**
	 * Uses String.split (StringTokenizer)
	 */
	public void submitBillButtonListener() {
		utilityParticipants.add(new Sublet("Jake", true)); 
		queueAllTenantArrayList();
		
		saveBillMonth();
		
		double amtPerTen = getAmountPerTenant();
		ArrayList<BillPerTenant> thisMonthsTenants = new ArrayList<BillPerTenant>();
		for(int i = 0; i < utilityParticipants.size(); i++){
			double tenantBill = amtPerTen * utilityParticipants.get(i).getFte();
			thisMonthsTenants.add(new BillPerTenant(billMonthID(modifyBillDate()), houseID()
					, utilityParticipants.get(i).getFte(), utilityParticipants.get(i).getName()
					, tenantID(utilityParticipants.get(i).getName())));
		}
		
		dbUtil.addBillPerTenantEntry(thisMonthsTenants);
		
	}

	public void saveOccupancyButtonListener() {

		modifyTenantFTE();

	}

	public void printReceiptButtonListener() {

	}

	public void populateActiveStatus() {
		String getTenant = String.format(
				"SELECT * FROM tenant WHERE name = '%s'",
				activeTenants.getValue());

		if (dbUtil.fetchTenantSelection(getTenant).get(0).isActive()) {
			activeTenantRadioButton.setSelected(true);
		} else {
			deactivateTenantRadioButton.setSelected(true);
		}
	}

	public void activateDeactivateButtonListener() {
		String updateTenant = String.format("UPDATE tenant SET active = %b"
				+ " WHERE name = '%s'",
				this.activeTenantRadioButton.isSelected(),
				this.activeTenants.getValue());
		dbUtil.modifyDatabase(updateTenant);
	}

	// ----------------controller utility methods--------------------

	/**
	 * Loads all tenant names into the combo box that have tenant type of
	 * 'sublet'
	 */
	public void queueUpSubletComboBox() {

		String allSubletTenants = "SELECT * FROM tenant WHERE tenantType = 'Sublet'"
				+ " AND active = true";
		ArrayList<Tenant> sublets = dbUtil
				.fetchTenantSelection(allSubletTenants);
		subs = FXCollections.observableArrayList();
		for (int i = 0; i < sublets.size(); i++) {
			subs.add(sublets.get(i).getName());
			utilityParticipants.add(new Sublet("Jake", true));
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
	}

	public void resetComboBoxes() {
		tenantsList.getItems().setAll(FXCollections.observableArrayList());
		activeTenants.getItems().setAll(FXCollections.observableArrayList());
		subletTenantList.getItems().setAll(FXCollections.observableArrayList());
		utilityParticipants = null;
		queueNONLandlordArrayList();
		queueUpSubletComboBox();
		queueUpTenantComboBox();
	}

	/**
	 * Uses polymorphism
	 */
	public void queueNONLandlordArrayList() {
		String landlordQuery = "Select * FROM tenant WHERE tenantType NOT IN ('Landlord')"
				+ " AND active = true";
		for (int i = 0; i < dbUtil.fetchTenantSelection(landlordQuery).size(); i++) {
			utilityParticipants
					.add(new Sublet(dbUtil.fetchTenantSelection(landlordQuery)
							.get(i).getName(), true));
		}
	}

	/**
	 * Uses polymorphism
	 */
	public void queueAllTenantArrayList() {
		String landlordQuery = "Select * FROM tenant WHERE tenantType IN ('Landlord')"
				+ " AND active = true";
		for (int i = 0; i < dbUtil.fetchTenantSelection(landlordQuery).size(); i++) {
			utilityParticipants
					.add(new Landlord(dbUtil
							.fetchTenantSelection(landlordQuery).get(i)
							.getName(), true));
		}

	}

	public void modifyTenantFTE() {
		for (int i = 0; i < utilityParticipants.size(); i++) {
			if (utilityParticipants.get(i).getName()
					.equals(subletTenantList.getValue())) {
				utilityParticipants.get(i).setFte(
						Double.parseDouble(fte.getText()));
			}
		}

	}

	public double getAmountPerTenant() {
		double totalParticipateCoeff = 0;
		double totalBill = Double.parseDouble(fossilFuelBill.getText())
				+ Double.parseDouble(billAmount.getText())
				+ Double.parseDouble(otherBills.getText());
		for (int i = 0; i < utilityParticipants.size(); i++) {
			totalParticipateCoeff += utilityParticipants.get(i).getFte();
		}

		return totalBill / totalParticipateCoeff;
	}
	
	public void saveBillMonth(){
		
		//Saves bill month
		BillMonth billMonth = new BillMonth(modifyBillDate(), Double.parseDouble(
				fossilFuelBill.getText()), Double.parseDouble(billAmount.getText()),
						Double.parseDouble(otherBills.getText()));
		dbUtil.addBillingMonthEntry(billMonth);
	}
	
	public String modifyBillDate(){
		String[] date = billDate.getText().split("/");
		String dbDate = date[1] + "/" + date[0];
		return dbDate;
		
	}
	
	public int billMonthID(String date){
		String billMonthIDSQL = String.format("SELECT * FROM billMonth WHERE date = '%s'", date);
		return dbUtil.fetchBillMonth(billMonthIDSQL).get(0).getBillMonth_ID();
	}
	
	public int tenantID(String name){
		String tenantIDSQL = String.format("SELECT * FROM tenant WHERE date = '%s'", name);
		return dbUtil.fetchTenantSelection(tenantIDSQL).get(0).getTenant_ID();
	}
	
	public int houseID(){
		String houseIDSQL = "SELECT * FROM house";
		return dbUtil.fetchHouseSelection(houseIDSQL).get(0).getHouse_ID();
	}

}
