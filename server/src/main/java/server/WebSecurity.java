package server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.DefaultCorsProcessor;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    @Autowired
    CorsConfigurationSource corsConfigurationSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .addFilterBefore(new CorsFilter(new DefaultCorsProcessor(), corsConfigurationSource), FilterSecurityInterceptor.class)
            .authorizeRequests()
                .anyRequest().authenticated()
                .and()
            .formLogin().and()
            .httpBasic();
    }
}
