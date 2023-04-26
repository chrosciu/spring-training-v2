package pl.wojtyna.trainings.spring.crowdsorcery.borrower.jpa;

import org.springframework.transaction.annotation.Transactional;
import pl.wojtyna.trainings.spring.crowdsorcery.borrower.Borrower;
import pl.wojtyna.trainings.spring.crowdsorcery.borrower.BorrowerRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaBorrowerRepository implements BorrowerRepository {

    private final EntityManager entityManager;

    public JpaBorrowerRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Borrower borrower) {
        var borrowerEntity = new BorrowerEntity();
        borrowerEntity.setId(borrower.id());
        borrowerEntity.setName(borrower.name());
        entityManager.merge(borrowerEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Borrower> findAll() {
        return entityManager.createQuery("SELECT borrower FROM BorrowerEntity borrower", BorrowerEntity.class)
                            .getResultStream()
                            .map(borrowerEntity -> new Borrower(
                                borrowerEntity.getId(), borrowerEntity.getName()))
                            .toList();
    }

    @Override
    public Optional<Borrower> findById(String id) {
        var entity = entityManager.find(BorrowerEntity.class, id);
        return Optional.ofNullable(entity).map(borrowerEntity -> new Borrower(
                borrowerEntity.getId(), borrowerEntity.getName()));
    }


}
