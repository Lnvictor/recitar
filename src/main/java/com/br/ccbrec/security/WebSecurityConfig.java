package com.br.ccbrec.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        (requests) -> {
                            requests.requestMatchers(HttpMethod.GET, "/web/ccbrec").hasAnyAuthority("ROLE_ADMIN", "ROLE_READER");

                            // Strict Admin actions
                            requests.requestMatchers(HttpMethod.POST, "/web/ccbrec/addNewCount").hasAuthority("ROLE_ADMIN");
                            requests.requestMatchers(HttpMethod.GET, "/web/ccbrec/removeCount").hasAuthority("ROLE_ADMIN");

                            requests.anyRequest().authenticated();
                        }
                )
                .formLogin((form) -> {
                            form.loginPage("/login");
                            form.permitAll();
                        }
                )
                .logout((logout) -> {
                    logout.permitAll();
                });

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
