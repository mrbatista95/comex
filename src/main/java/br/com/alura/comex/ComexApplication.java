package br.com.alura.comex;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport
@EnableCaching
@OpenAPIDefinition(info = @Info(title = "Comex API", version = "2.0", description = "Comex Information"))
public class ComexApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComexApplication.class, args);
	}

}
