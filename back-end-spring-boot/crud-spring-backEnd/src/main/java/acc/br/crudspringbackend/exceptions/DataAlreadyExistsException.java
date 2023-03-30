package acc.br.crudspringbackend.exceptions;

public class DataAlreadyExistsException extends Exception {
	public DataAlreadyExistsException(String field,String value){
	    super(String.format("Valor %s do campo %s já inserido",field,value));
	  }
}