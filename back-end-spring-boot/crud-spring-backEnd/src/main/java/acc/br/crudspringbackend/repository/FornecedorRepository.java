package acc.br.crudspringbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import acc.br.crudspringbackend.model.Fornecedor;

import javax.transaction.Transactional;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long>
{
	List<Fornecedor> findByEmpresaId(Long idEmpresa);
	
	@Transactional
	void deleteByEmpresaId(Long idEmpresa);
	
	boolean existsByTipoDocumento(String tipoDocumento);
	boolean existsByCnpjCpf(String cnpjCpf);
	boolean existsByRg(String rg);
	boolean existsByNome(String nome);
	boolean existsByCep(String Cep);
	boolean existsByEmpresaId(Long idEmpresa);
	
}
