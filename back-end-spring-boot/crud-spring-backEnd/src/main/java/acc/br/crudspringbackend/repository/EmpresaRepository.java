package acc.br.crudspringbackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import acc.br.crudspringbackend.model.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa,Long> {
	boolean existsByNomeFantasia(String nomeFantasia);
	boolean existsByCnpj(String cnpj);
	boolean existsByCep(String cep);
}
