package utilitiesCalculator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseUtility {

		final static String DB_URL = "jdbc:derby:db/Tenant";

		public void addTenant(String name, String sharingRoom, String occupants, double area, int ID)
		{
			try {

				
	    		Connection conn = DriverManager.getConnection(DB_URL);
	    		
	    		Statement stmt = conn.createStatement();
	    		
	    		String insertPerson = "insert into Tenant (name, rooms, occupant, Tenant_ID)"
	    				+ String.format(" values (%s, %s, %d, %.2f, %s)" , 
	    								name, sharingRoom, occupants, area, ID);
	    		
	    		stmt.execute(insertPerson);
	    		
	    		conn.close();
	    	} catch (SQLException e){
	    		e.printStackTrace();
	    	}

		}
		
		static void addHouseInfo(House house) {
			
			
			
			
			try {
	    		Connection conn = DriverManager.getConnection("jdbc:derby:db/Tenant;");
	    		
	    		Statement stmt = conn.createStatement();
	    		
//	    		String insertPerson = "insert into Tenant (address, building, rooms, area, numOfTenants, occPeriod, Tenant_ID)"
//	    				+ String.format(" values (%d, '%s','%s', %.2f)" , address, building, rooms, ID);
	    		
//	    		stmt.execute(insertPerson);
//	    		
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
		public void inputSampleEntries() {
			Connection conn = null;
			String insertSampleRow1 = "INSERT INTO Employee (name, position, payRtHrly)"
					+ " VALUES('Telemundo Deltoro', 'stapler operator', 15.00)";
			String insertSampleRow2 = "INSERT INTO Employee (name, position, payRtHrly)"
					+ "VALUES('Smithery Smithonson', 'scissor operator', 15.00)";
			String insertSampleRow3 = "INSERT INTO Employee (name, position, payRtHrly)"
					+ "VALUES('Roger Rabit', 'staple remover', 15.00)";
			String insertSampleRow4 = "INSERT INTO Employee (name, position, payRtHrly)"
					+ "VALUES('Ignious Rockface', 'paper clip getter', 15.00)";
			String insertSampleRow5 = "INSERT INTO Employee (name, position, payRtHrly)"
					+ "VALUES('Kyle Neuman', 'Sole Proprieter', 100.00)";
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

//		public ArrayList<Employee> fetchSelection(String SQLStatement) throws InvalidHourlyRateException {
//			Connection conn = null;
//			ResultSet result = null;
//			ArrayList<Employee> emp = new ArrayList<>();
//			try {
//				conn = DriverManager.getConnection(DB_URL);
//				Statement stmt = conn.createStatement();
//				result = stmt.executeQuery(SQLStatement);
//				while(result.next()){
//					emp.add(new Employee(result.getString(1), result.getString(2), result.getDouble(3), result.getInt(4)));
//				}
//				
//				if(emp.isEmpty()) throw new InvalidHourlyRateException("0");
//			} catch (SQLException e) {
//				throw new InvalidHourlyRateException("0");
//			} finally {
//				try {
//					conn.close();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			
//			
//			return emp;
//
//		}

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




