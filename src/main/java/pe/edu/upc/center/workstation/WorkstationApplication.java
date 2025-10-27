package pe.edu.upc.center.workstation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WorkstationApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkstationApplication.class, args);
	}

}
