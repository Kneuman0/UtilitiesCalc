package utilitiesCalculator;

public class BillMonth {
	private String date;
	private double fossilFuelBill;
	private double electricityBill;
	private double otherBill;
	private int billMonth_ID;
	
	public BillMonth(String date, double fossilFuelBill, double electricityBill, double otherBill){
		this.date = date;
		this.fossilFuelBill = fossilFuelBill;
		this.electricityBill = electricityBill;
		this.otherBill = otherBill;
	}
	
	public BillMonth(String date, double fossilFuelBill, double electricityBill, double otherBill, int billMonth_ID){
		this.date = date;
		this.fossilFuelBill = fossilFuelBill;
		this.electricityBill = electricityBill;
		this.otherBill = otherBill;
		this.billMonth_ID = billMonth_ID;
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
