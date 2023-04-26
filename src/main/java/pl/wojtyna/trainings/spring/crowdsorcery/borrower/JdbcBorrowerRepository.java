package pl.wojtyna.trainings.spring.crowdsorcery.borrower;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcBorrowerRepository implements BorrowerRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcBorrowerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void save(Borrower borrower) {
        var paramMap = new HashMap<String, Object>();
        paramMap.put("id", borrower.id());
        paramMap.put("name", borrower.name());
        paramMap.put("is_default", false);
        jdbcTemplate.update("INSERT INTO borrowers VALUES(:id, :name, :is_default)", paramMap);
    }

    @Override
    public List<Borrower> findAll() {
        return jdbcTemplate.query("""
                                  SELECT id, name FROM borrowers
                                  """,
                                  (rs, rowNum) -> {
                                      var id = rs.getString("id");
                                      var name = rs.getString("name");
                                      return new Borrower(id, name);
                                  });
    }

    @Override
    public Optional<Borrower> findById(String id) {
        var borrower = jdbcTemplate.queryForObject("SELECT id, name FROM borrowers WHERE id = :id",
                Map.of("id", id),
                (rs, rowNum) -> new Borrower(rs.getString("id"), rs.getString("name")));
        return Optional.ofNullable(borrower);
    }
}
