package br.app.code.smart.Endereco;

import br.app.code.smart.Endereco.model.Cliente;
import br.app.code.smart.Endereco.service.impl.ClienteServiceImpl;
import jakarta.validation.constraints.Null;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EnderecoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnderecoApplication.class, args);
	}
}

