package pl.wojtyna.trainings.spring.crowdsorcery;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CliSpringAdapterRunner implements ApplicationRunner, ApplicationContextAware, BeanNameAware {

    private final CliAdapter cliAdapter;
    private ApplicationContext applicationContext;

    public CliSpringAdapterRunner(CliAdapter cliAdapter) {
        this.cliAdapter = cliAdapter;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        cliAdapter.run(args.getSourceArgs());
        if (applicationContext instanceof AnnotationConfigApplicationContext ac) {
            //ac.refresh();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBeanName(String name) {
        log.info("Set bean name to : " + name);
    }
}
