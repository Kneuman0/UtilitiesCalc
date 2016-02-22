package utilitiesCalculatorTest;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

import databaseModelClasses.*;
import utilitiesCalculator.DatabaseUtility;

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

}
