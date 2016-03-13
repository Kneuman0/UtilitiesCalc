package biz.personalAcademics.utilitiesCalculatorTest;

import static org.junit.Assert.*; 
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.core.StringContains.containsString;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import biz.personalAcademics.databaseModelClasses.*;
import biz.personalAcademics.utilitiesCalculator.DatabaseUtility;
import biz.personalAcademics.utilitiesCalculator.InvalidUserEntryException;
import biz.personalAcademics.utilitiesCalculator.UtilitesCalcMain;

public class DatabaseUtilityTest {
	
	@Rule
	public ExpectedException badInput = ExpectedException.none();

	/**
	 * Setup connection to database for testing
	 */
	@Before
	public void setSystemHome(){
		Connection conn = null;
		try {
			Properties p = System.getProperties();
			
			// Sets the system home for the database
			p.setProperty("derby.system.home", "../UtilitiesCalc/database");
			conn = DriverManager.getConnection("jdbc:derby:data/UtilitiesCalc");

		} catch (SQLException e) {
			DatabaseUtility.createDBTables();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	@Test
	public void testBillMonthStorageAndRetrieval() {
		//Store BillMonth
		BillMonth billIn = new BillMonth("2015/07", 100, 120, 25, 245, 15);
		DatabaseUtility.addBillingMonthEntry(billIn);
		
		//Retrieve same BillMonth
		String SQLStatement = "SELECT * FROM billMonth WHERE date = '2015/07'";
		BillMonth billOut = DatabaseUtility.fetchBillMonth(SQLStatement).get(0);
		
		//Test Retrieved BillMonth against stored BillMonth
		assertThat(billIn.getDate(), is(billOut.getDate()));
		assertThat(billIn.getFossilFuelBill(), is(billOut.getFossilFuelBill()));
		assertThat(billIn.getElectricityBill(), is(billOut.getElectricityBill()));
		assertThat(billIn.getOtherBill(), is(billOut.getOtherBill()));
		assertThat(billIn.getTotalBill(), is(billOut.getTotalBill()));
		assertThat(billIn.getHouse_ID(), is(billOut.getHouse_ID()));
	}
	
	@Test
	public void testTenantStorageAndRetrieval(){
		//Store Tenant
		Tenant tenantIn = new Tenant("Darcy", true, "sublet");
		DatabaseUtility.addTenant(tenantIn);
		
		//Retrieve same Tenant
		String SQLStatement = "SELECT * FROM tenant WHERE name = 'Darcy'";
		Tenant tenantOut = DatabaseUtility.fetchTenantSelection(SQLStatement).get(0);
		
		//Test Retrieved Tenant against stored Tenant
		assertThat(tenantIn.getName(), is(tenantOut.getName()));
		assertThat(tenantIn.isActive(), is(tenantOut.isActive()));
		assertThat(tenantIn.tenantType, is(tenantOut.tenantType));
	}
	
	@Test
	public void testBillPerTenantStorageAndRetrieval(){
		// create BillPerMonth object
		BillPerTenant bptIn = new BillPerTenant(10, 8, 1, 250, 5);
		
		// Initialize ArrayList to hold 1 BillPerTenant Object to test and add it
		ArrayList<BillPerTenant> tenantBillsIn = new ArrayList<>();
		tenantBillsIn.add(bptIn);
		
		// Send ArrayList to database method for storage
		DatabaseUtility.addBillPerTenantEntry(tenantBillsIn);
		
		// Retrieve ArrayList populated with single BillPerTenant object
		String SQLStatement = "SELECT * FROM billPerTenant WHERE tenant_ID = 5";
		
		// Store single object contained in ArrayList and test against original 
		BillPerTenant bptOut = DatabaseUtility.fetchBillPerMonth(SQLStatement).get(0);
		assertThat(bptIn.getBill(), is(bptOut.getBill()));
		assertThat(bptIn.getBillMonth_ID(), is(bptOut.getBillMonth_ID()));
		assertThat(bptIn.getFte(), is(bptOut.getFte()));
		assertThat(bptIn.getHouse_ID(), is(bptOut.getHouse_ID()));
		assertThat(bptIn.getTenant_ID(), is(bptOut.getTenant_ID()));
	}
	
	@Test
	public void testHouseStorageAndRetrieval(){
		//Store House
		House houseIn = new House("123 TestMe ct.", 5, 3600);
		DatabaseUtility.addHouseInfo(houseIn);
		
		//Retrieve same House
		String SQLStatement = "SELECT * FROM house WHERE address = '123 TestMe ct.'";
		House houseOut = DatabaseUtility.fetchHouseSelection(SQLStatement).get(0);
		
		//Test Retrieved House against stored House
		assertThat(houseIn.getAddress(), is(houseOut.getAddress()));
		assertThat(houseIn.getNumRooms(), is(houseOut.getNumRooms()));
		assertThat(houseIn.getSqFt(), is(houseOut.getSqFt()));
	}
	
	@Test
	public void testReceiptHouseInfoRetrieval(){
		// Populate ReceiptHouseInfo from database
		ReceiptHouseInfo receiptInfo = DatabaseUtility.fetchHouseSummary("1800 Pensylvania Ave").get(0);
		
		// Validate ReceiptHouseInfo contents based off mock data
		assertThat(receiptInfo.getElectric(), is(90.45));
		assertThat(receiptInfo.getFossilFuel(), is(75.50));
		assertThat(receiptInfo.getOtherBills(), is(25.0));
		assertThat(receiptInfo.getTotalBill(), is(190.95));
		assertThat(receiptInfo.getAddress(), is("1800 Pensylvania Ave"));
		
	}
	
	@Test
	public void testReceiptTenantInfoRetrieval(){
		// Populate ReceiptTenantInfo from databaseDatabaseUtility
		ReceiptTenantInfo receiptTenant = DatabaseUtility.fetchReceiptInfoForTenant("2015/09").get(0);
		
		// Validate ReceiptTenantInfo contents based off mock data
		assertThat(receiptTenant.getName(), is("Kyle Cricketface"));
		assertThat(receiptTenant.getAmountOwed(), is(0.0));
		assertThat(receiptTenant.getFte(), is(0.0));
		assertThat(receiptTenant.getTenantType(), is("Landlord"));
	}
	
	@Test
	public void testModifyBillDateMethod(){
		assertThat(DatabaseUtility.modifyBillDate("12/2016"), is("2016/12"));
	}
	
	@Test
	public void testModifyBillDateForInvalidUserInput(){
		badInput.expect(InvalidUserEntryException.class);
		badInput.expectMessage(containsString("12/24/2015"));
		DatabaseUtility.modifyBillDate("12/24/2015");
		
	}
}
