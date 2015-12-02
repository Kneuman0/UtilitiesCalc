package utilitiesCalculator;

public class House {
//	String address, String building, double areaOfBedroom,
//	int numRooms, int numOfTenants, 
	
	String address;
	String building;
	int numRooms;
	int numOfTenants;
	boolean isFullTime;

	public House(String address, String building, int numRooms, int numOfTenants, boolean isFullTime){
		this.address = address;
		this.building = building;
		this.numRooms = numRooms;
		this.numOfTenants = numOfTenants;
		this.isFullTime = isFullTime;
		
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
	 * @return the building
	 */
	public String getBuilding() {
		return building;
	}

	/**
	 * @param building the building to set
	 */
	public void setBuilding(String building) {
		this.building = building;
	}

	/**
	 * @return the numRooms
	 */
	public int getNumRooms() {
		return numRooms;
	}

	/**
	 * @param numRooms the numRooms to set
	 */
	public void setNumRooms(int numRooms) {
		this.numRooms = numRooms;
	}

	/**
	 * @return the numOfTenants
	 */
	public int getNumOfTenants() {
		return numOfTenants;
	}

	/**
	 * @param numOfTenants the numOfTenants to set
	 */
	public void setNumOfTenants(int numOfTenants) {
		this.numOfTenants = numOfTenants;
	}

	/**
	 * @return the isFullTime
	 */
	public boolean isFullTime() {
		return isFullTime;
	}

	/**
	 * @param isFullTime the isFullTime to set
	 */
	public void setFullTime(boolean isFullTime) {
		this.isFullTime = isFullTime;
	}
}
