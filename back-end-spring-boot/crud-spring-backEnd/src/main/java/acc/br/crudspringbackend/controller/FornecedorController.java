package acc.br.crudspringbackend.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import acc.br.crudspringbackend.exceptions.IdDoesNotExistException;
import acc.br.crudspringbackend.model.Empresa;
import acc.br.crudspringbackend.model.Fornecedor;
import acc.br.crudspringbackend.service.EmpresaService;
import acc.br.crudspringbackend.service.FornecedorService;

@RestController
public class FornecedorController {
	@Autowired
	FornecedorService fornecedorService;
	
	@RequestMapping(value = "/api/fornecedor", method = RequestMethod.GET)
	public ResponseEntity<List<Fornecedor>> getAllFornecedor() {
		return fornecedorService.getAllFornecedor();
	}

	@RequestMapping(value = "/api/fornecedor/{id}", method = RequestMethod.GET)
	public ResponseEntity<Fornecedor> getFornecedorById(@PathVariable(value = "id") long id) {
		return fornecedorService.getFornecedorById(id);
	}
	
	@RequestMapping(value = "/api/fornecedor/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Fornecedor> updateFornecedor(@PathVariable(value = "id") long id,
			@Validated @RequestBody Fornecedor newFornecedor) throws ParseException {
		return fornecedorService.updateFornecedor(id, newFornecedor);
	}

	@RequestMapping(value = "/api/fornecedor/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteFornecedor(@PathVariable(value = "id") long id) {
		return fornecedorService.deleteFornecedor(id);
	}

	@RequestMapping(value = "/api/empresa/{empresaId}/fornecedor", method = RequestMethod.POST)
	public ResponseEntity<Fornecedor> createFornecedor(@PathVariable(value = "empresaId") Long empresaId,
			@Validated @RequestBody Fornecedor fornecedor) throws IdDoesNotExistException, ParseException{
		return fornecedorService.createFornecedorPorEmpresa(empresaId, fornecedor);
	}
	
	@RequestMapping(value = "/api/empresa/{empresaId}/fornecedor", method = RequestMethod.GET)
	public ResponseEntity<List<Fornecedor>> getFornecedorPorEmpresaId(
			@PathVariable(value = "empresaId") Long empresaId) throws IdDoesNotExistException {
		
	    return fornecedorService.getFornecedorPorEmpresaId(empresaId);
	}
	
	@RequestMapping(value = "/api/empresa/{empresaId}/fornecedor", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteFornecedorPorEmpresaId(@PathVariable(value = "empresaId") 
	Long empresaId) throws IdDoesNotExistException {	    
	    return fornecedorService.deleteFornecedorPorEmpresaId(empresaId);
	}
}
