package utilitiesCalculator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.StringTokenizer;

import javax.print.attribute.standard.MediaSize.Other;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class UtilitiesCalcController {

	@FXML
    private Tab summaryTable;
	
	@FXML
	private ToggleGroup summary;
	 
	@FXML
	private Label houseUserLabel;

	@FXML
	private ComboBox<String> addressComboBox;

	@FXML
	private ComboBox<String> subletTenantList;

	@FXML
	private Button submitFTEbutton;

	@FXML
	private ComboBox<String> tenantTypeList;

	@FXML
	private Button deleteTenant;

	@FXML
	private TextField dateReceiptTextField;

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
	private TextField fte;

	@FXML
	private TextField nameTextBox;

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

	@FXML
	private Button deleteHouseButton;

	@FXML
	private ComboBox<String> houseAddresses;

	@FXML
	private Label userLabelUtilCalc;
	
	@FXML
    private RadioButton billTotRadioButton;
	
	@FXML
    private RadioButton houseSumRadioButton;
	
	@FXML
    private RadioButton tenSumRadioButton;
	
	@FXML
    private TextArea summaryTextArea;
	
	@FXML
    private TextField ReceiptViewDateTextField;

    @FXML
    private ComboBox<?> receiptViewAddressComboBox;
    
    @FXML
    private RadioButton receiptViewRadioButton;
    
    @FXML
    private Button viewReceiptButton;

	ObservableList<String> tenants;
	ObservableList<String> subs;
	ObservableList<String> addresses;
	DatabaseUtility dbUtil;
	ArrayList<Tenant> utilityParticipants;
	

	/**
	 * Tested/working
	 */
	public void initialize() {
		dbUtil = new DatabaseUtility();
		utilityParticipants = new ArrayList<Tenant>();
		queueUpTenantTypesComboBox();
		queueUpTenantComboBox();
		queueUpSubletComboBox();
		queueUpAddressComboBox();
		queueNONLandlordArrayList();
//		summaryTextArea.setText();
	}

	/**
	 * Tested/working
	 */
	public void addTenantButtonListener() {
		if (ensureAllEntriesLoggedTenant()) {
			return;
		}
		Tenant tenant = new Tenant(nameTextBox.getText(),
				newTenantActiveRadioButton.isSelected(),
				tenantTypeList.getValue());
		dbUtil.addTenant(tenant);
		userMessageLabelTenant.setText(String.format("%s has been added!",
				tenant));
		resetComboBoxes();
	}

	/**
	 * Tested/working
	 */
	public void addHouseInfoListener() {
		if (ensureAllEntriesLoggedHouseInfo()) {
			return;
		}
		House house = null;
		try {
			house = new House(addressInput.getText(),
					Integer.parseInt(squareFootage.getText()),
					Integer.parseInt(numberOfRooms.getText()));
		} catch (NumberFormatException e) {
			houseUserLabel
					.setText("Non-number detected in 'Number of Rooms' field"
							+ " or 'Square Footage' field");
			return;
		}

		houseUserLabel.setText(String.format("House at %s has been added!",
				addressInput.getText()));
		dbUtil.addHouseInfo(house);
	}

	/**
	 * Tested/working Need to figure out how to delete all bills by this tenant
	 * as well
	 */
	public void deleteTenantButtonListener() {
		String deleteTen = tenantsList.getValue();

		dbUtil.deleteTenant(deleteTen);
		userMessageLabelTenant.setText(String.format(
				"%s has been deleted from the database!", deleteTen));
		resetComboBoxes();
	}

	/*
	 * Tested/Working
	 */
	public void deleteHouseButtonListener() {
		String deleteHouse = addressComboBox.getValue();
		dbUtil.deleteHouse(deleteHouse);
		houseUserLabel.setText(String.format("House a %s has been deleted!",
				deleteHouse));
		houseUserLabel.setText(String.format("House at %s has been added!",
				deleteHouse));
		resetComboBoxes();
	}

	/**
	 * Tested/Working
	 */
	public void submitBillButtonListener() {
		if (ensureAllEntriesLoggedUtilCalc()) {
			return;
		}

		queueAllTenantArrayList();
		saveBillMonth();
		double amtPerTen = getAmountPerTenant();
		System.out.println("Amt per tenant: " + amtPerTen);
		ArrayList<BillPerTenant> thisMonthsTenants = new ArrayList<BillPerTenant>();

		// creates an arraylist of BillPerTenant objects and passes it to
		// addBillPerTenantEntry
		// which adds it to the database
		for (int i = 0; i < utilityParticipants.size(); i++) {
			double tenantBill = amtPerTen * utilityParticipants.get(i).getFte();
			thisMonthsTenants
					.add(new BillPerTenant(billMonthID(modifyBillDate(billDate.getText())),
							houseID(houseAddresses.getValue()),
							utilityParticipants.get(i).getFte(), tenantBill,
							utilityParticipants.get(i).getTenant_ID()));
		}

		userLabelUtilCalc.setText(String.format("Bill for %s has been processed and saved to database!",
				modifyBillDate(billDate.getText())));
		dbUtil.addBillPerTenantEntry(thisMonthsTenants);

	}

	/**
	 * Tested/Working
	 */
	public void saveOccupancyButtonListener() {
		try {
			modifyTenantFTE();
		} catch (InvalidUserEntryException e) {
			userLabelUtilCalc.setText(String
					.format("%s is not a valid portion of occupancy", e.getMessage()));
			return;
		}
		userLabelUtilCalc.setText(String.format("Occupancy for %s saved as %.2f", 
				subletTenantList.getValue(), Double.parseDouble(fte.getText())));
		// Deletes each sublet from combo box after FTE has been recorded
		String subTenantFTERecorded = subletTenantList.getValue();
		for (int i = 0; i < subs.size(); i++) {
			if (subs.get(i).equals(subTenantFTERecorded)) {
				subs.remove(i);
			}
		}
		subletTenantList.setItems(subs);

	}

	public void printReceiptButtonListener() {
		if(ensureAllEntriesLoggedReceipt()){
			return;
		}
		FileWriter tenantReceipt;
		PrintWriter fileOut= null;
		String date = dateReceiptTextField.getText().replace("/", "-");
		String filePath = String.format("C:\\%s%s.txt", 
				"BillsFor", date);
		try {
			
			tenantReceipt = new FileWriter(filePath);
			fileOut = new PrintWriter(tenantReceipt);
		} catch (IOException e1) {
			System.out.println("File Not Found");
			e1.printStackTrace();
		}
		
		try {
			printTenantInfo(fileOut);
		} catch (InvalidUserEntryException e) {
			userLabelUtilCalc.setText(String.format("%s was not found in the databse."
					+ " No receipt printed.",
					e.getMessage()));
			return;
		}
		printHouseInfo(fileOut);
		userLabelUtilCalc.setText(String.format("Receipt Processed! File saved at %s\n"
				+ "(For best results, open with MS Word)", filePath));
		fileOut.close();
		

	}

	/**
	 * Tested/working
	 */
	public void populateActiveStatus() {
		String getTenant = String.format(
				"SELECT * FROM tenant WHERE name = '%s'",
				activeTenants.getValue());

		try {
			if (dbUtil.fetchTenantSelection(getTenant).get(0).isActive()) {
				activeTenantRadioButton.setSelected(true);
			} else {
				deactivateTenantRadioButton.setSelected(true);
			}
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Exception due to something with radio buttons");
		}
	}

	/**
	 * Tested/working
	 */
	public void activateDeactivateButtonListener() {
		String updateTenant = String.format("UPDATE tenant SET active = %b"
				+ " WHERE name = '%s'",
				this.activeTenantRadioButton.isSelected(),
				this.activeTenants.getValue());
		if (this.activeTenantRadioButton.isSelected()) {
			userMessageLabelTenant.setText(String.format(
					"%s has been activated!", activeTenants.getValue()));
		} else {
			userMessageLabelTenant.setText(String.format(
					"%s has been deactivated!", activeTenants.getValue()));
		}
		dbUtil.modifyDatabase(updateTenant);
	}
	
	public void tenantSummaryRadioButtonListener(){
		summaryTextArea.setText("Heres your stinking tenant summary");
	}
	
	public void housingSummaryRadioButtonListener(){
		summaryTextArea.setText("Heres your stinking house summary");
	}
	
	public void billTotStatRadioButtonListener(){
		summaryTextArea.setText("Heres your stinking statistics summary");
	}
	
	public void receiptViewRadioButtonListener(){
		summaryTextArea.setText("Please enter your that address and the date on the "
				+ "receipt you'd like to view");
	}
	
	public void viewReceiptButton(){
		summaryTextArea.setText("Heres your stinking receipt summary");
	}
	

	// ----------------controller utility methods--------------------

	/**
	 * Loads all tenant names into the combo box that have tenant type of
	 * 'sublet'
	 * 
	 * Tested/Working
	 */
	private void queueUpSubletComboBox() {

		String allSubletTenants = "SELECT * FROM tenant WHERE tenantType = 'Sublet'"
				+ " AND active = true";
		ArrayList<Tenant> sublets = dbUtil
				.fetchTenantSelection(allSubletTenants);
		subs = FXCollections.observableArrayList();
		for (int i = 0; i < sublets.size(); i++) {
			subs.add(sublets.get(i).getName());
		}
		this.subletTenantList.setItems(subs);
	}

	/**
	 * Loads all tenant names into the combo boxes labeled tenant
	 * Tested/Working
	 */
	private void queueUpTenantComboBox() {

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
	 * Tested/Working
	 */
	private void queueUpTenantTypesComboBox() {
		ObservableList<String> tenTypesList = FXCollections
				.observableArrayList("Landlord", "Standard", "Sublet");

		this.tenantTypeList.getItems().addAll(tenTypesList);
	}

	/**
	 * Tested/Working
	 */
	private void queueUpAddressComboBox() {
		String allAddressesSQL = "SELECT * FROM house";
		ArrayList<House> allHouses = dbUtil
				.fetchHouseSelection(allAddressesSQL);
		addresses = FXCollections.observableArrayList();
		for (int i = 0; i < allHouses.size(); i++) {
			addresses.add(allHouses.get(i).getAddress());
		}
		addressComboBox.getItems().addAll(addresses);
		houseAddresses.getItems().addAll(addresses);
	}

	/**
	 * sets all comboBoxes containing tenants to null then re-populates
	 * comboBoxes with tenants that accurately reflect db
	 * 
	 * used in relation to deleting tenants and submitting occupancy
	 * 
	 * Tested/Working
	 */
	private void resetComboBoxes() {
		tenantsList.getItems().setAll(FXCollections.observableArrayList());
		activeTenants.getItems().setAll(FXCollections.observableArrayList());
		subletTenantList.getItems().setAll(FXCollections.observableArrayList());
		addressComboBox.getItems().setAll(FXCollections.observableArrayList());
		houseAddresses.getItems().addAll(FXCollections.observableArrayList());
		utilityParticipants = new ArrayList<Tenant>();
		queueNONLandlordArrayList();
		queueUpSubletComboBox();
		queueUpTenantComboBox();
		queueUpAddressComboBox();
	}

	/**
	 * Uses polymorphism (Tenant = Sublet)
	 * 
	 * adds active NON landlord tenants to an arraylist. All tenants have an
	 * editable fte of 1 (see sublet class constructor)
	 * 
	 * Will be used in submitBillButtonListener() to populate and calculate bill
	 * for the month
	 * 
	 * Tested/Working
	 */
	private void queueNONLandlordArrayList() {
		String landlordQuery = "Select * FROM tenant WHERE tenantType NOT IN ('Landlord')"
				+ " AND active = true";
		ArrayList<Tenant> temp = dbUtil.fetchTenantSelection(landlordQuery);
		for (int i = 0; i < temp.size(); i++) {
			utilityParticipants.add(new Sublet(temp.get(i).getName(), true, 
					temp.get(i).getTenant_ID()));
		}
	}

	/**
	 * Uses polymorphism (Tenant = Landlord)
	 * 
	 * adds active landlord tenants to the paying tenants arrayList. Landlord
	 * tenants have FTEs of 0 (see landlord class constructor)
	 * 
	 * Must be called after queueNONLandlordArrayList() and after all FTEs have
	 * been modified Must be called in submitBillButtonlistener() 
	 * 
	 * Tested/Working
	 */
	private void queueAllTenantArrayList() {
		String landlordQuery = "Select * FROM tenant WHERE tenantType IN ('Landlord')"
				+ " AND active = true";
		ArrayList<Tenant> temp = dbUtil.fetchTenantSelection(landlordQuery);
		for (int i = 0; i < temp.size(); i++) {
			utilityParticipants.add(new Landlord(temp.get(i).getName(), true, 
							temp.get(i).getTenant_ID()));
		}

	}

	/**
	 * Tested/working Still need to delete the tenant after user has selected it
	 * 
	 * changes the fte of the tenant specified by the user in the combobox Must
	 * be used in saveOccupancyButtonListener()
	 * 
	 * Tested/Working
	 * 
	 * @throws InvalidUserEntryException
	 * 
	 */
	private void modifyTenantFTE() throws InvalidUserEntryException {
		for (int i = 0; i < utilityParticipants.size(); i++) {
			if (utilityParticipants.get(i).getName()
					.equals(subletTenantList.getValue())) {

				try {
					utilityParticipants.get(i).setFte(
							Double.parseDouble(fte.getText()));
				} catch (NumberFormatException e) {
					throw new InvalidUserEntryException(fte.getText());
				}
			}
		}

	}

	/**
	 * totals the bill and divides that by the total FTE of all tenants.
	 * Must be used after all sublet FTEs have been entered by the user. 
	 * Must use in submitBillButtonListener(). 
	 * Must use after queueAllTenantArrayList().
	 * 
	 * Tested/Working
	 * 
	 * @return
	 */
	private double getAmountPerTenant() {
		double totalParticipateCoeff = 0;
		double totalBill = Double.parseDouble(fossilFuelBill.getText())
				+ Double.parseDouble(billAmount.getText())
				+ Double.parseDouble(otherBills.getText());
		for (int i = 0; i < utilityParticipants.size(); i++) {
			totalParticipateCoeff += utilityParticipants.get(i).getFte();
		}
		System.out.println(totalParticipateCoeff);

		return totalBill / totalParticipateCoeff;
	}

	/**
	 * saves billMonth in database from GUI items. Must use in
	 * submitBillButtonListener() 
	 * 
	 * Tested/Working
	 */
	private void saveBillMonth() {

		BillMonth billMonth = null;
		try {
			double totalBill = Double.parseDouble(fossilFuelBill.getText()) +
					Double.parseDouble(billAmount.getText()) +
					Double.parseDouble(otherBills.getText());
			billMonth = new BillMonth(modifyBillDate(billDate.getText()),
					Double.parseDouble(fossilFuelBill.getText()),
					Double.parseDouble(billAmount.getText()),
					Double.parseDouble(otherBills.getText()), totalBill, 
					houseID(houseAddresses.getValue()));
		} catch (NumberFormatException e) {
			userLabelUtilCalc.setText("Non-number detected in bill field");
			return;
		}
		dbUtil.addBillingMonthEntry(billMonth);
	}

	/**
	 * Uses String.split to change the bill from month first year last to year
	 * first month last for sorting purposes. Untested
	 * 
	 * Tested/Working
	 * 
	 * @return
	 */
	private String modifyBillDate(String dbDateIn) {
		String[] date = dbDateIn.split("/");
		String dbDate = date[1] + "/" + date[0];
		return dbDate;

	}

	/**
	 * Returns the billMonth ID based on the date. Date must be modified from
	 * the date the user enters. use modifyBillDate() method 
	 * 
	 * Tested/Working
	 * 
	 * @param date
	 * @return
	 */
	private int billMonthID(String date) {
		String billMonthIDSQL = String.format(
				"SELECT * FROM billMonth WHERE date = '%s'", date);
		return dbUtil.fetchBillMonth(billMonthIDSQL).get(0).getBillMonth_ID();
	}

	/**
	 * returns the tenant ID based on the name passed in. Ignores the
	 * possibility of duplicate names 
	 * 
	 * @param name
	 * @return
	 */
//	private int tenantID(String name) {
//		String tenantIDSQL = String.format(
//				"SELECT * FROM tenant WHERE name = '%s'", name);
//		return dbUtil.fetchTenantSelection(tenantIDSQL).get(0).getTenant_ID();
//	}

	/**
	 * returns the house ID of the first house in the db. assumes only 1 house
	 * exists in DB
	 * 
	 * will be modified to accept an address that will be stored in a combobox
	 * in the GUI 
	 * 
	 * Tested/Working
	 * 
	 * @return
	 */
	private int houseID(String address) {
		String houseIDSQL = String.format(
				"SELECT * FROM house WHERE address = '%s'", address);
		return dbUtil.fetchHouseSelection(houseIDSQL).get(0).getHouse_ID();
	}

	/**
	 * Checks all fields necessary to add a full BillMonth and BillPerTenant
	 * object to database. If any field is empty, user will be notified and no
	 * code executed
	 * 
	 * Method must be used at the every beginning of the listener
	 * 
	 * Tested/Working
	 * 
	 * @return
	 */
	private boolean ensureAllEntriesLoggedUtilCalc() {
		boolean notifiyUser = false;
		if (billDate.getText().equals("")) {
			userLabelUtilCalc
					.setText("Bill Date field empty! Please enter 0 if there is no bill");
			notifiyUser = true;
		}
		if (houseAddresses.getSelectionModel().isEmpty()) {
			userLabelUtilCalc.setText("No house selected!");
			notifiyUser = true;
		}
		if (fossilFuelBill.getText().equals("")) {
			userLabelUtilCalc
					.setText("Fossil Fuel bill field empty! Please enter 0 if there is no bill");
			notifiyUser = true;
		}
		if (billAmount.getText().equals("")) {
			userLabelUtilCalc
					.setText("Electric bill field empty! Please enter 0 if there is no bill");
			notifiyUser = true;
		}
		if (otherBills.getText().equals("")) {
			userLabelUtilCalc
					.setText("Other bill field empty! Please enter 0 if there is no Bill");
			notifiyUser = true;
		}

		return notifiyUser;
	}

	/**
	 * Checks all fields necessary to add a full House object to database. If
	 * any field is empty, user will be notified and no code executed
	 * 
	 * Method must be used at the every beginning of the listener
	 * 
	 * Tested/Working
	 * 
	 * @return
	 */
	private boolean ensureAllEntriesLoggedHouseInfo() {
		boolean notifyUser = false;
		if (addressInput.getText().equals("")) {
			houseUserLabel.setText("Address field empty!");
			notifyUser = true;
		}
		if (numberOfRooms.getText().equals("")) {
			houseUserLabel.setText("Number of rooms field empty!");
			notifyUser = true;
		}
		if (squareFootage.getText().equals("")) {
			houseUserLabel.setText("Square Footage field empty!");
			notifyUser = true;
		}

		return false;
	}

	/**
	 * Checks all fields necessary to add a full Tenant object to database. 
	 * If any field is empty, user will be notified and no code executed
	 * 
	 * Method must be used at the every beginning of the listener
	 * 
	 * Tested/Working
	 * 
	 * @return
	 */
	private boolean ensureAllEntriesLoggedTenant() {
		boolean notifyUser = false;
		if (nameTextBox.getText().equals("")) {
			notifyUser = true;
			userMessageLabelTenant.setText("Name field is empty!");
		}
		if (tenantTypeList.getSelectionModel().isEmpty()) {
			notifyUser = true;
			userMessageLabelTenant.setText("No tenant type selected!");
		}
		return notifyUser;
	}
	
	private String getWhiteSpace(String s, int columnWidth){
		int length = columnWidth - s.length();
		String spaces = "";
		for(int i = 0; i < length;i++){
			spaces += " ";
		}
		return spaces;
	}
	
	private void printTenantInfo(PrintWriter fileOut) throws InvalidUserEntryException{
		StringBuilder tenantInfo = new StringBuilder();
		String date = String.format("Date: %s\n\n", dateReceiptTextField.getText());
		tenantInfo.append(date);
		String header = String.format("|%-20s|%-5s|%-15s|%-8s|\n", 
				"Tenant Name", "FTE", "Tenant Type", "Owed");
		tenantInfo.append(header);
		String lineBreak = "";
		for(int i = 0; i < header.length(); i++){
			lineBreak += "-";
		}
		tenantInfo.append(String.format("%s\n", lineBreak));
		ArrayList<ReceiptTenantInfo> tens = 
				dbUtil.fetchReceiptInfoForTenant(modifyBillDate(dateReceiptTextField.getText()));
		if(tens.size() == 0){
			throw new InvalidUserEntryException(dateReceiptTextField.getText());
		}
		for(int i = 0; i < tens.size(); i++){
			String stuff = String.format("|%-20s|%-5.2f|%-15s|$%-7.2f|\n",
					tens.get(i).getName(), tens.get(i).getFte(), 
					tens.get(i).getTenantType(), tens.get(i).getAmountOwed());
			tenantInfo.append(stuff);
		}
		tenantInfo.append("\n\n\n\n");
		fileOut.println(tenantInfo);
	}
	
	private void printHouseInfo(PrintWriter fileOut){
		ArrayList<ReceiptHouseInfo> houseInformation = 
				dbUtil.fetchReceiptInfoForHouse(modifyBillDate(dateReceiptTextField.getText()), 
						houseID(houseAddresses.getValue()));
		StringBuilder houseInfo = new StringBuilder();
		String idHouse = String.format("|%-22s|%-12s|%-12s|%-12s|\n", "Address", "Total Bill", "Cost / sqft", "Cost / Room");
		String dash = "";
		for(int i = 2; i <= idHouse.length(); i++){
			dash += "-";
		}
		houseInfo.append(idHouse);
		houseInfo.append(String.format("%s\n", dash));
		
		for(int i = 0; i < houseInformation.size(); i++){
		String tenantInfo = String.format("|%-22s|%-12.2f|%-12.2f|%-12.2f|\n",
				 houseInformation.get(i).getAddress(), houseInformation.get(i).getTotalBill(), 
				 houseInformation.get(i).getCostPerSqFt(),
				 houseInformation.get(i).getCostPerRoom());
		houseInfo.append(tenantInfo);
		}
		fileOut.println(houseInfo);
		
	}
	
	private boolean ensureAllEntriesLoggedReceipt(){
		boolean notifyUser = false;
		if(houseAddresses.getSelectionModel().isEmpty()){
			userLabelUtilCalc.setText("No house address selected");
			notifyUser = true;
		}
		if(dateReceiptTextField.getText().equals("")){
			userLabelUtilCalc.setText("No date entered");
			notifyUser = true;
		}
		return notifyUser;
	}

}
