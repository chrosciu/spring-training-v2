package pl.wojtyna.trainings.spring.crowdsorcery.investor.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import pl.wojtyna.trainings.spring.crowdsorcery.investor.service.InvestorService;

import javax.annotation.PostConstruct;

@RestController
@Slf4j
public class InvestorRestApi {
    private final InvestorService investorService;

    public InvestorRestApi(InvestorService investorService) {
        this.investorService = investorService;
    }

    @PostConstruct
    void init() {
        log.info("init");
    }
}
