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
                            requests.requestMatchers(HttpMethod.GET, "/web/ccbrec")
                                    .hasAnyAuthority("ROLE_ADMIN", "ROLE_AUXILIAR","ROLE_READER");

                            // Strict Admin actions
                            requests.requestMatchers(HttpMethod.POST, "/web/ccbrec/add")
                                    .hasAnyAuthority("ROLE_ADMIN");
                            requests.requestMatchers(HttpMethod.GET, "/web/ccbrec/delete").
                                    hasAnyAuthority("ROLE_ADMIN");

                            // Strict Admin and Auxiliares actions
                            requests.requestMatchers(HttpMethod.GET,"/web/meetings/**")
                                            .hasAnyAuthority("ROLE_ADMIN", "ROLE_AUXILIAR");

                            requests.requestMatchers(HttpMethod.POST,"/web/meetings/**")
                                    .hasAnyAuthority("ROLE_ADMIN");

                            requests.requestMatchers(HttpMethod.GET, "/web/meetings/add")
                                            .hasAnyAuthority("ROLE_ADMIN");
                            requests.requestMatchers(HttpMethod.GET, "/web/settings")
                                    .hasAnyAuthority("ROLE_ADMIN", "ROLE_AUXILIAR");
                            requests.requestMatchers(HttpMethod.POST, "/web/settings/addUser")
                                    .hasAnyAuthority("ROLE_ADMIN");
                            requests.requestMatchers(HttpMethod.POST, "/web/settings/change")
                                    .hasAnyAuthority("ROLE_ADMIN");
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
