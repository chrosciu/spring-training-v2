package pl.wojtyna.trainings.spring.crowdsorcery.borrower.jpa;

import pl.wojtyna.trainings.spring.crowdsorcery.borrower.Borrower;
import pl.wojtyna.trainings.spring.crowdsorcery.borrower.BorrowerRepository;

import java.util.List;
import java.util.Optional;

public class SpringDataBackedBorrowerRepository implements BorrowerRepository {

    private final SpringBorrowerEntityRepository springRepo;
    private final BorrowerEntityMapper borrowerEntityMapper;

    public SpringDataBackedBorrowerRepository(SpringBorrowerEntityRepository springRepo,
                                              BorrowerEntityMapper borrowerEntityMapper) {
        this.springRepo = springRepo;
        this.borrowerEntityMapper = borrowerEntityMapper;
    }

    @Override
    public void save(Borrower borrower) {
        springRepo.save(borrowerEntityMapper.toEntity(borrower));
    }

    @Override
    public List<Borrower> findAll() {
        return springRepo.findAll().stream().map(borrowerEntityMapper::fromEntity).toList();
    }

    @Override
    public Optional<Borrower> findById(String id) {
        return springRepo.findById(id).map(borrowerEntityMapper::fromEntity);
    }

    @Override
    public List<Borrower> findByName(String name) {
        return springRepo.findByName(name).stream().map(borrowerEntityMapper::fromEntity).toList();
    }


}
