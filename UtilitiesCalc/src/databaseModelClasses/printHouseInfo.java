package databaseModelClasses;

public class printHouseInfo {

	/**
	 * Test class for printing receipt
	 * @param args
	 */
	public static void main(String[] args) {
		
		String houseDate = "10/2012";
		String houseAddress = "123 Main St";
		double total = 70.00;
		double sqft = 0.45;
		double room = 100.00;
		String dash = "";
		
		StringBuilder houseInfo = new StringBuilder();
		// header for receipt
		String idHouse = String.format("|%-8s|%-25s|%-12s|%-12s|%-12s|\n", "Date", "Address",
				"Total Bill", "Cost / sqft", "Cost / Room");
		
		for(int i = 0; i <= idHouse.length(); i++){
			dash += "-";
		}
		
		String tenantInfo = String.format("|%-8s|%-25s|%-12.2f|%-12s|%-12.2f|", houseDate, houseAddress, 
											 total, sqft, room);
		
		houseInfo.append(idHouse);
		houseInfo.append(String.format("%s\n", dash));
		houseInfo.append(tenantInfo);
		System.out.println(houseInfo);
		
	}
}
