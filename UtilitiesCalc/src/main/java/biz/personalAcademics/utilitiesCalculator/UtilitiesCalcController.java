package biz.personalAcademics.utilitiesCalculator;

import biz.personalAcademics.databaseModelClasses.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
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
	private RadioButton tenSumRadioButton;

	@FXML
	private TextArea summaryTextArea;

	@FXML
	private TextField ReceiptViewDateTextField;

	@FXML
	private ComboBox<String> receiptViewAddressComboBox;

	@FXML
	private RadioButton receiptViewRadioButton;

	@FXML
	private Button viewReceiptButton;

	ObservableList<String> tenants;
	ObservableList<String> subs;
	ObservableList<String> addresses;
	ArrayList<Tenant> utilityParticipants;
	boolean executingInIDE;

	/**
	 * Tested/working
	 */
	public void initialize() {
		utilityParticipants = new ArrayList<Tenant>();
		setExecutionEnvironment();
		checkDBStatus();
		queueUpTenantTypesComboBox();
		queueUpTenantComboBox();
		queueUpSubletComboBox();
		queueUpAddressComboBox();
		queueNONLandlordArrayList();
		displayTenantSummary();
		setHouseField();
		initalizeReceiptView();

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

		DatabaseUtility.addTenant(tenant);

		userMessageLabelTenant.setText(String.format("%s has been added!",
				tenant.getName()));
		nameTextBox.clear();
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
					Integer.parseInt(numberOfRooms.getText()),
					Integer.parseInt(squareFootage.getText()));
		} catch (NumberFormatException e) {
			houseUserLabel
					.setText("Non-number detected in 'Number of Rooms' field"
							+ " or 'Square Footage' field");
			return;
		}

		houseUserLabel.setText(String.format("House at %s has been added!",
				addressInput.getText()));
		DatabaseUtility.addHouseInfo(house);
		setHouseField();
		resetComboBoxes();
	}

	/**
	 * Tested/working Need to figure out how to delete all bills by this tenant
	 * as well
	 */
	public void deleteTenantButtonListener() {
		String deleteTen = tenantsList.getValue();

		DatabaseUtility.deleteTenant(deleteTen);
		userMessageLabelTenant.setText(String.format(
				"%s has been deleted from the database!", deleteTen));
		resetComboBoxes();
	}

	/*
	 * Tested/Working
	 */
	public void deleteHouseButtonListener() {
		String deleteHouse = addressComboBox.getValue();
		DatabaseUtility.deleteHouse(deleteHouse);
		houseUserLabel.setText(String.format("House a %s has been deleted!",
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
		ArrayList<BillPerTenant> thisMonthsTenants = new ArrayList<BillPerTenant>();

		// creates an arraylist of BillPerTenant objects and passes it to
		// addBillPerTenantEntry
		// which adds it to the database
		try {
			for (int i = 0; i < utilityParticipants.size(); i++) {
				double tenantBill = amtPerTen
						* utilityParticipants.get(i).getFte();
				thisMonthsTenants.add(new BillPerTenant(
						billMonthID(modifyBillDate(billDate.getText())),
						houseID(houseAddresses.getValue()), utilityParticipants
								.get(i).getFte(), tenantBill,
						utilityParticipants.get(i).getTenant_ID()));

			}
		} catch (InvalidUserEntryException e) {
			userLabelUtilCalc.setText(e.getMessage());
			return;
		}

		userLabelUtilCalc.setText(String.format(
				"Bill for %s has been processed and saved to database!",
				billDate.getText()));
		DatabaseUtility.addBillPerTenantEntry(thisMonthsTenants);
		clearInformationForBillCalculation();

	}

	/**
	 * Tested/Working
	 */
	public void saveOccupancyButtonListener() {
		try {
			modifyTenantFTE();
		} catch (InvalidUserEntryException e) {
			userLabelUtilCalc.setText(String.format(
					"%s is not a valid portion of occupancy", e.getMessage()));
			return;
		}
		userLabelUtilCalc.setText(String.format(
				"Occupancy for %s saved as %.2f", subletTenantList.getValue(),
				Double.parseDouble(fte.getText())));
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
		if (ensureAllEntriesLoggedReceipt()) {
			return;
		}
		File currentJavaJarFile = new File(UtilitesCalcMain.class
				.getProtectionDomain().getCodeSource().getLocation().getPath());
		String currentJavaJarFilePath = currentJavaJarFile.getAbsolutePath();
		String currentRootDirectoryPath = currentJavaJarFilePath.replace(
				currentJavaJarFile.getName(), "");

		if (executingInIDE) {
			currentRootDirectoryPath = "../UtilitiesCalc/src/main/java/resources/receipts/";
		}

		String date = dateReceiptTextField.getText().replace("/", "-");
		String filePath = String.format("%s%s%s.txt", currentRootDirectoryPath,
				"BillsFor", date);
		
		PrintWriter fileOut = null;
		try {
			// stores PrintWriter object that was used to print the receipt info
			// This will be passed to printHouseInfo() method
			fileOut = printTenantInfo(filePath);
		} catch (InvalidUserEntryException e) { // REQ#12
			userLabelUtilCalc.setText(String
					.format("%s was not found in the databse."
							+ " No receipt printed.", e.getMessage()));
			return;
		}
		printHouseInfo(fileOut);
		userLabelUtilCalc.setText(String.format(
				"Receipt Processed! File saved at %s\r\n"
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
			if (DatabaseUtility.fetchTenantSelection(getTenant).get(0)
					.isActive()) {
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
		DatabaseUtility.modifyDatabase(updateTenant);
	}

	public void tenantSummaryRadioButtonListener() {
		displayTenantSummary();
	}

	public void billTotStatRadioButtonListener() {
		displayHouseStatistics();
	}

	public void receiptViewRadioButtonListener() {
		receiptViewAddressComboBox.setDisable(false);
		receiptViewAddressComboBox.setOpacity(100);
		viewReceiptButton.setDisable(false);
		viewReceiptButton.setOpacity(100);
		ReceiptViewDateTextField.setDisable(false);
		ReceiptViewDateTextField.setOpacity(100);
		summaryTextArea
				.setText("Please select the address and and date on the bill you would like to query");
	}

	public void viewReceiptButton() {
		if (ensureAllUserEntriesLoggedReceiptView()) {
			return;
		}
		try {
			printreceiptHouseInfo(printReceiptTenantInfo());

		} catch (InvalidUserEntryException e) {
			summaryTextArea
					.setText("Your query returned no results, double check your date");
			return;
		}

	}

	// ----------------controller utility methods--------------------

	/**
	 * Loads all tenant names into the combo box that have tenant type of
	 * 'sublet'
	 * 
	 * Tested/Working
	 */
	private void queueUpSubletComboBox() {
		subletTenantList.getItems().setAll(FXCollections.observableArrayList());
		String allSubletTenants = "SELECT * FROM tenant WHERE tenantType = 'Sublet'"
				+ " AND active = true";
		ArrayList<Tenant> sublets = null;

		sublets = DatabaseUtility.fetchTenantSelection(allSubletTenants);

		subs = FXCollections.observableArrayList();
		for (int i = 0; i < sublets.size(); i++) {
			subs.add(sublets.get(i).getName());
		}
		this.subletTenantList.setItems(subs);
	}

	/**
	 * Loads all tenant names into the combo boxes labeled tenant Tested/Working
	 */
	private void queueUpTenantComboBox() {
		tenantsList.getItems().setAll(FXCollections.observableArrayList());
		activeTenants.getItems().setAll(FXCollections.observableArrayList());
		String allTenantsSQL = "SELECT * FROM tenant";
		ArrayList<Tenant> allTenants = null;
		try {
			allTenants = DatabaseUtility.fetchTenantSelection(allTenantsSQL);

		} catch (NullPointerException e) {
			e.printStackTrace();
		}
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
		addressComboBox.getItems().clear();
		houseAddresses.getItems().clear();
		receiptViewAddressComboBox.getItems().clear();
		String allAddressesSQL = "SELECT * FROM house";
		ArrayList<House> allHouses = DatabaseUtility
				.fetchHouseSelection(allAddressesSQL);
		addresses = FXCollections.observableArrayList();
		for (int i = 0; i < allHouses.size(); i++) {
			addresses.add(allHouses.get(i).getAddress());
		}
		addressComboBox.getItems().addAll(addresses);
		houseAddresses.getItems().addAll(addresses);
		receiptViewAddressComboBox.getItems().addAll(addresses);
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
		utilityParticipants = new ArrayList<Tenant>();

		queueNONLandlordArrayList();
		queueUpSubletComboBox();
		queueUpTenantComboBox();
		queueUpAddressComboBox();
		displayTenantSummary();

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
		ArrayList<Tenant> temp = null;

		temp = DatabaseUtility.fetchTenantSelection(landlordQuery);
		// REQ#10
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
		ArrayList<Tenant> temp = null;

		temp = DatabaseUtility.fetchTenantSelection(landlordQuery);
		// REQ#10
		for (int i = 0; i < temp.size(); i++) {
			utilityParticipants.add(new Landlord(temp.get(i).getName(), true,
					temp.get(i).getTenant_ID()));
		}

	}

	private void setHouseField() {
		String houseSQL = "SELECT * FROM house";
		ArrayList<House> houseInfo = DatabaseUtility
				.fetchHouseSelection(houseSQL);
		Integer sqFootage = 0;
		Integer numberOfRoomsInt = 0;
		try {
			addressInput.setText(houseInfo.get(0).getAddress());
			sqFootage = houseInfo.get(0).getSqFt();
			numberOfRoomsInt = houseInfo.get(0).getNumRooms();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("No Addresses Found");
		}
		squareFootage.setText(sqFootage.toString());
		numberOfRooms.setText(numberOfRoomsInt.toString());
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

	private void modifyTenantFTE() throws InvalidUserEntryException { // REQ #11
		for (int i = 0; i < utilityParticipants.size(); i++) {
			if (utilityParticipants.get(i).getName()
					.equals(subletTenantList.getValue())) {

				try {
					utilityParticipants.get(i).setFte(
							Double.parseDouble(fte.getText()));
				} catch (NumberFormatException e) {
					// REQ#12
					throw new InvalidUserEntryException(fte.getText());
				}
			}
		}

	}

	/**
	 * totals the bill and divides that by the total FTE of all tenants. Must be
	 * used after all sublet FTEs have been entered by the user. Must use in
	 * submitBillButtonListener(). Must use after queueAllTenantArrayList().
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
			double totalBill = Double.parseDouble(fossilFuelBill.getText())
					+ Double.parseDouble(billAmount.getText())
					+ Double.parseDouble(otherBills.getText());
			billMonth = new BillMonth(modifyBillDate(billDate.getText()),
					Double.parseDouble(fossilFuelBill.getText()),
					Double.parseDouble(billAmount.getText()),
					Double.parseDouble(otherBills.getText()), totalBill,
					houseID(houseAddresses.getValue()));
		} catch (NumberFormatException e) {
			userLabelUtilCalc.setText("Non-number detected in bill field");
			return;
		} catch (InvalidUserEntryException e1) {
			userLabelUtilCalc.setText(e1.getMessage());
			return;
		}
		DatabaseUtility.addBillingMonthEntry(billMonth);
	}

	/**
	 * Uses String.split to change the bill from month first year last to year
	 * first month last for sorting purposes. Untested
	 * 
	 * Tested/Working
	 * 
	 * @return
	 */
	private String modifyBillDate(String dbDateIn)
			throws InvalidUserEntryException {
		String[] date = dbDateIn.split("/");

		if (date.length != 2) {
			throw new InvalidUserEntryException(String.format(
					"%s is not a valid date", dbDateIn));
		}

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
		return DatabaseUtility.fetchBillMonthID(date);
	}

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
		return DatabaseUtility.fetchHouseSelection(houseIDSQL).get(0)
				.getHouse_ID();
	}

	private boolean ensureAllUserEntriesLoggedReceiptView() {
		boolean notifyUser = false;
		if (receiptViewAddressComboBox.getSelectionModel().isEmpty()) {
			summaryTextArea.setText("No Address Selected!");
			notifyUser = true;
		}

		return notifyUser;
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

		return notifyUser;
	}

	/**
	 * Checks all fields necessary to add a full Tenant object to database. If
	 * any field is empty, user will be notified and no code executed
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

	// private String getWhiteSpace(String s, int columnWidth){
	// int length = columnWidth - s.length();
	// String spaces = "";
	// for(int i = 0; i < length;i++){
	// spaces += " ";
	// }
	// return s + spaces;
	// }

	/**
	 * prints the tenants info to a file
	 * 
	 * @param fileOut
	 * @throws InvalidUserEntryException
	 */
	private PrintWriter printTenantInfo(String filePath)
			throws InvalidUserEntryException {

		// StringBuilder REQ#2
		StringBuilder tenantInfo = new StringBuilder();
		String date = String.format("Date: %s\r\n\r\n",
				dateReceiptTextField.getText());
		tenantInfo.append(date);
		String header = String.format("|%-20s|%-5s|%-15s|%-8s|\r\n",
				"Tenant Name", "FTE", "Tenant Type", "Owed");
		tenantInfo.append(header);
		String lineBreak = "";
		for (int i = 0; i < header.length(); i++) {
			lineBreak += "-";
		}
		tenantInfo.append(String.format("%s\r\n", lineBreak));
		ArrayList<ReceiptTenantInfo> tens = DatabaseUtility
				.fetchReceiptInfoForTenant(modifyBillDate(dateReceiptTextField
						.getText()));
		if (tens.size() == 0) {
			throw new InvalidUserEntryException(dateReceiptTextField.getText());
		}
		for (int i = 0; i < tens.size(); i++) {
			String stuff = String.format("|%-20s|%-5.2f|%-15s|$%-7.2f|\r\n",
					tens.get(i).getName(), tens.get(i).getFte(), tens.get(i)
							.getTenantType(), tens.get(i).getAmountOwed());
			tenantInfo.append(stuff);
		}
		tenantInfo.append("\r\n\r\n\r\n\r\n");
		
		/*
		 * gets the PrintWriter AFTER database is queried
		 * this same PrintWriter object will be passed to the printHouseReceiptInfo() method
		 * in the listener method
		 */
		PrintWriter passToHouseInfo = getReceiptPrintWriter(filePath);
		passToHouseInfo.println(tenantInfo);
		
		return passToHouseInfo;
	}

	/**
	 * Prints house info to a file
	 * 
	 * @param fileOut
	 */
	private void printHouseInfo(PrintWriter fileOut) {
		ArrayList<ReceiptHouseInfo> houseInformation = DatabaseUtility
				.fetchReceiptInfoForHouse(
						modifyBillDate(dateReceiptTextField.getText()),
						houseID(houseAddresses.getValue()));
		StringBuilder houseInfo = new StringBuilder(); // REQ#2
		String idHouse = String.format("|%-22s|%-12s|%-12s|%-12s|\r\n",
				"Address", "Total Bill", "Cost / sqft", "Cost / Room");
		String dash = "";
		for (int i = 2; i <= idHouse.length(); i++) {
			dash += "-";
		}
		houseInfo.append(idHouse);
		houseInfo.append(String.format("%s\r\n", dash));

		for (int i = 0; i < houseInformation.size(); i++) {
			String tenantInfo = String.format(
					"|%-22s|%-12.2f|%-12.2f|%-12.2f|\r\n", houseInformation
							.get(i).getAddress(), houseInformation.get(i)
							.getTotalBill(), houseInformation.get(i)
							.getCostPerSqFt(), houseInformation.get(i)
							.getCostPerRoom());
			houseInfo.append(tenantInfo);
		}
		fileOut.println(houseInfo);

	}

	/**
	 * Accepts string from printReceiptTenantInfo(), appends it to the house
	 * info and then prints all info to text area
	 * 
	 * @param tenantReceipt
	 */
	private void printreceiptHouseInfo(String tenantReceipt) {
		ArrayList<ReceiptHouseInfo> houseInformation = DatabaseUtility
				.fetchReceiptInfoForHouse(
						modifyBillDate(ReceiptViewDateTextField.getText()),
						houseID(receiptViewAddressComboBox.getValue()));
		StringBuilder houseInfo = new StringBuilder();
		String idHouse = String.format("|%-22s|%-12s|%-12s|%-12s|\r\n",
				"Address", "Total Bill", "Cost / sqft", "Cost / Room");
		String dash = "";
		for (int i = 2; i <= idHouse.length(); i++) {
			dash += "-";
		}
		houseInfo.append(idHouse);
		houseInfo.append(String.format("%s\r\n", dash));

		for (int i = 0; i < houseInformation.size(); i++) {
			String tenantInfo = String.format(
					"|%-22s|%-12.2f|%-12.2f|%-12.2f|\r\n", houseInformation
							.get(i).getAddress(), houseInformation.get(i)
							.getTotalBill(), houseInformation.get(i)
							.getCostPerSqFt(), houseInformation.get(i)
							.getCostPerRoom());
			houseInfo.append(tenantInfo);
		}
		summaryTextArea.setText(tenantReceipt + houseInfo.toString());
	}

	/**
	 * generates tenant info string and should send to printReceiptHouseInfo()
	 * 
	 * @return
	 * @throws InvalidUserEntryException
	 */
	private String printReceiptTenantInfo() throws InvalidUserEntryException {
		StringBuilder tenantInfo = new StringBuilder();
		String date = String.format("Date: %s\r\n\r\n",
				ReceiptViewDateTextField.getText());
		tenantInfo.append(date);
		String header = String.format("|%-20s|%-5s|%-15s|%-8s|\r\n",
				"Tenant Name", "FTE", "Tenant Type", "Owed");
		tenantInfo.append(header);
		String lineBreak = "";
		for (int i = 0; i < header.length(); i++) {
			lineBreak += "-";
		}
		tenantInfo.append(String.format("%s\r\n", lineBreak));
		ArrayList<ReceiptTenantInfo> tens = DatabaseUtility
				.fetchReceiptInfoForTenant(modifyBillDate(ReceiptViewDateTextField
						.getText()));
		if (tens.size() == 0) {
			throw new InvalidUserEntryException(
					ReceiptViewDateTextField.getText());
		}
		for (int i = 0; i < tens.size(); i++) {
			String stuff = String.format("|%-20s|%-5.2f|%-15s|$%-7.2f|\r\n",
					tens.get(i).getName(), tens.get(i).getFte(), tens.get(i)
							.getTenantType(), tens.get(i).getAmountOwed());
			tenantInfo.append(stuff);
		}
		tenantInfo.append("\r\n\r\n\r\n\r\n");
		return tenantInfo.toString();
	}

	/**
	 * check for all user input necessary for receipt
	 * 
	 * @return
	 */
	private boolean ensureAllEntriesLoggedReceipt() {
		boolean notifyUser = false;
		if (houseAddresses.getSelectionModel().isEmpty()) {
			userLabelUtilCalc.setText("No house address selected");
			notifyUser = true;
		}
		if (dateReceiptTextField.getText().equals("")) {
			userLabelUtilCalc.setText("No date entered");
			notifyUser = true;
		}
		return notifyUser;
	}

	/**
	 * generates a line between the header and the information to enhance
	 * readability
	 * 
	 * @param header
	 * @return
	 */
	private String getHeaderBreak(String header) {
		String dash = "";
		for (int i = 0; i < header.length(); i++) {
			dash += "-";
		}
		return String.format("%s\r\n", dash);
	}

	/**
	 * Displays the tenant summary of all the living in the specified house
	 */
	private void displayTenantSummary() {
		StringBuilder tenantSum = new StringBuilder();
		String header = String.format("|%-20s|%-10s|%-15s|%-12s|\r\n", "Name",
				"type", "Months Paid", "Total Paid");
		tenantSum.append(header);
		String SQL = "SELECT * FROM tenant";
		ArrayList<Tenant> names = null;

		names = DatabaseUtility.fetchTenantSelection(SQL);

		ArrayList<BillPerTenant> tenantInfo = new ArrayList<BillPerTenant>();
		tenantSum.append(getHeaderBreak(header));
		for (int i = 0; i < names.size(); i++) {
			ArrayList<BillPerTenant> totalEntries = DatabaseUtility
					.fetchTenantTotals(names.get(i).getName());
			double amountPaid = 0;
			double totalStay = 0;
			for (int index = 0; index < totalEntries.size(); index++) {
				amountPaid += totalEntries.get(index).getBill();
				totalStay += totalEntries.get(index).getFte();
			}
			tenantInfo.add(new BillPerTenant(0, 0, 0, totalStay, names.get(i)
					.getName(), amountPaid, names.get(i).getTenantType()));
		}

		for (int i = 0; i < tenantInfo.size(); i++) {
			tenantSum.append(String.format(
					"|%-20s|%-10s|%-15.2f|$ %-10.2f|\r\n", tenantInfo.get(i)
							.getTenantName(),
					tenantInfo.get(i).getTenantType(), tenantInfo.get(i)
							.getFte(), tenantInfo.get(i).getBill()));
		}

		summaryTextArea.setText(tenantSum.toString());
	}

	/**
	 * displays the house statisics in the text area
	 */
	private void displayHouseStatistics() {
		String getAllHouses = "SELECT * FROM house";
		ArrayList<ReceiptHouseInfo> houseInfo = DatabaseUtility
				.fetchHouseSummary(DatabaseUtility
						.fetchHouseSelection(getAllHouses).get(0).getAddress());
		StringBuilder houseSum = new StringBuilder();
		String header = String.format(
				"|%-25s|%-16s|%-15s|%-10s|%-15s|%-15s|%-15s|\r\n", "Address",
				"Utility Total", "Fossil Fuel", "Electric", "Other",
				"Sq Ft Average", "Average Per Room");
		houseSum.append(header);
		houseSum.append(getHeaderBreak(header));
		double totalBill = 0;
		double totalFossilFuel = 0;
		double totalElectric = 0;
		double totalOther = 0;
		double sqFtAverage = 0;
		double roomAverage = 0;
		for (int i = 0; i < houseInfo.size(); i++) {
			totalBill += houseInfo.get(i).getTotalBill();
			totalFossilFuel += houseInfo.get(i).getFossilFuel();
			totalElectric += houseInfo.get(i).getElectric();
			totalOther += houseInfo.get(i).getOtherBills();
			sqFtAverage += houseInfo.get(i).getCostPerSqFt();
			roomAverage += houseInfo.get(i).getCostPerRoom();

		}
		ReceiptHouseInfo h = new ReceiptHouseInfo(
				houseInfo.get(0).getAddress(), totalBill, totalFossilFuel,
				totalElectric, totalOther, sqFtAverage / houseInfo.size(),
				roomAverage / houseInfo.size());

		houseSum.append(String
				.format("|%-25s|$%-15.2f|$%-14.2f|$%-9.2f|$%-14.2f|$%-14.2f|$%-15.2f|\r\n",
						h.getAddress(), h.getTotalBill(), h.getFossilFuel(),
						h.getElectric(), h.getOtherBills(), h.getCostPerSqFt(),
						h.getCostPerRoom()));

		summaryTextArea.setText(houseSum.toString());
	}

	/**
	 * clears all entries in the bill calculation
	 */
	private void clearInformationForBillCalculation() {
		billDate.clear();
		houseAddresses.getSelectionModel().clearSelection();
		billAmount.clear();
		fossilFuelBill.clear();
		otherBills.clear();
		fte.clear();
		subletTenantList.getSelectionModel().clearSelection();

	}

	/**
	 * Initializes the look of the receipt view to emphasize the fact that its
	 * not in service unless selected
	 */
	private void initalizeReceiptView() {
		receiptViewAddressComboBox.setDisable(true);
		receiptViewAddressComboBox.setOpacity(10);
		viewReceiptButton.setDisable(true);
		viewReceiptButton.setOpacity(10);
		ReceiptViewDateTextField.setDisable(true);
		ReceiptViewDateTextField.setOpacity(10);

	}

	/**
	 * check is db exists, if not, method initializes a database
	 */
	private void checkDBStatus() {

		Connection conn = null;
		try {
			Properties p = System.getProperties();
			File currentJavaJarFile = new File(UtilitesCalcMain.class
					.getProtectionDomain().getCodeSource().getLocation()
					.getPath());
			String currentJavaJarFilePath = currentJavaJarFile
					.getAbsolutePath();
			String currentRootDirectoryPath = currentJavaJarFilePath.replace(
					currentJavaJarFile.getName(), "");

			if (executingInIDE) {
				currentRootDirectoryPath = "../UtilitiesCalc";
				System.out.println("executing in IDE");
			}

			// Sets the appropriate path
			p.setProperty("derby.system.home", currentRootDirectoryPath
					+ "/database");
			conn = DriverManager.getConnection(DatabaseUtility.DB_URL);
			conn.close();

		} catch (SQLException e) {
			DatabaseUtility.createDBTables();
		}
	}

	/**
	 * Checks if program is executing in jar file for IDE and sets the boolean
	 * for file management accordingly
	 */
	private void setExecutionEnvironment() {
		try {
			File checkIfInJar = new File(
					"../UtilitiesCalc/database/CHECKME.txt");
			Scanner check = new Scanner(checkIfInJar);
			check.close();
			executingInIDE = true;
		} catch (FileNotFoundException e1) {
			System.out.println("File Not Found");
			executingInIDE = false;
		}
	}

	/**
	 * Returns a PrintWriter that will save a file in the package
	 * resources.receipts if the program is executing inside of an IDE or next
	 * to the jar file is executing in a jar
	 * 
	 * @return
	 */
	private PrintWriter getReceiptPrintWriter(String filePath) {

		FileWriter tenantReceipt;
		PrintWriter fileOut = null;
		try {
			tenantReceipt = new FileWriter(filePath);
			fileOut = new PrintWriter(tenantReceipt);
		} catch (IOException e1) {
			System.out.println("File Not Found");
			e1.printStackTrace();
		}

		return fileOut;
	}

}
