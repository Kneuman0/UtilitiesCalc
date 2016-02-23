package biz.personalAcademics.databaseModelClasses;

public interface TenantInterface {

	//REQ#3
	
	/**
	 * returns the name of the tenant
	 * @return
	 */
	public String getName();
	
	/**
	 * Returns an int containing tenant's ID from UtilitiesCalc d
	 * @return
	 */
	public int getTenant_ID();
}
