package pl.wojtyna.trainings.spring;

public class CliAdapter {

    private final CliCommandsMapper cliCommandsMapper;
    private final InvestorService investorService;

    public CliAdapter(CliCommandsMapper cliCommandsMapper, InvestorService investorService) {
        this.cliCommandsMapper = cliCommandsMapper;
        this.investorService = investorService;
    }

    void run(String[] args) {
        var registerInvestorCommand = cliCommandsMapper.map(args).orElseThrow();
        System.out.println("Registering new investor");
        investorService.register(registerInvestorCommand);
        System.out.println("New investor registered");
        System.out.println("All available investors");
        investorService.findAll().forEach(System.out::println);
    }

}