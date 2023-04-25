package pl.wojtyna.trainings.spring.crowdsorcery.notification;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import pl.wojtyna.trainings.spring.crowdsorcery.CrowdSorceryRootContextConfiguration;
import pl.wojtyna.trainings.spring.crowdsorcery.investor.profile.InvestorProfile;
import pl.wojtyna.trainings.spring.crowdsorcery.investor.service.InvestorProfileService;
import pl.wojtyna.trainings.spring.crowdsorcery.investor.service.InvestorService;
import pl.wojtyna.trainings.spring.crowdsorcery.investor.service.RegisterInvestor;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
        var context = new SpringApplicationBuilder().parent(CrowdSorceryRootContextConfiguration.class)
                                                    .properties("spring.main.allow-bean-definition-overriding=true")
                                                    .initializers((ApplicationContextInitializer<GenericApplicationContext>) applicationContext -> {
                                                        applicationContext.registerBean(InvestorProfileService.class,
                                                                                        () -> (id) -> Optional.of(new InvestorProfile(
                                                                                            100,
                                                                                            true,
                                                                                            "http://localhost:8080?ref=123")));
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
        var argumentCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSenderMock).send(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).satisfies(message -> {
            assertThat(message.getTo()).hasSize(1);
            assertThat(message.getTo()[0]).isEqualTo("devdev@null.org");
        });
    }
}
