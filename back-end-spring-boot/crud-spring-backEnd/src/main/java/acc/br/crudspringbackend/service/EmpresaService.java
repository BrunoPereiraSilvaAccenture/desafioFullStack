package acc.br.crudspringbackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

//import acc.br.crudspringbackend.controller.CepRestController;
import acc.br.crudspringbackend.exceptions.DataAlreadyExistsException;
import acc.br.crudspringbackend.exceptions.EmptyFieldException;
import acc.br.crudspringbackend.exceptions.EmptyTableException;
import acc.br.crudspringbackend.exceptions.IdDoesNotExistException;
import acc.br.crudspringbackend.exceptions.InvalidFieldSizeException;
import acc.br.crudspringbackend.exceptions.IsDigitException;
import acc.br.crudspringbackend.exceptions.NoValuesNegativesException;
import acc.br.crudspringbackend.exceptions.ValueNotExistException;
import acc.br.crudspringbackend.model.Empresa;
import acc.br.crudspringbackend.model.Endereco;
import acc.br.crudspringbackend.repository.EmpresaRepository;

@Service
public class EmpresaService {
	@Autowired
	EmpresaRepository empresaRepository;

	@Autowired
	private CepService cepService;

	public ResponseEntity<List<Empresa>> getAllEmpresas() {
		try {
			List<Empresa> empresas = new ArrayList<Empresa>();
			empresaRepository.findAll().forEach(empresa -> empresas.add(empresa));
			if (empresas.isEmpty())
				throw new EmptyTableException("Empresas");

			return new ResponseEntity<>(empresas, HttpStatus.OK);
		} catch (EmptyTableException eTE) {			
			return new ResponseEntity(eTE.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<Empresa> getEmpresaById(long id) {
		try {
			Optional<Empresa> empresa = empresaRepository.findById(id);
			if (empresa.isPresent())
				return new ResponseEntity<Empresa>(empresa.get(), HttpStatus.OK);
			else
				throw new IdDoesNotExistException(id, "Empresa");
		} catch (IdDoesNotExistException iDNE) {
			return new ResponseEntity(iDNE.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<Empresa> createEmpresa(Empresa empresa) {
		try {

			checkDataCnpj(empresa);
			checkDataNomeFantasia(empresa);

			ResponseEntity<Endereco> endereco = cepService.consumirApi(empresa.getCep());
			
			if (endereco == null)
				throw new ValueNotExistException("CEP", empresa.getCep());
			
			checkDataEndereco(empresa, endereco.getBody());

			empresa.setEstado(endereco.getBody().getUf());
			empresa.setEndereco(String.format("End: %s , Bairro: %s, Cidade: %s", endereco.getBody().getLogradouro(),
					endereco.getBody().getBairro(), endereco.getBody().getCidade()));

			empresaRepository.save(empresa);
			return new ResponseEntity<Empresa>(empresa, HttpStatus.CREATED);

		} catch (DataAlreadyExistsException dAEE) {
			return new ResponseEntity(dAEE.getMessage(), HttpStatus.NOT_FOUND);
		} catch (IsDigitException iDE) {
			return new ResponseEntity(iDE.getMessage(), HttpStatus.NOT_FOUND);
		} catch (InvalidFieldSizeException iFSE) {
			return new ResponseEntity(iFSE.getMessage(), HttpStatus.NOT_FOUND);
		} catch (EmptyFieldException eFE) {
			return new ResponseEntity(eFE.getMessage(), HttpStatus.NOT_FOUND);
		} catch (NoValuesNegativesException nVNE) {
			return new ResponseEntity(nVNE.getMessage(), HttpStatus.NOT_FOUND);
		} catch (ValueNotExistException vNEE) {
			return new ResponseEntity(vNEE.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<Empresa> updateEmpresa(long id, Empresa newEmpresa) {
		try {
			Optional<Empresa> oldEmpresa = empresaRepository.findById(id);
			if (oldEmpresa.isPresent()) {

				checkDataCnpj(newEmpresa);
				checkDataNomeFantasia(newEmpresa);

				ResponseEntity<Endereco> endereco = cepService.consumirApi(newEmpresa.getCep());
				Empresa empresa = oldEmpresa.get();
				
				if (endereco == null)
					throw new ValueNotExistException("CEP", newEmpresa.getCep());
				
				
				checkDataEndereco(newEmpresa, endereco.getBody());

				empresa.setEstado(endereco.getBody().getUf());
				empresa.setEndereco(String.format("End: %s , Bairro: %s, Cidade: %s", endereco.getBody().getLogradouro(),
						endereco.getBody().getBairro(), endereco.getBody().getCidade()));

				empresa.setCep(newEmpresa.getCep());
				empresa.setCnpj(newEmpresa.getCnpj());
				empresa.setNomeFantasia(newEmpresa.getNomeFantasia());

				empresaRepository.save(empresa);
				return new ResponseEntity<Empresa>(empresa, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
			} else
				throw new IdDoesNotExistException(id, "Empresa");
		} catch (IdDoesNotExistException idN) {
			return new ResponseEntity(idN.getMessage(), HttpStatus.NOT_FOUND);
		} catch (DataAlreadyExistsException dAEE) {
			return new ResponseEntity(dAEE.getMessage(), HttpStatus.NOT_FOUND);
		} catch (IsDigitException iDE) {
			return new ResponseEntity(iDE.getMessage(), HttpStatus.NOT_FOUND);
		} catch (InvalidFieldSizeException iFSE) {
			return new ResponseEntity(iFSE.getMessage(), HttpStatus.NOT_FOUND);
		} catch (EmptyFieldException eFE) {
			return new ResponseEntity(eFE.getMessage(), HttpStatus.NOT_FOUND);
		} catch (NoValuesNegativesException nVNE) {
			return new ResponseEntity(nVNE.getMessage(), HttpStatus.NOT_FOUND);
		} catch (ValueNotExistException vNEE) {
			return new ResponseEntity(vNEE.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<String> deleteEmpresa(long id) {
		try {
			Optional<Empresa> empresa = empresaRepository.findById(id);
			if (empresa.isPresent()) {
				String nomeEmpresa = empresa.get().getNomeFantasia();
				empresaRepository.delete(empresa.get());
			return new ResponseEntity(String.format("Empresa %s com id %d excluida com sucesso!", 
					nomeEmpresa,id),HttpStatus.NO_CONTENT);
			} else
				throw new IdDoesNotExistException(id, "Empresa");
		} catch (IdDoesNotExistException idN) {
			return new ResponseEntity(idN.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	

	public void checkDataCnpj(Empresa empresa) throws EmptyFieldException, IsDigitException, DataAlreadyExistsException,
			InvalidFieldSizeException, NoValuesNegativesException {
		if (empresa.getCnpj().isEmpty())
			throw new EmptyFieldException("CNPJ");

		if (!empresa.getCnpj().matches("[0-9]+"))
			throw new IsDigitException("CNPJ");

		if (empresa.getCnpj().length() != 14)
			throw new InvalidFieldSizeException("CNPJ", empresa.getCnpj().length(), "14");
			

		if (empresaRepository.existsByCnpj(empresa.getCnpj()))
			throw new DataAlreadyExistsException("CNPJ", empresa.getCnpj());

	}

	public void checkDataNomeFantasia(Empresa empresa) throws EmptyFieldException, DataAlreadyExistsException {
		if (empresa.getNomeFantasia().isEmpty())
			throw new EmptyFieldException("Nome Fantasia");

		if (empresaRepository.existsByNomeFantasia(empresa.getNomeFantasia()))
			throw new DataAlreadyExistsException("Nome Fantasia", empresa.getNomeFantasia());

	}

	public void checkDataEndereco(Empresa empresa, Endereco endereco) throws EmptyFieldException, IsDigitException,
			DataAlreadyExistsException, InvalidFieldSizeException, NoValuesNegativesException, ValueNotExistException {

		if (empresa.getCep().isEmpty())
			throw new EmptyFieldException("CEP");

		if (!empresa.getCep().matches("[0-9]+"))
			throw new IsDigitException("CEP");
		
		if (empresa.getCep().length() != 8)
			throw new InvalidFieldSizeException("CEP", empresa.getCep().length(), "8");
		
		if (endereco.getUf().length() != 2)
			throw new InvalidFieldSizeException("Estado", endereco.getUf().length(), "2");
	}

}
