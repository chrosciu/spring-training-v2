package pl.wojtyna.trainings.spring.crowdsorcery.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestModuleConfiguration {

    @Bean
    public RestResources restResources() {
        return new HardcodedRestResources();
    }

    @Bean
    public RestClient restClient(RestResources restResources) {
        return new UnirestClient(restResources);
    }
}
