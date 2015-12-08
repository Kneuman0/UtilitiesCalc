package utilitiesCalculator;

public class ReceiptTenantInfo {
	
	String name;
	String date;
	String tenantType;
	double fte;
	double amountOwed;
	int house_ID;
	
	public ReceiptTenantInfo(String name, String date, 
			String tenantType, double fte, double amountOwed, int house_ID){
		this.name = name;
		this.date = date;
		this.tenantType = tenantType;
		this.fte = fte;
		this.amountOwed = amountOwed;
		this.house_ID = house_ID;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the tenantType
	 */
	public String getTenantType() {
		return tenantType;
	}

	/**
	 * @param tenantType the tenantType to set
	 */
	public void setTenantType(String tenantType) {
		this.tenantType = tenantType;
	}

	/**
	 * @return the fte
	 */
	public double getFte() {
		return fte;
	}

	/**
	 * @param fte the fte to set
	 */
	public void setFte(double fte) {
		this.fte = fte;
	}

	/**
	 * @return the amountOwed
	 */
	public double getAmountOwed() {
		return amountOwed;
	}

	/**
	 * @param amountOwed the amountOwed to set
	 */
	public void setAmountOwed(double amountOwed) {
		this.amountOwed = amountOwed;
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
	
	

}
