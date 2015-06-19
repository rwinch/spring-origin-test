package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsProcessor;
import org.springframework.web.cors.DefaultCorsProcessor;

@SpringBootApplication
public class WebcorsApplication {

    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter(corsProcessor(), corsConfigurationSource());
    }

    @Bean
    public CorsProcessor corsProcessor() {
        return new DefaultCorsProcessor();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedMethod(HttpMethod.GET.name());
        corsConfiguration.addAllowedMethod(HttpMethod.HEAD.name());
        corsConfiguration.addAllowedMethod(HttpMethod.POST.name());
        corsConfiguration.addAllowedMethod(HttpMethod.PUT.name());
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setMaxAge(1800L);

        return new GlobalCorsConfigurationSource(corsConfiguration);
    }

    public static void main(String[] args) {
        SpringApplication.run(WebcorsApplication.class, args);
    }
}
