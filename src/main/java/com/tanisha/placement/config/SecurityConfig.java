package com.tanisha.placement.config;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // Public
                        .requestMatchers("/login.html").permitAll()

                        .requestMatchers("/api/student/resume/**").permitAll()

                        // Role-Based Access
                        .requestMatchers("/admin/*.html").hasRole("ADMIN")
                        .requestMatchers("/student/*.html").hasRole("STUDENT")

                        .requestMatchers("/admin/api/**").hasRole("ADMIN")
                        .requestMatchers("/student/api/**").hasRole("STUDENT")

                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login.html")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/redirect", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login.html")
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}