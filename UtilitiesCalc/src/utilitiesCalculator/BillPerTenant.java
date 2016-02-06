package utilitiesCalculator;

public class BillPerTenant {
	private int billMonth_ID;
	private int house_ID;
	private int tenant_ID;
	private double fte;
	private String tenantName;
	private double bill;
	private String tenantType;
	
	/**
	 * Need to make utility method that calculates tenants individual bill from the bill portion bill, and
	 * fte. I presume this object will be stored in an arraylist which will be passed to the database
	 * utility method to storage
	 * @param tenantType
	 * @param bill
	 * @param tenantName
	 */
	public BillPerTenant(int billMonth_ID, int house_ID, double fte, double bill, int tenant_ID){
		this.billMonth_ID = billMonth_ID;
		this.house_ID = house_ID;
		this.bill = bill;
		this.fte = fte;
		this.tenant_ID = tenant_ID;
	}
	public BillPerTenant(int billMonth_ID, int house_ID, int tenant_ID, double fte, 
			String tenantName, double bill, String tenantType){
		this.billMonth_ID = billMonth_ID;
		this.house_ID = house_ID;
		this.tenant_ID = tenant_ID;
		this.fte = fte;
		this.bill = bill;
		this.tenantName = tenantName;
		this.tenantType = tenantType;
		
		
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
	 * @return the tenant_ID
	 */
	public int getTenant_ID() {
		return tenant_ID;
	}

	/**
	 * @param tenant_ID the tenant_ID to set
	 */
	public void setTenant_ID(int tenant_ID) {
		this.tenant_ID = tenant_ID;
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
