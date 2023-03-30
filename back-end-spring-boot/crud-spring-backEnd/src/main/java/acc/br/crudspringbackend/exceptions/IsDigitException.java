package acc.br.crudspringbackend.exceptions;

public class IsDigitException extends Exception {
	public IsDigitException(String field){
	    super(String.format("%s só aceita números",field));
	  }
}

