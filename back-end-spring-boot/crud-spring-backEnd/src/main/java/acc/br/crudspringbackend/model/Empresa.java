package acc.br.crudspringbackend.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "empresa")
public class Empresa implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idEmpresa")
	private Long id;

	@Column(name = "cnpj", length = 14, nullable = false)
	private String cnpj;

	@Column(name = "nomefantasia", nullable = false)
	private String nomeFantasia;

	@Column(name = "cep", length = 8, nullable = false)
	private String cep;
	
	@Column(name = "estado", length = 2, nullable = false)
	private String estado;
	
	@Column(name = "endereco", nullable = false)
	private String endereco;

}