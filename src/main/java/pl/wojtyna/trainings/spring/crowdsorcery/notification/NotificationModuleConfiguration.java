package pl.wojtyna.trainings.spring.crowdsorcery.notification;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationModuleConfiguration {

    @Bean
    public EmailAddressResolver stubbedEmailAddressResolver() {
        return investor -> "dev@null.com";
    }
}
