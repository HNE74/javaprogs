package de.hne;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories("de.hne.repo")
public class TestserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestserviceApplication.class, args);
	}

}
