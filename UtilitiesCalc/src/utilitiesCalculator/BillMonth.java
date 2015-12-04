package utilitiesCalculator;

public class BillMonth {
	private double fossilFuelBill;
	private double electricityBill;
	private double otherBill;
	
	public BillMonth(double fossilFuelBill, double electricityBill, double otherBill){
		this.fossilFuelBill = fossilFuelBill;
		this.electricityBill = electricityBill;
		this.otherBill = otherBill;
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
