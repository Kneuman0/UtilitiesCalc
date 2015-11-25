package utilitiesCalculator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBReader {

	public static void main(String[] args) {
		
		try{
			Connection conn = DriverManager.getConnection("jdbc:derby:db/Tenant;");
			
			Statement stmt = conn.createStatement();
			
			//1. Create  Select statement SQL
			String select = "select name, address, building, rooms, area, numOfTenants, occPeriod, Tenant_ID from Tenant";
			
			//Execute query
			ResultSet results = stmt.executeQuery(select);
			
			//Read the results
			while(results.next()){
				System.out.printf("%s  |  %s  |  %.2f  |  %d  %n", results.getString("name"), results.getString("address")
						, results.getString("building"), results.getInt("rooms"), results.getInt("numOfTenants") 
						, results.getBoolean("occPeriod"), results.getInt("Tenant_ID"));
			}
			
			//Close the Connection
			conn.close();
			
		} catch (SQLException e){
			e.printStackTrace();
		}

	}

}
