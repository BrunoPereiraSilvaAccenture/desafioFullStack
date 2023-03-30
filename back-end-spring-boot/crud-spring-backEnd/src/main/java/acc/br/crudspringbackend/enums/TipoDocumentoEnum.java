package acc.br.crudspringbackend.enums;

public enum TipoDocumentoEnum {
	CPF("F"),CNPJ("J");
	
	private String valor;
	
	TipoDocumentoEnum(String valor){
		this.valor = valor;
	}
	
	public String getValor() {
		return this.valor;
	}
	
}
