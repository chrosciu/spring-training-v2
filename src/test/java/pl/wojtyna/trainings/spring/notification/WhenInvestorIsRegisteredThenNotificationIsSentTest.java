package pl.wojtyna.trainings.spring.notification;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import pl.wojtyna.trainings.spring.crowdsorcery.CrowdSorceryRootContextConfiguration;
import pl.wojtyna.trainings.spring.crowdsorcery.investor.InvestorProfile;
import pl.wojtyna.trainings.spring.crowdsorcery.investor.InvestorProfileService;
import pl.wojtyna.trainings.spring.crowdsorcery.investor.InvestorService;
import pl.wojtyna.trainings.spring.crowdsorcery.investor.RegisterInvestor;
import pl.wojtyna.trainings.spring.crowdsorcery.notification.NotificationModuleConfiguration;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class WhenInvestorIsRegisteredThenNotificationIsSentTest {

    // @formatter:off
    @DisplayName(
        """
         when investor is registered then notification is sent
        """
    )
    // @formatter:on
    @Test
    void test() {
        // given
        var mailSenderMock = mock(MailSender.class);
        var context = new SpringApplicationBuilder()
                .properties("spring.main.allow-bean-definition-overriding=true")
                .parent(CrowdSorceryRootContextConfiguration.class)
                .initializers((ApplicationContextInitializer<GenericApplicationContext>) applicationContext -> {
                    applicationContext.registerBean(InvestorProfileService.class,
                            () -> id -> Optional.of(new InvestorProfile(100, true, "https://chrost.eu")));
                })
                .child(NotificationModuleConfiguration.class)
                .initializers((ApplicationContextInitializer<GenericApplicationContext>) applicationContext -> {
                    applicationContext.registerBean(MailSender.class, () -> mailSenderMock);
                })
                .run("-id=123", "-name=Henry");
        var investorService = context.getBean(InvestorService.class);

        // when
        investorService.register(new RegisterInvestor("456", "George"));

        // then
        verify(mailSenderMock).send(any(SimpleMailMessage.class));
    }
}
