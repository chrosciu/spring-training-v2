package pl.wojtyna.trainings.spring.crowdsorcery.borrower.jpa;

import org.mapstruct.Mapper;
import pl.wojtyna.trainings.spring.crowdsorcery.borrower.Borrower;

@Mapper(componentModel = "spring")
public interface BorrowerEntityMapper {
    Borrower fromEntity(BorrowerEntity borrowerEntity);
    BorrowerEntity toEntity(Borrower borrower);
}
