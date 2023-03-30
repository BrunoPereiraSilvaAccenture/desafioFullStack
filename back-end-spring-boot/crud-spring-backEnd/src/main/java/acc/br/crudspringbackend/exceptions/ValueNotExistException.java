package acc.br.crudspringbackend.exceptions;

public class ValueNotExistException extends Exception{
	public ValueNotExistException(String field,String value){
	    super(String.format("O %s : %s não encontrado",field,value));
	  }
}
