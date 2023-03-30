package acc.br.crudspringbackend.exceptions;

public class EmptyFieldException extends Exception {
	public EmptyFieldException(String field){
	    super(String.format("Valor do campo %s não pode ser vazio",field));
	  }
}