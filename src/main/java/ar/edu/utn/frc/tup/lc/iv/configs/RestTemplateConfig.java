package ar.edu.utn.frc.tup.lc.iv.configs;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfig {

    /**
     * Creates and configures a RestTemplate bean.
     *
     * @param restTemplateBuilder A builder provided by Spring Boot to create and configure RestTemplate instances.
     * @return A fully configured RestTemplate instance.
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder){
        return  restTemplateBuilder
                // Sets the connection timeout to 1000 milliseconds (1 second)
                .setConnectTimeout(Duration.ofMillis(1000))
                // Sets the read timeout to 1000 milliseconds (1 second)
                .setReadTimeout(Duration.ofMillis(1000))
                .build();
    }

}

