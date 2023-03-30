package acc.br.crudspringbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import acc.br.crudspringbackend.model.Empresa;
import acc.br.crudspringbackend.model.Endereco;
import acc.br.crudspringbackend.service.CepService;
import acc.br.crudspringbackend.service.EmpresaService;

@RestController
public class EmpresaController {
	@Autowired
	EmpresaService empresaService;

	@RequestMapping(value = "/api/empresa", method = RequestMethod.GET)
	public ResponseEntity<List<Empresa>> getAllEmpresas() {
		return empresaService.getAllEmpresas();
	}

	@RequestMapping(value = "/api/empresa/{id}", method = RequestMethod.GET)
	public ResponseEntity<Empresa> getEmpresaById(@PathVariable(value = "id") long id) {
		return empresaService.getEmpresaById(id);
	}

	@RequestMapping(value = "/api/empresa/", method = RequestMethod.POST)
	public ResponseEntity<Empresa> createEmpresa(@Validated @RequestBody Empresa empresa) {
		return empresaService.createEmpresa(empresa);
	}

	@RequestMapping(value = "/api/empresa/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Empresa> updateEmpresa(@PathVariable(value = "id") long id,
			@Validated @RequestBody Empresa newEmpresa) {
		return empresaService.updateEmpresa(id, newEmpresa);
	}

	@RequestMapping(value = "/api/empresa/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteEmpresa(@PathVariable(value = "id") long id) {
		return empresaService.deleteEmpresa(id);
	}

}
