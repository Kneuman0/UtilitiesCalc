package utilitiesCalculator;

public class Tenant {
	private String name;
	private boolean active;
	
	public Tenant(String name, boolean active){
		this.active = active;
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
	 * @return the sqftOfRoom
	 */
	public double getSqftOfRoom() {
		return sqftOfRoom;
	}

	/**
	 * @param sqftOfRoom the sqftOfRoom to set
	 */
	public void setSqftOfRoom(double sqftOfRoom) {
		this.sqftOfRoom = sqftOfRoom;
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

	/**
	 * @return the isLandlord
	 */
	public boolean isLandlord() {
		return isLandlord;
	}

	/**
	 * @param isLandlord the isLandlord to set
	 */
	public void setLandlord(boolean isLandlord) {
		this.isLandlord = isLandlord;
	}

	double sqftOfRoom;
	boolean isFullTime;
	boolean isLandlord;
	
	public Tenant(String name, double sqftOfRoom){
		this.name = name;
		this.sqftOfRoom = sqftOfRoom;
	}
}