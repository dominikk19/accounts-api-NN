package pl.dkiszka.accountsapinn.adapters.rest.api.nbp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * @author Dominik Kiszka {dominikk19}
 * @project accounts-api-nn
 * @date 19.06.2021
 */
@Configuration
class NbpApiConfiguration {

    @Value("${restTemplate.configuration.connect-timeout}")
    private long connectTimeout;

    @Value("${restTemplate.configuration.read-timeout}")
    private long readTimeout;

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofMillis(connectTimeout))
                .setReadTimeout(Duration.ofMillis(readTimeout))
                .build();
    }
}
