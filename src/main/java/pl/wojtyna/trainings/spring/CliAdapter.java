package pl.wojtyna.trainings.spring;

import java.util.stream.Stream;

public class CliAdapter {
    private final CliMapper cliMapper;

    public CliAdapter(CliMapper cliMapper) {
        this.cliMapper = cliMapper;
    }

    public void run(String[] args) {
        var investorRepository = new InMemoryInvestorRepository();
        var investorService = new InvestorService(investorRepository);
        var registerInvestorCommand = cliMapper.map(args).orElseThrow();
        System.out.println("Registering new investor");
        investorService.register(registerInvestorCommand);
        System.out.println("New investor registered");
        System.out.println("All available investors");
        investorService.findAll().forEach(System.out::println);
    }

    private static String extractNameOrFail(String[] args) {
        return extractArgOrFail("name", args);
    }

    private static String extractIdOrFail(String[] args) {
        return extractArgOrFail("id", args);
    }

    private static String extractArgOrFail(String argName, String[] args) {
        return Stream.of(args)
                .filter(arg -> arg.startsWith("-%s=".formatted(argName)))
                .findAny()
                .map(arg -> arg.split("=")[1])
                .orElseThrow();
    }
}
