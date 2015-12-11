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
	 * 
	 * @param tenant
	 */
	public void addTenant(Tenant tenant) {
		try {

			Connection conn = DriverManager.getConnection(DB_URL);
			Statement stmt = conn.createStatement();
			String insertTenant = "INSERT INTO tenant (name, active, tenantType)"
					+ String.format(" values ('%s', %b, '%s')",
							tenant.getName(), tenant.isActive(),
							tenant.tenantType);

			stmt.execute(insertTenant);

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Populates house table with house object passed in
	 * 
	 * @param house
	 */
	public void addHouseInfo(House house) {

		try {
			Connection conn = DriverManager.getConnection(DB_URL);

			Statement stmt = conn.createStatement();

			String insertHouse = "INSERT INTO house (address, numRooms, sqFt)"
					+ String.format(" values ('%s', %d, %d)",
							house.getAddress(), house.getNumRooms(),
							house.getNumRooms());

			stmt.execute(insertHouse);

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Populates billingMonth table with billMonth object passed in
	 * 
	 * @param billMonth
	 */
	public void addBillingMonthEntry(BillMonth billMonth) {				//REQ#7

		try {
			Connection conn = DriverManager.getConnection(DB_URL);

			Statement stmt = conn.createStatement();
			

			String insertBillMonthEntry = "INSERT INTO billMonth(date, fossilFuel, electric, other, totalBill, house_ID)"
					+ String.format(" values ('%s', %.2f, %.2f, %.2f, %.2f, %d)",
							billMonth.getDate(), billMonth.getFossilFuelBill(),
							billMonth.getElectricityBill(),
							billMonth.getOtherBill(), billMonth.getTotalBill(), billMonth.getHouse_ID());

			stmt.execute(insertBillMonthEntry);

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Inserts all active tenants living the specified house at the specified month into the db.
	 * Must pass in a fully populated ArrayList of BillPerTenant objects
	 * 
	 * @param thisMonthsTenants
	 */
	public void addBillPerTenantEntry(ArrayList<BillPerTenant> thisMonthsTenants) {			//REQ#7

		try {
			Connection conn = DriverManager.getConnection(DB_URL);

			Statement stmt = conn.createStatement();
			for(int i = 0; i < thisMonthsTenants.size(); i++){
				String inserBillPerTenant = String.format("INSERT INTO billPerTenant("
						+ " billMonth_ID, house_ID, fte, bill, tenant_ID)"
						+ " VALUES(%d, %d, %f, %.2f, %d)",
						thisMonthsTenants.get(i).getBillMonth_ID(),
						thisMonthsTenants.get(i).getHouse_ID(),
						thisMonthsTenants.get(i).getFte(),
						thisMonthsTenants.get(i).getBill(),
						thisMonthsTenants.get(i).getTenant_ID());
				stmt.execute(inserBillPerTenant);
			}
			//
			// 

			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Used to modify rows in the db. Must pass in a complete and executable SQL statement
	 * @param SQLStatement
	 */
	public void modifyDatabase(String SQLStatement) {
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(DB_URL);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(SQLStatement);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Inserts 5 dummy tenant rows into the tenant table of the db for testing purposes
	 */
	public void inputSampleTenantEntries() {
		Connection conn = null;
		String insertSampleRow1 = "INSERT INTO tenant (name, active, tenantType)"
				+ " VALUES('Telemundo Deltoro', true, 'Sublet')";
		String insertSampleRow2 = "INSERT INTO tenant (name, active, tenantType)"
				+ " VALUES('Kyle Cricketface', true, 'Landlord')";
		String insertSampleRow3 = "INSERT INTO tenant (name, active, tenantType)"
				+ " VALUES('Thomas Tutu', true, 'Sublet')";
		String insertSampleRow4 = "INSERT INTO tenant (name, active, tenantType)"
				+ " VALUES('Rebecca Raferty', true, 'Standard')";
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
	 * Inserts dummy house entries into the house table in the db for testing purposes
	 */
	public void inputSampleHouseEntries() {
		Connection conn = null;
		String insertSampleRow1 = "INSERT INTO house (address, numRooms, sqFt)"
				+ " VALUES('1800 Pensylvania Ave', 10, 10000)";
//		String insertSampleRow2 = "INSERT INTO house (address, numRooms, sqFt)"
//				+ " VALUES('1204 raymond Dr.', 3, 1700)";

		try {
			conn = DriverManager.getConnection(DB_URL);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(insertSampleRow1);
//			stmt.executeUpdate(insertSampleRow2);

		} catch (SQLException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Inserts dummy bill month entries into the billMonth table of the db for testing purposes
	 */
	public void inputSampleBillMonthEntries() {
		Connection conn = null;
		String insertSampleRow1 = "INSERT INTO billMonth (date, fossilFuel, electric, other, totalBill, house_ID)"
				+ " VALUES('2015/09', 75.50, 90.45, 25.00, 190.95, 1)";
		String insertSampleRow2 = "INSERT INTO billMonth (date, fossilFuel, electric, other, totalBill, house_ID)"
				+ " VALUES('2015/10', 80.00, 91.00, 25.00, 196.0, 1)";
		String insertSampleRow3 = "INSERT INTO billMonth (date, fossilFuel, electric, other, totalBill, house_ID)"
				+ " VALUES('2015/11', 78.50, 85.40, 25.00, 188.9, 1)";

		try {
			conn = DriverManager.getConnection(DB_URL);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(insertSampleRow1);
			stmt.executeUpdate(insertSampleRow2);
			stmt.executeUpdate(insertSampleRow3);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void inputSampleBillPerTenantEntries(){
		Connection conn = null;
		String insertSampleRow1a = "INSERT INTO billPerTenant (billMonth_ID, house_ID, fte, bill, tenant_ID)"
				+ " VALUES(1, 1, .25, 44.0, 1)";
		String insertSampleRow2a = "INSERT INTO billPerTenant (billMonth_ID, house_ID, fte, bill, tenant_ID)"
				+ " VALUES(1, 1, 0, 0, 2)";
		String insertSampleRow3a = "INSERT INTO billPerTenant (billMonth_ID, house_ID, fte, bill, tenant_ID)"
				+ " VALUES(1, 1, 1, 176.0, 4)";
		
		String insertSampleRow1b = "INSERT INTO billPerTenant (billMonth_ID, house_ID, fte, bill, tenant_ID)"
				+ " VALUES(2, 1, .25, 40.0, 1)";
		String insertSampleRow2b = "INSERT INTO billPerTenant (billMonth_ID, house_ID, fte, bill, tenant_ID)"
				+ " VALUES(2, 1, 0, 0.0, 2)";
		String insertSampleRow3b = "INSERT INTO billPerTenant (billMonth_ID, house_ID, fte, bill, tenant_ID)"
				+ " VALUES(2, 1, 1, 160.0, 4)";

		try {
			conn = DriverManager.getConnection(DB_URL);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(insertSampleRow1a);
			stmt.executeUpdate(insertSampleRow2a);
			stmt.executeUpdate(insertSampleRow3a);
			stmt.executeUpdate(insertSampleRow1b);
			stmt.executeUpdate(insertSampleRow2b);
			stmt.executeUpdate(insertSampleRow3b);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Returns an ArrayList of tenant based on SQL string passed in
	 * 
	 * @param SQLStatement
	 * @return
	 */
	public ArrayList<Tenant> fetchTenantSelection(String SQLStatement) {    //REQ#8
		Connection conn = null;
		ResultSet result = null;
		ArrayList<Tenant> ten = new ArrayList<>();
		try {
			conn = DriverManager.getConnection(DB_URL);
			Statement stmt = conn.createStatement();
			result = stmt.executeQuery(SQLStatement);
			int i = 0;
			while (result.next()) {

				ten.add(new Tenant(result.getString(1), result.getBoolean(2),
						result.getString(3), result.getInt(4)));
				
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
	 * 
	 * @param SQLStatement
	 * @return
	 */
	public ArrayList<House> fetchHouseSelection(String SQLStatement) {
		Connection conn = null;
		ResultSet result = null;
		ArrayList<House> hou = new ArrayList<>();
		try {
			conn = DriverManager.getConnection(DB_URL);
			Statement stmt = conn.createStatement();
			result = stmt.executeQuery(SQLStatement);
			while (result.next()) {
				hou.add(new House(result.getString(1), result.getInt(2), result
						.getInt(3), result.getInt(4)));
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
	 * 
	 * @param SQLStatement
	 * @return
	 */
	public ArrayList<BillMonth> fetchBillMonth(String SQLStatement) {
		Connection conn = null;
		ResultSet result = null;
		ArrayList<BillMonth> bm = new ArrayList<>();
		try {
			conn = DriverManager.getConnection(DB_URL);
			Statement stmt = conn.createStatement();
			result = stmt.executeQuery(SQLStatement);
			while (result.next()) {
				bm.add(new BillMonth(result.getString(1), result.getDouble(2),
						result.getDouble(3), result.getDouble(4), result.getDouble(5),
						result.getInt(5), result.getInt(6)));
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
	
	public int fetchBillMonthID(String date){
		Connection conn = null;
		ResultSet result = null;
		String getBMID = String.format("SELECT billMonth_ID FROM billMonth WHERE date = '%s'", date);
		int id = 0;
				try {
			conn = DriverManager.getConnection(DB_URL);
			Statement stmt = conn.createStatement();
			result = stmt.executeQuery(getBMID);
			while (result.next()) {
				id = result.getInt("billMonth_ID");
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
		return id;
	}

	/**
	 * Returns ArrayList of BillPerTenant objects based on SQL statement passed
	 * in
	 * 
	 * @param SQLStatement
	 * @return
	 */
	public ArrayList<BillPerTenant> fetchBillPerMonth(String SQLStatement) {
		Connection conn = null;
		ResultSet result = null;
		ArrayList<BillPerTenant> bpt = new ArrayList<>();
		try {
			conn = DriverManager.getConnection(DB_URL);
			Statement stmt = conn.createStatement();
			result = stmt.executeQuery(SQLStatement);
			while (result.next()) {
				 bpt.add(new BillPerTenant(result.getInt(1),
				 result.getInt(2), result.getDouble(3),
				 result.getDouble(4), result.getInt(5)));
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

	/**
	 * Drops any table in the db with the a name matching the string passed in
	 * 
	 * @param tableName
	 */
	public void dropTable(String tableName) {
		String dropTable = String.format("DROP TABLE %s", tableName);
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(DB_URL);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(dropTable);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Deletes all rows in the tenant table
	 * in the db with the a name matching the string passed in
	 * 
	 * @param tenantName
	 */
	public void deleteTenant(String tenantName) {
		Connection conn = null;
		String deleteTenant = String.format("DELETE FROM tenant"
				+ " WHERE name = '%s'", tenantName);
		try {
			conn = DriverManager.getConnection(DB_URL);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(deleteTenant);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Deletes all rows in the house table
	 * in the db with the an address matching the string passed in
	 * 
	 * @param address
	 */
	public void deleteHouse(String address){
		Connection conn = null;
		String deleteHouse = String.format("DELETE FROM house"
				+ " WHERE address = '%s'", address);
		try {
			conn = DriverManager.getConnection(DB_URL);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(deleteHouse);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * returns an arrayList of ReceiptTenantInfo objects
	 * that relate to the date passed in
	 * 
	 * @return
	 */
	public ArrayList<ReceiptTenantInfo> fetchReceiptInfoForTenant(String date){			//REQ#8
		ArrayList<ReceiptTenantInfo> tenantInfo = new ArrayList<ReceiptTenantInfo>();
		String SQLStatement = String.format(
				"SELECT tenant.name, "
				+ " billMonth.date, "
				+ " tenant.tenantType, billPerTenant.fte,"
				+ "  billPerTenant.bill, house.house_ID"
				+ " FROM tenant, billPerTenant, "
				+ " billMonth, house"
				+ " WHERE billPerTenant.tenant_ID = tenant.tenant_ID"
				+ " AND billMonth.billMonth_ID = billPerTenant.billMonth_ID"
				+ " AND house.house_ID = billPerTenant.house_ID"
				+ " AND billMonth.date = '%s'", date);
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(DB_URL);
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(SQLStatement);
			while (result.next()) {
				 tenantInfo.add(new ReceiptTenantInfo(result.getString(1),
				 result.getString(2), result.getString(3),
				 result.getDouble(4), result.getDouble(5), result.getInt(6)));
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return tenantInfo;
		
	}
	
	/**
	 * returns an arrayList of ReceiptHouseInfo objects
	 * that relate to the date passed in
	 * 
	 * @return
	 */
	public ArrayList<ReceiptHouseInfo> fetchReceiptInfoForHouse(String date, int house_ID){
		ArrayList<ReceiptHouseInfo> houseInfo = new ArrayList<ReceiptHouseInfo>();
		String SQLStatement = String.format(
				"SELECT house.address, "
				+ " house.numRooms,"
				+ " house.sqFt, billMonth.totalBill,"
				+ " billMonth.fossilFuel, billMonth.electric,"
				+ " billMonth.other, house.house_ID"
				+ " FROM billMonth, house"
				+ " WHERE house.house_ID = %d"
				+ " AND billMonth.date = '%s'", house_ID, date);
		
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(DB_URL);
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(SQLStatement);
			while (result.next()) {
				 houseInfo.add(new ReceiptHouseInfo(result.getString(1),
				 result.getInt(2), result.getInt(3),
				 result.getDouble(4), result.getDouble(5), result.getDouble(6),
						 result.getDouble(7), result.getInt(8)));
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return houseInfo;
	}
	
	public String getWhiteSpace(String s, int columnWidth){
		int length = columnWidth - s.length();
		String spaces = "";
		for(int i = 0; i < length;i++){
			spaces += " ";
		}
		return spaces;
	}
	
	/**
	 * Uses String.split to change the bill from month first year last to year
	 * first month last for sorting purposes. Untested
	 * 
	 * Tested/Working
	 * 
	 * @return
	 */
	public String modifyBillDate(String dbDateIn) {
		String[] date = dbDateIn.split("/");
		String dbDate = date[1] + "/" + date[0];
		return dbDate;

	}
	
	public ArrayList<BillPerTenant> fetchTenantTotals(String tenantName){
		ArrayList<BillPerTenant> tenantInfo = new ArrayList<BillPerTenant>();
		String SQLStatement = String.format(
				"SELECT billPerTenant.fte, billPerTenant.bill, "
				+ " tenant.name, tenant.tenantType "
				+ " FROM billPerTenant, tenant"
				+ " WHERE billPerTenant.tenant_ID = tenant.tenant_ID "
				+ " AND tenant.name = '%s'", tenantName);
		
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(DB_URL);
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(SQLStatement);
			while (result.next()) {
				 tenantInfo.add(new BillPerTenant(0, 0, 0, result.getDouble(1),
						 result.getString(3), result.getDouble(2),result.getString(4)));
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return tenantInfo;
	}
	
	public ArrayList<ReceiptHouseInfo> fetchHouseSummary(String address){
		ArrayList<ReceiptHouseInfo> tenantInfo = new ArrayList<ReceiptHouseInfo>();
		String SQLStatement = String.format(
				"SELECT house.numRooms, house.sqFt,"
				+ " billMonth.totalBill, billMonth.fossilFuel, "
				+ " billMonth.electric, billMonth.other"
				+ " FROM billMonth, house"
				+ " WHERE house.house_ID = billMonth.house_ID "
				+ " AND house.address = '%s'", address);
		
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(DB_URL);
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(SQLStatement);
			while (result.next()) {
				 tenantInfo.add(new ReceiptHouseInfo(address,
						 result.getInt(1), result.getInt(2),result.getDouble(3),
						 result.getDouble(4), result.getDouble(5), result.getDouble(6), 0));
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return tenantInfo;
	}
	
	
}
