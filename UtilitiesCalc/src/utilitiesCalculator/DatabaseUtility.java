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
	public void addBillingMonthEntry(BillMonth billMonth) {

		try {
			Connection conn = DriverManager.getConnection(DB_URL);

			Statement stmt = conn.createStatement();
			

			String insertBillMonthEntry = "INSERT INTO billMonth(date, fossilFuel, electric, other)"
					+ String.format(" values ('%s', %.2f, %.2f, %.2f)",
							billMonth.getDate(), billMonth.getFossilFuelBill(),
							billMonth.getElectricityBill(),
							billMonth.getOtherBill());

			stmt.execute(insertBillMonthEntry);

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Need to think about how to get accurate FTE. Possibly process in
	 * controller and from observable arraylists then pass the value to this
	 * method. Other option is to put blank values in for bill and process the
	 * bill when its time to print the receipt by nest nesting a method in the
	 * receipt method that calculates the bill and then stores it
	 * 
	 * @param billPerTenant
	 * @param billMonth
	 */
	public void addBillPerTenantEntry(ArrayList<BillPerTenant> thisMonthsTenants) {

		try {
			Connection conn = DriverManager.getConnection(DB_URL);

			Statement stmt = conn.createStatement();
			for(int i = 0; i < thisMonthsTenants.size(); i++){
				String inserBillPerTenant = String.format("INSERT INTO billPerTenant("
						+ " billingMonth_ID, house_ID, fte, bill, tenant_ID)"
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
	 * Inserts 5 sample rows into the database, one with the name Kyle Neuman
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
				+ " VALUES('Rebecca Raferty', true, 'Full Time Renter')";
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

	public void inputSampleHouseEntries() {
		Connection conn = null;
		String insertSampleRow1 = "INSERT INTO house (address, numRooms, sqFt)"
				+ " VALUES('1800 Pensylvania Ave', 10, 10000)";
		String insertSampleRow2 = "INSERT INTO house (address, numRooms, sqFt)"
				+ " VALUES('1204 raymond Dr.', 3, 1700)";

		try {
			conn = DriverManager.getConnection(DB_URL);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(insertSampleRow1);
			stmt.executeUpdate(insertSampleRow2);

		} catch (SQLException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void inputSampleBillMonthEntries() {
		Connection conn = null;
		String insertSampleRow1 = "INSERT INTO billMonth (date, fossilFuel, electric, other)"
				+ " VALUES('09/2015', 75.50, 90.45, 25.00)";
		String insertSampleRow2 = "INSERT INTO billMonth (date, fossilFuel, electric, other)"
				+ " VALUES('10/2015', 80.00, 91.00, 25.00)";
		String insertSampleRow3 = "INSERT INTO billMonth (date, fossilFuel, electric, other)"
				+ " VALUES('11/2015', 78.50, 85.40, 25.00)";

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

	/**
	 * Returns an ArrayList of tenant based on SQL string passed in
	 * 
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
				bm.add(new BillMonth(result.getString(1), result.getInt(2),
						result.getInt(3), result.getInt(4), result.getInt(5)));
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
	
//	public ArrayList<>
}
