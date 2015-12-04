package utilitiesCalculator;

public class BillPerTenant {
	private String tenantType;
	private double fte;
	private String tenantName;
	private double bill;
	
	public BillPerTenant(String tenantType, double fte, String tenantName, double bill){
		this.tenantType = tenantType;
		this.tenantName = tenantName;
		this.fte = fte;
		this.bill = bill;
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
