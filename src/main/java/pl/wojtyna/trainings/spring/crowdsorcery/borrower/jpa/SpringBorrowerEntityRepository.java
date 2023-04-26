package pl.wojtyna.trainings.spring.crowdsorcery.borrower.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringBorrowerEntityRepository extends JpaRepository<BorrowerEntity, String> {
    List<BorrowerEntity> findByName(String name);
}
