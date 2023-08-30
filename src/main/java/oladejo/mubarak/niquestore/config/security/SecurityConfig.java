package oladejo.mubarak.niquestore.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

@Service
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final   AuthenticationProvider authenticationProvider;

    private final String[] vendorUrls = {"/api/v1/product/add", "/api/v1/products/view"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/vendor/**").hasAuthority("VENDOR")
                .requestMatchers("/api/v1/product/**").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/order/**").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/payment/**").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/auth/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
