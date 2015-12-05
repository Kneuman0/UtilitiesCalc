package utilitiesCalculator;

public class Sublet extends Tenant {
	
	double fte;
	
	
	public Sublet(String name, boolean active) {
		super(name, active, "Sublet");
		this.fte = 1;
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

}
