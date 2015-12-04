package utilitiesCalculator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseUtility {

		final static String DB_URL = "jdbc:derby:db/Tenant";

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
		
		public void addHouseInfo(House house) {
			
			try {
	    		Connection conn = DriverManager.getConnection("jdbc:derby:db/Tenant;");
	    		
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
		 * Utility method for dropping a table named Employee
		 */
		public void dropEmployeeTableFromPersonnelDB() {
			Connection connDrop = null;
			try {
				connDrop = DriverManager.getConnection(DB_URL);
				Statement stmt = connDrop.createStatement();
				stmt.execute("DROP TABLE Tenant");
			} catch (SQLException e) {
				System.out.println("Tenant table does not exist");
			} finally {
				try {
					connDrop.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		/**
		 * Inserts 5 sample rows into the database, one with the name Kyle Neuman
		 */
		public void inputSampleTenantEntries() {
			Connection conn = null;
			String insertSampleRow1 = "INSERT INTO tenant (name, active)"
					+ " VALUES('Telemundo Deltoro', false)";
			String insertSampleRow2 = "INSERT INTO tenant (name)"
					+ " VALUES('Kyle Cricketface', true)";
			String insertSampleRow3 = "INSERT INTO tenant (name)"
					+ " VALUES('Thomas Tutu', true)";
			String insertSampleRow4 = "INSERT INTO tenant (name)"
					+ " VALUES('Rebecca Raferty', false)";
			String insertSampleRow5 = "INSERT INTO tenant (name)"
					+ " VALUES('Gilberto DeMayo', true)";
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

//		public Employee retriveTable() {
//			Connection conn = null;
//			ResultSet results = null;
//			String firstRow = "SELECT * FROM Employee";
//			// + "WHERE name='Kyle Neuman'";
//			try {
//				conn = DriverManager.getConnection(DB_URL);
//				Statement stmt = conn.createStatement();
//				results = stmt.executeQuery(firstRow);
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return results;
//		}
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
					ten.add(new Tenant(result.getString(1), result.getBoolean(2)));
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
		
		public ArrayList<BillMonth> fetchBillMonth(String SQLStatement){
			Connection conn = null;
			ResultSet result = null;
			ArrayList<BillMonth> bm = new ArrayList<>();
			try {
				conn = DriverManager.getConnection(DB_URL);
				Statement stmt = conn.createStatement();
				result = stmt.executeQuery(SQLStatement);
				while(result.next()){
					bm.add(new BillMonth(result.getInt(1), result.getInt(2), result.getInt(3)));
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




