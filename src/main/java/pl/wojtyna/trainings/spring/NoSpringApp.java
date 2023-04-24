package pl.wojtyna.trainings.spring;

public class NoSpringApp {

    public static void main(String[] args) {
        var cliMapper = (CliMapper) null;
        var cliAdapter = new CliAdapter(cliMapper);
        cliAdapter.run(args);
    }


}
