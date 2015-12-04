package utilitiesCalculator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseUtility {

		final static String DB_URL = "jdbc:derby:db/UtilitiesCalc";

		/**
		 * Populates tenant table using Tenant object passed in
		 * @param tenant
		 */
		public void addTenant(Tenant tenant){
			try {
				
				Connection conn = DriverManager.getConnection(DB_URL);
	    		Statement stmt = conn.createStatement();
	    		String insertTenant = "insert into tenant (name)"
	    				+ String.format(" values (%s)" , tenant.getName());
	    		
	    		stmt.execute(insertTenant);
	    		
	    		conn.close();
	    	} catch (SQLException e){
	    		e.printStackTrace();
	    	}

		}
		
		/**
		 * Populates house table with house object passed in
		 * @param house
		 */
		public void addHouseInfo(House house) {
			
			try {
	    		Connection conn = DriverManager.getConnection(DB_URL);
	    		
	    		Statement stmt = conn.createStatement();
	    		
	    		String insertHouse = "insert into house (address, numRooms, sqFt)"
	    				+ String.format(" values (%d, '%d','%d')" , house.getAddress(),
	    						house.getNumRooms(), house.getNumRooms());
	    		
	    		stmt.execute(insertHouse);
	    		
	    		conn.close();
	    	} catch (SQLException e){
	    		e.printStackTrace();
	    	}
		}
		
		/**
		 * Populates billingMonth table with billMonth object passed in
		 * @param billMonth
		 */
		public void addBillingMonthEntry(BillMonth billMonth){

			try {
	    		Connection conn = DriverManager.getConnection(DB_URL);
	    		
	    		Statement stmt = conn.createStatement();
	    		
	    		String insertBillMonthEntry = "insert into billingMonth(date, numRooms, sqFt)"
	    				+ String.format(" values ('%s', %.2f, %.2f,%.2f)" , billMonth.getDate(), billMonth.getFossilFuelBill(),
	    						billMonth.getElectricityBill(), billMonth.getOtherBill());
	    		
	    		stmt.execute(insertBillMonthEntry);
	    		
	    		conn.close();
	    	} catch (SQLException e){
	    		e.printStackTrace();
	    	}
		}
		
		/**
		 * Need to think about how to get accurate FTE. Possibly process in controller and from observable arraylists then pass
		 * the value to this method. Other option is to put blank values in for bill and process the bill when its time
		 * to print the receipt by nest nesting a method in the receipt method that calculates the bill and then stores it
		 * @param billPerTenant
		 * @param billMonth
		 */
		public void addBillPerTenantEntry(BillPerTenant billPerTenant, BillMonth billMonth){

			try {
	    		Connection conn = DriverManager.getConnection(DB_URL);
	    		
	    		Statement stmt = conn.createStatement();
	    		String getTenantID = String.format("SELECT tenant_ID FROM tenant WHERE name = '%s'",
	    				billPerTenant.getTenantName());
	    		Tenant tenant =  fetchTenantSelection(getTenantID).get(0);
	    		String getBillMonthID = String.format("SELECT billMonth_ID FROM billMonth"
	    				+ "WHERE date = '%s'", billMonth.getDate());
	    		BillMonth bm = fetchBillMonth(getBillMonthID).get(0);
	    		
	    		String getHouse = String.format("SELECT * FROM house");
	    		ResultSet result = stmt.executeQuery(getHouse);
	    		
	    		double bill = (billMonth.getFossilFuelBill() + billMonth.getElectricityBill()
	    				+ billMonth.getOtherBill()) * billPerTenant.getFte();
	    		
//	    		String insertBillPerTenant = "insert into billingMonth(date, numRooms, sqFt)"
//	    				+ String.format(" values ('%s', %.2f, %.2f,%.2f)" , bm.getDate(), result.getString(4),
//	    						billPerTenant.getTenantType(), billPerTenant.getFte(), 
//	    						);
//	    		
//	    		stmt.execute(insertBillPerTenant);
	    		
	    		conn.close();
	    	} catch (SQLException e){
	    		e.printStackTrace();
	    	}
		}
		
				
		/**
		 * Inserts 5 sample rows into the database, one with the name Kyle Neuman
		 */
		public void inputSampleTenantEntries() {
			Connection conn = null;
			String insertSampleRow1 = "INSERT INTO tenant (name, active, tenantType)"
					+ " VALUES('Telemundo Deltoro', false, 'Sublet')";
			String insertSampleRow2 = "INSERT INTO tenant (name, active, tenantType)"
					+ " VALUES('Kyle Cricketface', true, 'Landlord')";
			String insertSampleRow3 = "INSERT INTO tenant (name, active, tenantType)"
					+ " VALUES('Thomas Tutu', true, 'Sublet')";
			String insertSampleRow4 = "INSERT INTO tenant (name, active, tenantType)"
					+ " VALUES('Rebecca Raferty', false, 'Full Time Renter')";
			String insertSampleRow5 = "INSERT INTO tenant (name, active, tenantType)"
					+ " VALUES('Gilberto DeMayo', true, 'Sublet')";
			try {
				conn = DriverManager.getConnection(DB_URL);
				Statement stmt = conn.createStatement();
				stmt.executeUpdate(insertSampleRow1);
				stmt.executeUpdate(insertSampleRow2);
				stmt.executeUpdate(insertSampleRow3);
				stmt.executeUpdate(insertSampleRow4);
				stmt.executeUpdate(insertSampleRow5);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		/**
		 * Returns an ArrayList of tenant based on SQL string passed in
		 * @param SQLStatement
		 * @return
		 */
		public ArrayList<Tenant> fetchTenantSelection(String SQLStatement) {
			Connection conn = null;
			ResultSet result = null;
			ArrayList<Tenant> ten = new ArrayList<>();
			try {
				conn = DriverManager.getConnection(DB_URL);
				Statement stmt = conn.createStatement();
				result = stmt.executeQuery(SQLStatement);
				
				while(result.next()){
					
					ten.add(new Tenant(result.getString(1), result.getBoolean(2), result.getString(3)));
				}
				
				
				
			} catch (SQLException e) {
				
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return ten;
		}
		
		/**
		 * Returns ArrayList of House objects based on SQL statement passed in
		 * @param SQLStatement
		 * @return
		 */
		public ArrayList<House> fetchHouseSelection(String SQLStatement){
			Connection conn = null;
			ResultSet result = null;
			ArrayList<House> hou = new ArrayList<>();
			try {
				conn = DriverManager.getConnection(DB_URL);
				Statement stmt = conn.createStatement();
				result = stmt.executeQuery(SQLStatement);
				while(result.next()){
					hou.add(new House(result.getString(1), result.getInt(2), result.getInt(3)));
				}
				
			} catch (SQLException e) {
				
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return hou;
		}
		
		/**
		 * Returns ArrayList on BillMonth objects based on SQL Statement passed in
		 * @param SQLStatement
		 * @return
		 */
		public ArrayList<BillMonth> fetchBillMonth(String SQLStatement){
			Connection conn = null;
			ResultSet result = null;
			ArrayList<BillMonth> bm = new ArrayList<>();
			try {
				conn = DriverManager.getConnection(DB_URL);
				Statement stmt = conn.createStatement();
				result = stmt.executeQuery(SQLStatement);
				while(result.next()){
					bm.add(new BillMonth(result.getString(1), result.getInt(2), result.getInt(3), result.getInt(4)));
				}
				
			} catch (SQLException e) {
				
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return bm;
		}
		
		/**
		 * Returns ArrayList of BillPerTenant objects based on SQL statement passed in
		 * @param SQLStatement
		 * @return
		 */
		public ArrayList<BillPerTenant> fetchBillPerMonth(String SQLStatement){
			Connection conn = null;
			ResultSet result = null;
			ArrayList<BillPerTenant> bpt = new ArrayList<>();
			try {
				conn = DriverManager.getConnection(DB_URL);
				Statement stmt = conn.createStatement();
				result = stmt.executeQuery(SQLStatement);
				while(result.next()){
					bpt.add(new BillPerTenant(result.getString(1), result.getDouble(2), result.getString(3), result.getDouble(4)));
				}
				
			} catch (SQLException e) {
				
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return bpt;
		}

		
		public void updateDatabase(String SQLStatement) {
			Connection conn = null;
			try {
				conn = DriverManager.getConnection(DB_URL);
				Statement stmt = conn.createStatement();
				stmt.executeUpdate(SQLStatement);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}




