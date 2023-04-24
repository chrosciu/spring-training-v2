package pl.wojtyna.trainings.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringTrainingV2Application {

    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext("pl.wojtyna")) {
            var cliAdapter = context.getBean(CliAdapter.class);
            cliAdapter.run(args);
        }
    }

}
