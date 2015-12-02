package utilitiesCalculator;

public class Tenant {
	String name;
	double sqftOfRoom;
	boolean isFullTime;
	boolean isLandlord;
	
	public Tenant(String name, double sqftOfRoom){
		this.name = name;
		this.sqftOfRoom = sqftOfRoom;
	}
}