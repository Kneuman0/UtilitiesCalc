package utilitiesCalculator;

@SuppressWarnings("serial")
public class InvalidUserEntryException extends RuntimeException{
	
	public InvalidUserEntryException(String error){
		super(error);
	}

}
