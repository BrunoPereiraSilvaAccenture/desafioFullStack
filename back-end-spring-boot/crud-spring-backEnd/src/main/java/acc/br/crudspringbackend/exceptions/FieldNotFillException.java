package acc.br.crudspringbackend.exceptions;

public class FieldNotFillException extends Exception {
	public FieldNotFillException(String field){
	    super(String.format("Campo %s não deve conter valor",field));
	  }
}
