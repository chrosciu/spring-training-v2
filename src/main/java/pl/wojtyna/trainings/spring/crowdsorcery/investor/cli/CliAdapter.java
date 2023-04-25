package pl.wojtyna.trainings.spring.crowdsorcery.investor.cli;

import lombok.extern.slf4j.Slf4j;
import pl.wojtyna.trainings.spring.crowdsorcery.investor.service.InvestorService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
public class CliAdapter {

    private final CliCommandsMapper cliCommandsMapper;
    private final InvestorService investorService;

    public CliAdapter(CliCommandsMapper cliCommandsMapper, InvestorService investorService) {
        this.cliCommandsMapper = cliCommandsMapper;
        this.investorService = investorService;
    }

    @PostConstruct
    private void init() {
        log.info("CliAdapter is ready");
    }

    @PreDestroy
    private void destroy() {
        log.info("CliAdapter is going down");
    }

    public void run(String[] args) {
        var registerInvestorCommand = cliCommandsMapper.map(args).orElseThrow();
        System.out.println("Registering new investor");
        investorService.register(registerInvestorCommand);
        System.out.println("New investor registered");
        System.out.println("All available investors");
        investorService.findAll().forEach(System.out::println);
    }

}
