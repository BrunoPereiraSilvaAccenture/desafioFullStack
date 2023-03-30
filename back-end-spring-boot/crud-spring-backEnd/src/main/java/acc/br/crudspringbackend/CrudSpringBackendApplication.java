package acc.br.crudspringbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableFeignClients
public class CrudSpringBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudSpringBackendApplication.class, args);
	}

}
