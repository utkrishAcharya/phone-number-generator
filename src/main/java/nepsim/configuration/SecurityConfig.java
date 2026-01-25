package nepsim.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // disable CSRF because this is a stateless REST API
                .csrf(csrf -> csrf.disable())

                // configure what is permitted vs what needs authentication
                .authorizeHttpRequests(auth -> auth
                        // allow these endpoints without auth
                        .requestMatchers(
                                "/auth/signup",
                                "/auth/login",
                                "/error"        // allow error internal mapping
                        ).permitAll()

                        // allow swagger/open api (optional)
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // any other request must be authenticated
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
