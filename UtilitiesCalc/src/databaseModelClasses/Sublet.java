package databaseModelClasses;


public class Sublet extends Tenant {
	
	public Sublet(String name, boolean active) {
		super(name, active, "Sublet");
		super.setFte(1);
	}
	
	public Sublet(String name, boolean active, int tenant_ID) {
		super(name, active, "Sublet", tenant_ID);
		super.setFte(1);
	}
}
