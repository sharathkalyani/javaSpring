package com.example.wastepickup.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomSuccessHandler customSuccessHandler;
    
    // ✅ Bean for password encryption
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ✅ Bean for authentication manager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // ✅ Define role-based access and security rules
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Public routes
                .requestMatchers("/","/register", "/login", "/css/**", "/js/**").permitAll()

                // Temporary access to pickup creation for testing
                .requestMatchers("/wastepickup/create").permitAll()

                // Role-based access
                .requestMatchers("/collector/**").hasRole("COLLECTOR")
                .requestMatchers("/recycling/**").hasRole("RECYCLING_STAFF")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/dashboard").hasRole("CITIZEN") // Assuming you have a citizen role

                // Profile page for any authenticated user
                .requestMatchers("/profile").authenticated()

                // All other routes require authentication
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler(customSuccessHandler) // Redirect to home after login
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }
}
