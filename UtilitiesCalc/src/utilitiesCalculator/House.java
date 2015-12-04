package utilitiesCalculator;

public class House {
	private String address;
	private int sqFt;
	private int numRooms;
	
	public House(String address, int numRooms, int sqFt){
		this.address = address;
		this.sqFt = sqFt;
		this.numRooms = numRooms;
				
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
