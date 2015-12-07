package utilitiesCalculator;

// REQ 5
public class Landlord extends Tenant {

	
	public Landlord(String name, boolean active) {
		super(name, active, "Landlord");
		super.setFte(0);
	}
	
	public Landlord(String name, boolean active, int tenant_ID) {
		super(name, active, "Landlord", tenant_ID);
		super.setFte(0);
	}

}
