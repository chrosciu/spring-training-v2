package pl.wojtyna.trainings.spring;

import java.util.Optional;

public interface CliMapper {
    Optional<RegisterInvestor> map(String[] args);
}
