package acc.br.crudspringbackend.exceptions;

public class NoValuesNegativesException extends Exception {
	public NoValuesNegativesException(String field,String value){
	    super(String.format("Valor %s do campo %s não pode ser negativo",field,value));
	  }
}
