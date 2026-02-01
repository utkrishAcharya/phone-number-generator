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
                // disable CSRF (otherwise POST requests like login/signup will be blocked) :contentReference[oaicite:3]{index=3}
                .csrf(csrf -> csrf.disable())
            

                // permit all requests (so nothing requires authentication yet) :contentReference[oaicite:4]{index=4}
                .authorizeHttpRequests(auth -> auth
                                       
                     .anyRequest().permitAll()
                );

        return http.build();
    }
}
