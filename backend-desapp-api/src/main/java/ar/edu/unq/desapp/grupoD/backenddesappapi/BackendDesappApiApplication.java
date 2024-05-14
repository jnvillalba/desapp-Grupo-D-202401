package ar.edu.unq.desapp.grupoD.backenddesappapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "ar.edu.unq.desapp.grupoD.backenddesappapi.configuration")
public class BackendDesappApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendDesappApiApplication.class, args);
	}

}
