package pl.wojtyna.trainings.spring.crowdsorcery.borrower;

import java.util.List;
import java.util.Optional;

public interface BorrowerRepository {

    void save(Borrower borrower);

    List<Borrower> findAll();

    Optional<Borrower> findById(String id);

    List<Borrower> findByName(String id);
}
