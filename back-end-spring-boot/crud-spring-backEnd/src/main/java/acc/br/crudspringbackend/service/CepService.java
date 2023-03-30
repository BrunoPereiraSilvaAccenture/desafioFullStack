package acc.br.crudspringbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import acc.br.crudspringbackend.model.Endereco;

@Service
public class CepService {

	String url = "http://cep.la/";

	public ResponseEntity<Endereco> consumirApi(String cep) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Accept", "application/json");
			HttpEntity<String> entity = new HttpEntity<>("body", headers);

			ResponseEntity<Endereco> response = restTemplate.exchange(url + cep, HttpMethod.GET, entity,
					Endereco.class);

			return response;
		} catch (Exception e) {
			return null;
		}

	}
}
