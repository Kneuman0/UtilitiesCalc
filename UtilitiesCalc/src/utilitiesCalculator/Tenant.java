package utilitiesCalculator;

public class Tenant {
	private String name;
	private boolean active;
	public String tenantType;
	double fte;
	private int tenant_ID;
	
	public Tenant(String name, boolean active, String tenantType){
		this.name = name;
		this.active = active;
		this.tenantType = tenantType;
		
	}
	
	public Tenant(String name, boolean active, String tenantType, double fte){
		this.name = name;
		this.active = active;
		this.tenantType = tenantType;
		this.fte = fte;
	}
	
	public Tenant(String name, boolean active, String tenantType, double fte, int tenant_ID){
		this.name = name;
		this.active = active;
		this.tenantType = tenantType;
		this.fte = fte;
		this.tenant_ID = tenant_ID;
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
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
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
	
	
}