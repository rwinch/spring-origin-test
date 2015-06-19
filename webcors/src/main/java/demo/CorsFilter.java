package demo;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsProcessor;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter extends OncePerRequestFilter {
    private CorsProcessor processor;
    private CorsConfigurationSource source;

    public CorsFilter(CorsProcessor processor, CorsConfigurationSource source) {
        super();
        this.processor = processor;
        this.source = source;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (CorsUtils.isCorsRequest(request)) {
            CorsConfiguration configuration = source
                    .getCorsConfiguration(request);
            processor.processRequest(configuration, request, response);
            if (CorsUtils.isPreFlightRequest(request)) {
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

}
