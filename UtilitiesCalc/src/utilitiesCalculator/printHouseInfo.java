package utilitiesCalculator;

public class printHouseInfo {

	public static void main(String[] args) {
		
		String houseDate = "10/2012";
		String houseAddress = "123 Main St";
		double fossil = 20.00;
		double electric = 35.00;
		double other = 15.00;
		double total = 70.00;
		double sqft = 0.45;
		double room = 100.00;
		String dash = "";
		
		StringBuilder houseInfo = new StringBuilder();
		String idHouse = String.format("|%-8s|%-12s|%-12s|%-10s|%-6s|%-12s|%-10s|%-10s|\n", "Date", "Address", "Fossil Fuel", 
									   "Electric", "Other", "Total Bill", "Cost / sqft", "Cost / Room");
		
		for(int i = 0; i <= idHouse.length(); i++){
			dash += "-";
		}
		
		String tenantInfo = String.format("|%-8s|%-12s|%-12.2f|%-10.2f|%-6.2f|%-12.2f|%-11s|%-11.2f|", houseDate, houseAddress, 
											fossil, electric, other, total, sqft, room);
		
		houseInfo.append(idHouse);
		houseInfo.append(String.format("%s\n", dash));
		houseInfo.append(tenantInfo);
		System.out.println(houseInfo);
		
	}
}
