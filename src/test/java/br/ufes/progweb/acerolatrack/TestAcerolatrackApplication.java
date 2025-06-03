package br.ufes.progweb.acerolatrack;

import org.springframework.boot.SpringApplication;

public class TestAcerolatrackApplication {

	public static void main(String[] args) {
		SpringApplication.from(AcerolatrackApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
