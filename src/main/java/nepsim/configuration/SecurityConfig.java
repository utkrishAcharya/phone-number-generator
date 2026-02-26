package nepsim.configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

     // disable CSRF (otherwise POST requests like login/signup will be blocked) 
            .csrf(csrf -> csrf.disable())
            
                // permit all requests (so nothing requires authentication yet) 
            .authorizeHttpRequests(auth -> auth
            .anyRequest().permitAll();                        
            );
        return http.build();
    } }
