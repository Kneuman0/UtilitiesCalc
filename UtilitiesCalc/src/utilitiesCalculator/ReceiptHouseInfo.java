package utilitiesCalculator;

public class ReceiptHouseInfo {
	private String address;
	private double costPerRoom;
	private double costPerSqFt;
	private double totalBill;
	private double fossilFuel;
	private double electric;
	private double otherBills;
	private int house_ID;
	
	public ReceiptHouseInfo(String address, int numRooms, int sqft,
			double totalBill, double fossilFuel, double electric, double otherBills, int house_ID){
		this.address = address;
		this.costPerRoom = totalBill / (double)numRooms;
		this.costPerSqFt = totalBill / (double)sqft;
		this.totalBill = totalBill;
		this.fossilFuel = fossilFuel;
		this.electric = electric;
		this.otherBills = otherBills;
		this.house_ID = house_ID;
	}
	
	public ReceiptHouseInfo(String address, double totalBill, double fossilFuel, 
			double electric, double otherBills, double costPerSqFt, double costPerRoom){
		this.address = address;
		this.costPerRoom = costPerRoom;
		this.costPerSqFt = costPerSqFt;
		this.totalBill = totalBill;
		this.fossilFuel = fossilFuel;
		this.electric = electric;
		this.otherBills = otherBills;
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
	 * @return the costPerRoom
	 */
	public double getCostPerRoom() {
		return costPerRoom;
	}

	/**
	 * @param costPerRoom the costPerRoom to set
	 */
	public void setCostPerRoom(double costPerRoom) {
		this.costPerRoom = costPerRoom;
	}

	/**
	 * @return the costPerSqFt
	 */
	public double getCostPerSqFt() {
		return costPerSqFt;
	}

	/**
	 * @param costPerSqFt the costPerSqFt to set
	 */
	public void setCostPerSqFt(double costPerSqFt) {
		this.costPerSqFt = costPerSqFt;
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
	 * @return the fossilFuel
	 */
	public double getFossilFuel() {
		return fossilFuel;
	}

	/**
	 * @param fossilFuel the fossilFuel to set
	 */
	public void setFossilFuel(double fossilFuel) {
		this.fossilFuel = fossilFuel;
	}

	/**
	 * @return the electric
	 */
	public double getElectric() {
		return electric;
	}

	/**
	 * @param electric the electric to set
	 */
	public void setElectric(double electric) {
		this.electric = electric;
	}

	/**
	 * @return the otherBills
	 */
	public double getOtherBills() {
		return otherBills;
	}

	/**
	 * @param otherBills the otherBills to set
	 */
	public void setOtherBills(double otherBills) {
		this.otherBills = otherBills;
	}
	
	
	

}
