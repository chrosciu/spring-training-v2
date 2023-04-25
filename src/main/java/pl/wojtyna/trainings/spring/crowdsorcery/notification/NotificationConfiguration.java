package pl.wojtyna.trainings.spring.crowdsorcery.notification;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "crowdsorcery.notification")
public record NotificationConfiguration(String email) {
}
