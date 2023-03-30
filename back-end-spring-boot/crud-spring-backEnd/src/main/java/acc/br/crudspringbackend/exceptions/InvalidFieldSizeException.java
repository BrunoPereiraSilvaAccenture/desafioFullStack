package acc.br.crudspringbackend.exceptions;

public class InvalidFieldSizeException extends Exception {
	public InvalidFieldSizeException(String field,int size,String value){
	    super(String.format("%s com tamanho %d inválido, quantidade esperada era de : %s",field,size,value));
	  }
}