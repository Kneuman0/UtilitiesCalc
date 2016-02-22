package databaseModelClasses;

public class House {
	private String address;
	private int sqFt;
	private int numRooms;
	private int house_ID;
	
	public House(String address, int numRooms, int sqFt){
		this.address = address;
		this.sqFt = sqFt;
		this.numRooms = numRooms;
		}
	
	public House(String address, int numRooms, int sqFt, int house_ID){
		this.address = address;
		this.sqFt = sqFt;
		this.numRooms = numRooms;
		this.house_ID = house_ID;
	}

	
	/**
	 * @return the sqFt
	 */
	public int getSqFt() {
		return sqFt;
	}

	/**
	 * @param sqFt the sqFt to set
	 */
	public void setSqFt(int sqFt) {
		this.sqFt = sqFt;
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

}
