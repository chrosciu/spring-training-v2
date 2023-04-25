package pl.wojtyna.trainings.spring.crowdsorcery.investor.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wojtyna.trainings.spring.crowdsorcery.investor.service.InvestorService;
import pl.wojtyna.trainings.spring.crowdsorcery.investor.service.RegisterInvestor;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/investorModule/api/v0/investors")
@Slf4j
public class InvestorRestApi {

    private final InvestorService investorService;

    public InvestorRestApi(InvestorService investorService) {
        this.investorService = investorService;
    }

    @PostMapping
    public ResponseEntity<Void> register(@RequestBody RegisterInvestorRestDto registerInvestorRestDto) {
        investorService.register(new RegisterInvestor(registerInvestorRestDto.id(),
                registerInvestorRestDto.name()));
        return ResponseEntity.created(URI.create("/investorModule/api/v0/investors/%s".formatted(
                        registerInvestorRestDto.id())))
                .build();
    }

    @GetMapping("/{id}")
    public Optional<InvestorFetchResultRestDto> fetch(@PathVariable("id") String id) {
        log.info("Get investor");
        return investorService.findAll()
                              .stream()
                              .filter(investor -> Objects.equals(investor.id(), id))
                              .map(investor -> new InvestorFetchResultRestDto(investor.id(), investor.name()))
                              .findAny();
    }
}
