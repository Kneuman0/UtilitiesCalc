package utilitiesCalculator;

public class BillPerTenant {
	private String tenantType;
	private double fte;
	private String tenantName;
	private double bill;
	
	/**
	 * Need to make utility method that calculates tenants individual bill from the bill portion bill, and
	 * fte. I presume this object will be stored in an arraylist which will be passed to the database
	 * utility method to storage
	 * @param tenantType
	 * @param fte
	 * @param tenantName
	 */
	public BillPerTenant(String tenantType, double fte, String tenantName){
		this.tenantType = tenantType;
		this.tenantName = tenantName;
		this.fte = fte;
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
	 * @return the tenantName
	 */
	public String getTenantName() {
		return tenantName;
	}

	/**
	 * @param tenantName the tenantName to set
	 */
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	/**
	 * @return the bill
	 */
	public double getBill() {
		return bill;
	}

	/**
	 * @param bill the bill to set
	 */
	public void setBill(double bill) {
		this.bill = bill;
	}
	
	
	
}
