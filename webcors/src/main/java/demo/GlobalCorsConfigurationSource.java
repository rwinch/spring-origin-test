package demo;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

public class GlobalCorsConfigurationSource implements CorsConfigurationSource {
    private CorsConfiguration corsConfiguration;

    public GlobalCorsConfigurationSource(CorsConfiguration corsConfiguration) {
        super();
        this.corsConfiguration = corsConfiguration;
    }

    @Override
    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
        return corsConfiguration;
    }

}
