package nepsim.configuration;

import nepsim.security.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.POST;

@Configuration
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // Disable CSRF so POST, PUT, DELETE do not require a CSRF token
                .csrf(csrf -> csrf.disable())

                //  Stateless — we use JWT instead of session
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                //  Public vs Protected routes
                .authorizeHttpRequests(auth -> auth

                        // Allow public access to signup and login POSTs
                        .requestMatchers(POST, "/auth/signup").permitAll()
                        .requestMatchers(POST, "/auth/login").permitAll()
                        .requestMatchers(POST, "/api/simusers/signup").permitAll()
                        .requestMatchers(POST, "/api/simusers/login").permitAll()

                        // Allow error path so Spring doesn’t block error responses
                        .requestMatchers("/error").permitAll()

                        // Everything else requires a valid JWT
                        .anyRequest().authenticated()
                )

                // Add JWT filter before the UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
