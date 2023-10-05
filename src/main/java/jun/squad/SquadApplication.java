package jun.squad;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.TimeZone;

@Configuration
@EnableJpaAuditing
@SpringBootApplication
@RequiredArgsConstructor
public class SquadApplication {

	private final EntityManager entityManager;

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
		SpringApplication.run(SquadApplication.class, args);
	}


	@Bean
	public JPAQueryFactory jpaQueryFactory () {
		return new JPAQueryFactory(entityManager);
	}
}
