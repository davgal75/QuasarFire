package com.proyecto.quasaroperation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Clase que inicia el proyecto
@SpringBootApplication(scanBasePackages = {"com.proyecto"})
public class QuasarOperationApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuasarOperationApplication.class, args);
	}

}
