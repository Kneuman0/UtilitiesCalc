package biz.personalAcademics.databaseModelClasses;

public class BillMonth {
	private String date;
	private double fossilFuelBill;
	private double electricityBill;
	private double otherBill;
	private double totalBill;
	private int billMonth_ID;
	private int house_ID;
	private String address;
	
	public BillMonth(String date, double fossilFuelBill, 
			double electricityBill, double otherBill, double totalBill, int house_ID){
		this.date = date;
		this.fossilFuelBill = fossilFuelBill;
		this.electricityBill = electricityBill;
		this.otherBill = otherBill;
		this.totalBill = totalBill;
		this.house_ID = house_ID;
	}
	
	public BillMonth(String date, double fossilFuelBill, double electricityBill, 
			double otherBill, double totalBill, int house_ID, int billMonth_ID){
		this.date = date;
		this.fossilFuelBill = fossilFuelBill;
		this.electricityBill = electricityBill;
		this.otherBill = otherBill;
		this.billMonth_ID = billMonth_ID;
		this.totalBill = totalBill;
		this.house_ID = house_ID;
	}
	
	public BillMonth(double fossilFuelBill, double electricityBill, 
			double otherBill, double totalBill, String address){
		this.fossilFuelBill = fossilFuelBill;
		this.electricityBill = electricityBill;
		this.otherBill = otherBill;
		this.totalBill = totalBill;
		this.address = address;
	}
	
	
	
	
	/**
	 * @return the house_ID
	 */
	public int getHouse_ID() {
		return house_ID;
	}

	/**
	 * @param house_ID the house_ID to set
	 */
	public void setHouse_ID(int house_ID) {
		this.house_ID = house_ID;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the totalBill
	 */
	public double getTotalBill() {
		return totalBill;
	}

	/**
	 * @param totalBill the totalBill to set
	 */
	public void setTotalBill(double totalBill) {
		this.totalBill = totalBill;
	}

	/**
	 * @return the billMonth_ID
	 */
	public int getBillMonth_ID() {
		return billMonth_ID;
	}

	/**
	 * @param billMonth_ID the billMonth_ID to set
	 */
	public void setBillMonth_ID(int billMonth_ID) {
		this.billMonth_ID = billMonth_ID;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}



	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}



	/**
	 * @return the fossilFuelBill
	 */
	public double getFossilFuelBill() {
		return fossilFuelBill;
	}

	/**
	 * @param fossilFuelBill the fossilFuelBill to set
	 */
	public void setFossilFuelBill(double fossilFuelBill) {
		this.fossilFuelBill = fossilFuelBill;
	}

	/**
	 * @return the electricityBill
	 */
	public double getElectricityBill() {
		return electricityBill;
	}

	/**
	 * @param electricityBill the electricityBill to set
	 */
	public void setElectricityBill(double electricityBill) {
		this.electricityBill = electricityBill;
	}

	/**
	 * @return the otherBill
	 */
	public double getOtherBill() {
		return otherBill;
	}

	/**
	 * @param otherBill the otherBill to set
	 */
	public void setOtherBill(double otherBill) {
		this.otherBill = otherBill;
	}

	
}
