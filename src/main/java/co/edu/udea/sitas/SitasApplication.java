package co.edu.udea.sitas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@SpringBootApplication()
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL_FORMS)
public class SitasApplication {

	public static void main(String[] args) {
		SpringApplication.run(SitasApplication.class, args);
	}

}
