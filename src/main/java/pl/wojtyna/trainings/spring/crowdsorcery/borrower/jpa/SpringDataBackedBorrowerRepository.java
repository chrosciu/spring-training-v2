package pl.wojtyna.trainings.spring.crowdsorcery.borrower.jpa;

import pl.wojtyna.trainings.spring.crowdsorcery.borrower.Borrower;
import pl.wojtyna.trainings.spring.crowdsorcery.borrower.BorrowerRepository;

import java.util.List;
import java.util.Optional;

public class SpringDataBackedBorrowerRepository implements BorrowerRepository {

    private final SpringBorrowerEntityRepository springRepo;

    public SpringDataBackedBorrowerRepository(SpringBorrowerEntityRepository springRepo) {
        this.springRepo = springRepo;
    }

    @Override
    public void save(Borrower borrower) {
        var borrowerEntity = new BorrowerEntity();
        borrowerEntity.setId(borrower.id());
        borrowerEntity.setName(borrower.name());
        springRepo.save(borrowerEntity);
    }

    @Override
    public List<Borrower> findAll() {
        return springRepo.findAll().stream().map(borrowerEntity -> new Borrower(
            borrowerEntity.getId(), borrowerEntity.getName())).toList();
    }

    @Override
    public Optional<Borrower> findById(String id) {
        return springRepo.findById(id).map(borrowerEntity -> new Borrower(
                borrowerEntity.getId(), borrowerEntity.getName()));
    }

    @Override
    public List<Borrower> findByName(String name) {
        return springRepo.findByName(name).stream().map(borrowerEntity -> new Borrower(
                borrowerEntity.getId(), borrowerEntity.getName())).toList();
    }


}
