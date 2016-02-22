package utilitiesCalculatorTest;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;

import org.junit.Test;

import databaseModelClasses.*;
import utilitiesCalculator.DatabaseUtility;
import utilitiesCalculator.InvalidUserEntryException;

public class DatabaseUtilityTest {

	@Test
	public void testBillMonthStorageAndRetrieval() {
		DatabaseUtility util = new DatabaseUtility();
		BillMonth billIn = new BillMonth("2015/07", 100, 120, 25, 245, 15);
		util.addBillingMonthEntry(billIn);
		String SQLStatement = "SELECT * FROM billMonth WHERE date = '2015/07'";
		BillMonth billOut = util.fetchBillMonth(SQLStatement).get(0);
		assertThat(billIn.getDate(), is(billOut.getDate()));
		assertThat(billIn.getFossilFuelBill(), is(billOut.getFossilFuelBill()));
		assertThat(billIn.getElectricityBill(), is(billOut.getElectricityBill()));
		assertThat(billIn.getOtherBill(), is(billOut.getOtherBill()));
		assertThat(billIn.getTotalBill(), is(billOut.getTotalBill()));
		assertThat(billIn.getHouse_ID(), is(billOut.getHouse_ID()));
	}
	
	@Test
	public void testTenantStorageAndRetrieval(){
		DatabaseUtility util = new DatabaseUtility();
		Tenant tenantIn = new Tenant("Darcy", true, "sublet");
		util.addTenant(tenantIn);
		String SQLStatement = "SELECT * FROM tenant WHERE name = 'Darcy'";
		Tenant tenantOut = util.fetchTenantSelection(SQLStatement).get(0);
		assertThat(tenantIn.getName(), is(tenantOut.getName()));
		assertThat(tenantIn.isActive(), is(tenantOut.isActive()));
		assertThat(tenantIn.tenantType, is(tenantOut.tenantType));
	}
	
	@Test
	public void testBillPerTenantStorageAndRetrieval(){
		// Initialize class and create BillPerMonth object
		DatabaseUtility util = new DatabaseUtility();
		BillPerTenant bptIn = new BillPerTenant(10, 8, 1, 250, 5);
		
		//Initalize ArrayList to hold 1 BillPerTenant Object to test and add it
		ArrayList<BillPerTenant> tenantBillsIn = new ArrayList<>();
		tenantBillsIn.add(bptIn);
		
		// Send ArrayList to database method for storage
		util.addBillPerTenantEntry(tenantBillsIn);
		
		// Retrieve ArrayList populated with single BillPerTenant object
		String SQLStatement = "SELECT * FROM billPerTenant WHERE tenant_ID = 5";
		
		// Store single object contained in ArrayList and test against original 
		BillPerTenant bptOut = util.fetchBillPerMonth(SQLStatement).get(0);
		assertThat(bptIn.getBill(), is(bptOut.getBill()));
		assertThat(bptIn.getBillMonth_ID(), is(bptOut.getBillMonth_ID()));
		assertThat(bptIn.getFte(), is(bptOut.getFte()));
		assertThat(bptIn.getHouse_ID(), is(bptOut.getHouse_ID()));
		assertThat(bptIn.getTenant_ID(), is(bptOut.getTenant_ID()));
	}
	
	@Test
	public void testHouseStorageAndRetrieval(){
		DatabaseUtility util = new DatabaseUtility();
		House houseIn = new House("123 TestMe ct.", 5, 3600);
		util.addHouseInfo(houseIn);
		String SQLStatement = "SELECT * FROM house WHERE address = '123 TestMe ct.'";
		House houseOut = util.fetchHouseSelection(SQLStatement).get(0);
		assertThat(houseIn.getAddress(), is(houseOut.getAddress()));
		assertThat(houseIn.getNumRooms(), is(houseOut.getNumRooms()));
		assertThat(houseIn.getSqFt(), is(houseOut.getSqFt()));
	}
	
	@Test
	public void testReceiptHouseInfoRetrieval(){
		DatabaseUtility util = new DatabaseUtility();
		ReceiptHouseInfo receiptInfo = util.fetchHouseSummary("1800 Pensylvania Ave").get(0);
		assertThat(receiptInfo.getElectric(), is(90.45));
		assertThat(receiptInfo.getFossilFuel(), is(75.50));
		assertThat(receiptInfo.getOtherBills(), is(25.0));
		assertThat(receiptInfo.getTotalBill(), is(190.95));
		assertThat(receiptInfo.getAddress(), is("1800 Pensylvania Ave"));
		
	}
	
	@Test
	public void testReceiptTenantInfoRetrieval(){
		DatabaseUtility util = new DatabaseUtility();
		ReceiptTenantInfo receiptTenant = util.fetchReceiptInfoForTenant("2015/09").get(0);
		assertThat(receiptTenant.getName(), is("Kyle Cricketface"));
		assertThat(receiptTenant.getAmountOwed(), is(0.0));
		assertThat(receiptTenant.getFte(), is(0.0));
		assertThat(receiptTenant.getTenantType(), is("Landlord"));
	}
	
	@Test
	public void testModifyBillDateMethod(){
		DatabaseUtility util = new DatabaseUtility();
		assertThat(util.modifyBillDate("12/2016"), is("2016/12"));
	}
	
	@Test
	public void testModifyBillDateForInvalidUserInput(){
		DatabaseUtility util = new DatabaseUtility();
		try {
			util.modifyBillDate("12/24/2015");
			fail("InvalidUserInputException never thrown for date: 12/24/2015");
		} catch (InvalidUserEntryException e) {
			assertTrue(true);
		}
	}
}
