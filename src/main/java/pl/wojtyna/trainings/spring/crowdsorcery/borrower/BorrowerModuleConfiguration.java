package pl.wojtyna.trainings.spring.crowdsorcery.borrower;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class BorrowerModuleConfiguration {

    @Bean
    public BorrowerRepository borrowerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        return new JdbcBorrowerRepository(jdbcTemplate);
    }

    @Bean
    public BorrowerService borrowerService(BorrowerRepository repository) {
        return new BorrowerService(repository);
    }
}
