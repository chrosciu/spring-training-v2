package pl.wojtyna.trainings.spring.crowdsorcery.notification;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.MailSender;
import pl.wojtyna.trainings.spring.crowdsorcery.eventpublisher.SubscriberRegistry;

@Configuration
@EnableConfigurationProperties(NotificationConfiguration.class)
@PropertySource("classpath:notification.properties")
public class NotificationModuleConfiguration {

    @Bean
    public EventSubscriberInitializer eventSubscriberInitializer(SubscriberRegistry subscriberRegistry,
                                                                 NotificationService notificationService) {
        return new EventSubscriberInitializer(subscriberRegistry, notificationService);
    }

    @Bean
    public EmailAddressResolver stubbedEmailAddressResolver(
            NotificationConfiguration notificationConfiguration) {
        return investor -> notificationConfiguration.email();
    }

    @Bean
    public NotificationService notificationService(MailSender mailSender, EmailAddressResolver emailAddressResolver) {
        return new SimpleMailNotificationService(mailSender, emailAddressResolver);
    }
}
