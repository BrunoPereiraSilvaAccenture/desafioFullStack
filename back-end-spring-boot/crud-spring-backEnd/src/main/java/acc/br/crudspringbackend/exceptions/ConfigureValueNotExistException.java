package acc.br.crudspringbackend.exceptions;

public class ConfigureValueNotExistException extends Exception {
	public ConfigureValueNotExistException(String field,String value){
	    super(String.format("Valor do campo %s deve ser: %s",field,value));
	  }
}