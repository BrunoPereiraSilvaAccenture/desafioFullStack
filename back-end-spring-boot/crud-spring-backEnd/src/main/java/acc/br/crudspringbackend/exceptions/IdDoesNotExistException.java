package acc.br.crudspringbackend.exceptions;

public class IdDoesNotExistException extends Exception {
	public IdDoesNotExistException(long id, String typeTable){
	    super(String.format("%s com id %d não existe",typeTable,id));
	  }
}
