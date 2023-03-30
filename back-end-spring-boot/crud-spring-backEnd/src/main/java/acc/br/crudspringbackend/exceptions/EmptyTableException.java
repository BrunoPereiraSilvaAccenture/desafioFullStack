package acc.br.crudspringbackend.exceptions;

public class EmptyTableException extends Exception {
	public EmptyTableException(String table){
	    super(String.format("Em %s não existe dados cadastrados",table));
	  }
}
