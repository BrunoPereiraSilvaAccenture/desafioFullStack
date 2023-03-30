package acc.br.crudspringbackend.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name="fornecedor")
public class Fornecedor implements Serializable
{
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "tipodocumento", length=1, nullable = false)
    private String tipoDocumento; 

    @Column(name = "cnpjcpf", length = 14, nullable = false)
    private String cnpjCpf; 

    @Column(name = "rg", length=7)
    private String rg;
    
    @Column(name = "datanascimento")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "GMT-3")
	@Temporal(TemporalType.TIMESTAMP)
    private Date dataNascimento;
    
    @Column(name = "nome", nullable = false)
    private String nome;
    
    @Column(name="email", nullable = true)
    private String email;
    
    @Column(name = "cep",length=8, nullable = false)
    private String cep;
    
    @Column(name = "estado", length = 2, nullable = false)
	private String estado;
	
	@Column(name = "endereco", nullable = false)
	private String endereco;
    
    @ManyToOne
    @JoinColumn(name = "empresaIdEmpresa",referencedColumnName = "idEmpresa",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Empresa empresa;

}