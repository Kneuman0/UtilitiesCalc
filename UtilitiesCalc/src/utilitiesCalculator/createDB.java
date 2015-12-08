package utilitiesCalculator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.collections.FXCollections;

import org.apache.derby.iapi.store.raw.FetchDescriptor;

public class createDB {

	public static void main(String[] args) {

		final boolean DROP_TABLE = true;
		final boolean RESET_DATABSE = true;
		final boolean SHOW_ROW_COUNT = true;
		DatabaseUtility util = new DatabaseUtility();

		final String DB_URL = "jdbc:derby:db/UtilitiesCalc;create=true;";

		try {

			Connection conn = DriverManager.getConnection(DB_URL);
			// if(DROP_TABLE){
			// util.dropTable("tenant");
			//
			// }
			if (RESET_DATABSE) {
				createHouseTable(conn, DROP_TABLE);
				createBillMonthTable(conn, DROP_TABLE);
				createBillMonthPerTenant(conn, DROP_TABLE);
				createTenantTable(conn, DROP_TABLE);
				util.inputSampleTenantEntries();
				util.inputSampleHouseEntries();
				util.inputSampleBillMonthEntries();
				util.inputSampleBillPerTenantEntries();
			}
			//-----------------StartTest-----------------------------------
//			String date = "2015/09";
//			ArrayList<ReceiptTenantInfo> rti = util.fetchReceiptInfoForTenant(date);
//			System.out.println(rti.size());
//			for(int i = 0; i < rti.size(); i++){
//				System.out.printf("Name: %s\nBill: %s\nHouse_ID: %d\n--------------------\n", 
//						rti.get(i).getName(), rti.get(i).getAmountOwed(), rti.get(i).getHouse_ID());
//				
//			}
			
			String dates = "2015/09";
			ArrayList<ReceiptHouseInfo> rhi = util.fetchReceiptInfoForHouse(dates);
			System.out.println(rhi.size());
			for(int i = 0; i < rhi.size(); i++){
				System.out.printf("Address: %s\nDate: %s\nTotal Bill: %.2f\n--------------------\n", 
						rhi.get(i).getAddress());
				
			}
			
			
			
			
			//-----------------EndTest-------------------------------------
			
			// ----------------DO NO DELETE--------------------------------
			if(SHOW_ROW_COUNT){
			String testH = String.format("SELECT * FROM house");
			System.out.println("Houses: " + util.fetchTenantSelection(testH).size());

			String testBM = String.format("SELECT * FROM billMonth");
			System.out.println("BillMonths: " + util.fetchTenantSelection(testBM).size());
			
			String testBPT = String.format("SELECT * FROM billPerTenant");
			System.out.println("BillPerTenants: " + util.fetchBillPerMonth(testBPT).size());

			String test1 = String.format("SELECT * FROM tenant");
			System.out.println("Tenants: " + util.fetchTenantSelection(test1).size());
			}
			// ----------------DO NO DELETE--------------------------------
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Database already exists");
		}
	}
	
	

	private static void createTenantTable(Connection conn, boolean input)
			throws SQLException {
		Statement stmt = conn.createStatement();
		if (input) {
			try {

				String dropTable = "drop table Tenant";
				stmt.execute(dropTable);
				System.out.println("Tenant table dropped.");

			} catch (Exception e) {

				System.out.println("Tenant table does not exist");
			}
		}

		String createTableSQL = "CREATE TABLE tenant ("
				+ " name varchar(100),"
				+ " active boolean,"
				+ " tenantType varchar(100),"
				+ " tenant_ID int NOT NULL GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1),"
				+ " PRIMARY KEY(Tenant_ID)" + ")";

		stmt.execute(createTableSQL);
	}

	private static void createHouseTable(Connection conn, boolean input)
			throws SQLException {

		Statement stmt = conn.createStatement();
		if (input) {
			try {
				String dropTable = "drop table house";
				stmt.execute(dropTable);
				System.out.println("house table dropped.");
			} catch (Exception e) {

				System.out.println("house table does not exist");
			}
		}

		String createTableSQL = "CREATE TABLE house ("
				+ " address varchar(100),"
				+ " numRooms integer,"
				+ " sqFt integer,"
				+ " house_ID int NOT NULL GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1),"
				+ " PRIMARY KEY(house_ID)" + ")";

		stmt.execute(createTableSQL);
	}

	private static void createBillMonthTable(Connection conn, boolean input)
			throws SQLException {

		Statement stmt = conn.createStatement();
		if (input) {
			try {
				String dropTable = "drop table billMonth";
				stmt.execute(dropTable);
				System.out.println("billMonth table dropped.");
			} catch (Exception e) {

				System.out.println("billMonth table does not exist");
			}
		}

		String createTableSQL = "CREATE TABLE billMonth ("
				+ " date char(7),"
				+ " fossilFuel double,"
				+ " electric double,"
				+ " other double,"
				+ " totalBill double,"
				+ " house_ID int,"
				+ " billMonth_ID int NOT NULL GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1),"
				+ " PRIMARY KEY(billMonth_ID)"
				+ ")";

		stmt.execute(createTableSQL);
	}

	private static void createBillMonthPerTenant(Connection conn, boolean input)
			throws SQLException {

		Statement stmt = conn.createStatement();
		if (input) {
			try {
				String dropTable = "drop table billPerTenant";
				stmt.execute(dropTable);
				System.out.println("billPerTenant table dropped.");
			} catch (Exception e) {

				System.out.println("billPerTenant table does not exist");
			}
		}

		String createTableSQL = "CREATE TABLE billPerTenant ("
				+ " billMonth_ID integer," 
				+ " house_ID integer,"
				+ " fte double," 
				+ " bill double," 
				+ " tenant_ID integer" 
				+ ")";

		stmt.execute(createTableSQL);
	}
}
