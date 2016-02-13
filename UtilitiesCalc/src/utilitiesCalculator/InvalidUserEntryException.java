package utilitiesCalculator;

@SuppressWarnings("serial")
public class InvalidUserEntryException extends Exception{
	
	public InvalidUserEntryException(String error){
		super(error);
	}

}
