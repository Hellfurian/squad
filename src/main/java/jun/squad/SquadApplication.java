package jun.squad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SquadApplication {

	public static void main(String[] args) {
		SpringApplication.run(SquadApplication.class, args);
	}

}
