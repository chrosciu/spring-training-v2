package pl.wojtyna.trainings.spring.crowdsorcery.borrower;

import java.util.List;
import java.util.Optional;

public class BorrowerService {

    private final BorrowerRepository borrowerRepository;

    public BorrowerService(BorrowerRepository borrowerRepository) {
        this.borrowerRepository = borrowerRepository;
    }

    public void register(Borrower borrower) {
        borrowerRepository.save(borrower);
    }

    public List<Borrower> findAll() {
        return borrowerRepository.findAll();
    }

    public Optional<Borrower> findById(String id) {
        return borrowerRepository.findById(id);
    }

    public List<Borrower> findByName(String name) {
        return borrowerRepository.findByName(name);
    }
}
