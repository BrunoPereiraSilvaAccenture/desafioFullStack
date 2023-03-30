package acc.br.crudspringbackend.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import acc.br.crudspringbackend.enums.TipoDocumentoEnum;
import acc.br.crudspringbackend.exceptions.ConfigureValueNotExistException;
import acc.br.crudspringbackend.exceptions.DataAlreadyExistsException;
import acc.br.crudspringbackend.exceptions.EmptyFieldException;
import acc.br.crudspringbackend.exceptions.EmptyTableException;
import acc.br.crudspringbackend.exceptions.FieldNotFillException;
import acc.br.crudspringbackend.exceptions.IdDoesNotExistException;
import acc.br.crudspringbackend.exceptions.InvalidFieldSizeException;
import acc.br.crudspringbackend.exceptions.IsDigitException;
import acc.br.crudspringbackend.exceptions.NoValuesNegativesException;
import acc.br.crudspringbackend.exceptions.SpecificCaseException;
import acc.br.crudspringbackend.exceptions.ValueNotExistException;
import acc.br.crudspringbackend.model.Empresa;
import acc.br.crudspringbackend.model.Endereco;
import acc.br.crudspringbackend.model.Fornecedor;
import acc.br.crudspringbackend.repository.EmpresaRepository;
import acc.br.crudspringbackend.repository.FornecedorRepository;

@Service
public class FornecedorService {
	@Autowired
	FornecedorRepository fornecedorRepository;

	@Autowired
	EmpresaRepository empresaRepository;

	@Autowired
	private CepService cepService;

	public ResponseEntity<List<Fornecedor>> getAllFornecedor() {
		try {
			List<Fornecedor> fornecedores = new ArrayList<Fornecedor>();
			fornecedorRepository.findAll().forEach(fornecedor -> fornecedores.add(fornecedor));
			if (fornecedores.isEmpty())
				throw new EmptyTableException("Fornecedor");

			return new ResponseEntity<>(fornecedores, HttpStatus.OK);
		} catch (EmptyTableException eTE) {
			return new ResponseEntity(eTE.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<Fornecedor> getFornecedorById(long id) {
		try {
			Optional<Fornecedor> fornecedor = fornecedorRepository.findById(id);
			if (fornecedor.isPresent())
				return new ResponseEntity<Fornecedor>(fornecedor.get(), HttpStatus.OK);
			else
				throw new IdDoesNotExistException(id, "Fornecedor");
		} catch (IdDoesNotExistException iDNEE) {
			return new ResponseEntity(iDNEE.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<String> deleteFornecedorPorEmpresaId(Long empresaId) throws IdDoesNotExistException {
		try {
			if (!fornecedorRepository.existsByEmpresaId(empresaId)) {
				throw new IdDoesNotExistException(empresaId, "Fornecedor na empresa");
			}

			Empresa empresa = empresaRepository.findById(empresaId).get();
			fornecedorRepository.deleteByEmpresaId(empresaId);
			return new ResponseEntity(
					String.format("Fornecedores da empresa %s foram excluidos", empresa.getNomeFantasia()),
					HttpStatus.NO_CONTENT);
		} catch (IdDoesNotExistException iDNEE) {
			return new ResponseEntity(iDNEE.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<String> deleteFornecedor(long id) {
		try {
			Optional<Fornecedor> fornecedor = fornecedorRepository.findById(id);
			if (fornecedor.isPresent()) {
				String nomeFornecedor = fornecedor.get().getNome();
				fornecedorRepository.delete(fornecedor.get());
				return new ResponseEntity(
						String.format("Fornecedor %s com id %d excluida com sucesso!", nomeFornecedor, id),
						HttpStatus.NO_CONTENT);
			} else
				throw new IdDoesNotExistException(id, "Fornecedor");
		} catch (IdDoesNotExistException idN) {
			return new ResponseEntity(idN.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<List<Fornecedor>> getFornecedorPorEmpresaId(Long empresaId) throws IdDoesNotExistException {
		try {
			if (!fornecedorRepository.existsByEmpresaId(empresaId)) {
				throw new IdDoesNotExistException(empresaId, "Fornecedor na empresa");
			}

			List<Fornecedor> fornecedores = fornecedorRepository.findByEmpresaId(empresaId);
			return new ResponseEntity<>(fornecedores, HttpStatus.OK);
		} catch (IdDoesNotExistException iDNEE) {
			return new ResponseEntity(iDNEE.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<Fornecedor> createFornecedorPorEmpresa(Long empresaId, Fornecedor fornecedor)
			throws IdDoesNotExistException, ParseException {

		try {
			if (!empresaRepository.existsById(empresaId)) {
				throw new IdDoesNotExistException(empresaId, "Fornecedor");
			}
						
			System.out.println(fornecedor);
			
			checkDocuments(fornecedor);

			if (fornecedor.getTipoDocumento().toUpperCase().equals(TipoDocumentoEnum.CNPJ.getValor().toUpperCase()))
				checkCnpj(fornecedor, empresaId);
			else if (fornecedor.getTipoDocumento().toUpperCase().equals(TipoDocumentoEnum.CPF.getValor().toUpperCase()))
				checkCpf(fornecedor, empresaId);

			ResponseEntity<Endereco> endereco = cepService.consumirApi(fornecedor.getCep());

			if (endereco == null)
				throw new ValueNotExistException("CEP", fornecedor.getCep());

			checkDados(fornecedor, endereco.getBody());

			fornecedor.setEstado(endereco.getBody().getUf());
			fornecedor.setEndereco(String.format("End: %s , Bairro: %s, Cidade: %s", endereco.getBody().getLogradouro(),
					endereco.getBody().getBairro(), endereco.getBody().getCidade()));

			fornecedor.setEmpresa(empresaRepository.findById(empresaId).get());

			fornecedorRepository.save(fornecedor);
			return new ResponseEntity<>(fornecedor, HttpStatus.CREATED);
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
		} catch (SpecificCaseException sCE) {
			return new ResponseEntity(sCE.getMessage(), HttpStatus.NOT_FOUND);
		} catch (ConfigureValueNotExistException cVNEE) {
			return new ResponseEntity(cVNEE.getMessage(), HttpStatus.NOT_FOUND);
		} catch (FieldNotFillException fNFE) {
			return new ResponseEntity(fNFE.getMessage(), HttpStatus.NOT_FOUND);
		}

	}

	public ResponseEntity<Fornecedor> updateFornecedor(long id, Fornecedor newFornecedor) throws ParseException {
		try {
			Optional<Fornecedor> oldFornecedor = fornecedorRepository.findById(id);
			if (oldFornecedor.isPresent()) {

				checkDocuments(newFornecedor);

				Fornecedor fornecedor = oldFornecedor.get();
				
				if (newFornecedor.getTipoDocumento().toUpperCase()
						.equals(TipoDocumentoEnum.CNPJ.getValor().toUpperCase()))
					checkCnpj(newFornecedor, fornecedor.getEmpresa().getId());
				else if (newFornecedor.getTipoDocumento().toUpperCase()
						.equals(TipoDocumentoEnum.CPF.getValor().toUpperCase()))
					checkCpf(newFornecedor, fornecedor.getEmpresa().getId());

				
				ResponseEntity<Endereco> endereco = cepService.consumirApi(newFornecedor.getCep());

				if (endereco == null)
					throw new ValueNotExistException("CEP", newFornecedor.getCep());

				checkDados(newFornecedor, endereco.getBody());

				fornecedor.setEstado(endereco.getBody().getUf());
				fornecedor.setEndereco(
						String.format("End: %s , Bairro: %s, Cidade: %s", endereco.getBody().getLogradouro(),
								endereco.getBody().getBairro(), endereco.getBody().getCidade()));

				fornecedor.setCep(newFornecedor.getCep());
				fornecedor.setCnpjCpf(newFornecedor.getCnpjCpf());
				fornecedor.setDataNascimento(newFornecedor.getDataNascimento());
				fornecedor.setEmail(newFornecedor.getEmail());
				fornecedor.setNome(newFornecedor.getNome());
				fornecedor.setRg(newFornecedor.getRg());
				fornecedor.setTipoDocumento(newFornecedor.getTipoDocumento());

				fornecedor.setEmpresa(empresaRepository.findById(fornecedor.getEmpresa().getId()).get());

				fornecedorRepository.save(fornecedor);
				return new ResponseEntity<Fornecedor>(fornecedor, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
			} else
				throw new IdDoesNotExistException(id, "Fornecedor");
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
		} catch (SpecificCaseException sCE) {
			return new ResponseEntity(sCE.getMessage(), HttpStatus.NOT_FOUND);
		} catch (ConfigureValueNotExistException cVNEE) {
			return new ResponseEntity(cVNEE.getMessage(), HttpStatus.NOT_FOUND);
		} catch (FieldNotFillException fNFE) {
			return new ResponseEntity(fNFE.getMessage(), HttpStatus.NOT_FOUND);
		}

	}

	public void checkDocuments(Fornecedor fornecedor)
			throws EmptyFieldException, IsDigitException, DataAlreadyExistsException, InvalidFieldSizeException,
			NoValuesNegativesException, ConfigureValueNotExistException {
		if (fornecedor.getTipoDocumento().isEmpty())
			throw new EmptyFieldException("Tipo Documento");

		if (fornecedor.getTipoDocumento().length() != 1)
			throw new InvalidFieldSizeException("Tipo Documento", fornecedor.getTipoDocumento().length(), "1");

		boolean tipoDocumentoExist = false;
		String tiposDocumentosExiste = "";
		for (TipoDocumentoEnum tipoDocumento : TipoDocumentoEnum.values()) {
			if (tipoDocumento.getValor().toUpperCase().equals(fornecedor.getTipoDocumento().toUpperCase())) {
				tipoDocumentoExist = true;
				break;
			}
			tiposDocumentosExiste += "|" + tipoDocumento.getValor().toUpperCase() + "("
					+ tipoDocumento.name().toUpperCase() + ")|";
		}
		if (!tipoDocumentoExist)
			throw new ConfigureValueNotExistException("Tipo Documento", tiposDocumentosExiste);

	}

	public void checkCnpj(Fornecedor fornecedor, Long idEmpresa)
			throws EmptyFieldException, IsDigitException, DataAlreadyExistsException, InvalidFieldSizeException,
			NoValuesNegativesException, ConfigureValueNotExistException, FieldNotFillException {

		
		if (fornecedor.getCnpjCpf().isEmpty())
			throw new EmptyFieldException("CNPJ/CPF");

		if (fornecedor.getCnpjCpf().length() != 14)
			throw new InvalidFieldSizeException("CNPJ/CPF", fornecedor.getCnpjCpf().length(), "14");

		if (!fornecedor.getCnpjCpf().matches("[0-9]+"))
			throw new IsDigitException("CNPJ/CPF");

		if (fornecedorRepository.existsByCnpjCpf(fornecedor.getCnpjCpf())
				&& fornecedorRepository.existsByEmpresaId(idEmpresa))
			throw new DataAlreadyExistsException("CNPJ/CPF por Empresa", fornecedor.getCnpjCpf());

		if (!fornecedor.getRg().isEmpty())
			throw new FieldNotFillException("Rg");

		if (fornecedor.getDataNascimento() != null)
			throw new FieldNotFillException("Data Nascimento");
		
	}

	public void checkCpf(Fornecedor fornecedor, Long idEmpresa) throws EmptyFieldException, IsDigitException,
			DataAlreadyExistsException, InvalidFieldSizeException, NoValuesNegativesException,
			ConfigureValueNotExistException, FieldNotFillException, SpecificCaseException, ParseException {

		if (fornecedor.getCnpjCpf().isEmpty())
			throw new EmptyFieldException("CNPJ/CPF");

		if (fornecedor.getCnpjCpf().length() != 11)
			throw new InvalidFieldSizeException("CNPJ/CPF", fornecedor.getCnpjCpf().length(), "11");

		if (!fornecedor.getCnpjCpf().matches("[0-9]+"))
			throw new IsDigitException("CNPJ/CPF");

		if (fornecedorRepository.existsByCnpjCpf(fornecedor.getCnpjCpf())
		 && fornecedorRepository.existsByEmpresaId(idEmpresa)
		)
			throw new DataAlreadyExistsException("CNPJ/CPF por Empresa", fornecedor.getCnpjCpf());

		if (fornecedor.getRg().isEmpty())
			throw new EmptyFieldException("Rg");

		if (fornecedor.getRg().length() != 8)
			throw new InvalidFieldSizeException("Rg", fornecedor.getRg().length(), "8");

		if (!fornecedor.getRg().matches("[0-9]+"))
			throw new IsDigitException("Rg");

		if (fornecedorRepository.existsByRg(fornecedor.getRg())
				&& fornecedorRepository.existsByCnpjCpf(fornecedor.getCnpjCpf())
				&& fornecedorRepository.existsByEmpresaId(idEmpresa))
			throw new DataAlreadyExistsException("Rg por Cpf de empresa", fornecedor.getRg());

		if (fornecedor.getDataNascimento() == null)
			throw new EmptyFieldException("Data Nascimento");

	}

	public void checkDados(Fornecedor fornecedor, Endereco endereco)
			throws EmptyFieldException, IsDigitException, DataAlreadyExistsException, InvalidFieldSizeException,
			NoValuesNegativesException, ConfigureValueNotExistException, FieldNotFillException, SpecificCaseException {

		if (fornecedor.getEmail().isEmpty())
			throw new EmptyFieldException("Email");

		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);

		Matcher matcher = pattern.matcher(fornecedor.getEmail());
		if (!matcher.matches())
			throw new SpecificCaseException(
					String.format("Email %s invalido: formato valido exemplo@com", fornecedor.getEmail()));

		if (fornecedor.getNome().isEmpty())
			throw new EmptyFieldException("Nome");

		if (fornecedor.getCep().isEmpty())
			throw new EmptyFieldException("CEP");

		if (!fornecedor.getCep().matches("[0-9]+"))
			throw new IsDigitException("CEP");

		if (fornecedor.getCep().length() != 8)
			throw new InvalidFieldSizeException("CEP", fornecedor.getCep().length(), "8");

		if (endereco.getUf().length() != 2)
			throw new InvalidFieldSizeException("Estado", endereco.getUf().length(), "2");

		if (fornecedor.getDataNascimento() != null) {
			int age = Period.between(
					LocalDateTime.ofInstant(fornecedor.getDataNascimento().toInstant(), ZoneOffset.UTC).toLocalDate(),
					LocalDate.now()).getYears();

			if (endereco.getUf().toUpperCase().equals("PR") && fornecedor.getTipoDocumento().toUpperCase()
					.equals(TipoDocumentoEnum.CPF.getValor().toUpperCase()) && age < 18)
				throw new SpecificCaseException(String.format(
						"Fornecedor do Paraná com Cpf %s é menor de idade possui %d anos", fornecedor.getCnpjCpf(),age));
		}
	}

}
