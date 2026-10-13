package org.multikoisas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Desativa o CSRF pois a API é stateless baseada em token
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Garante que o servidor não guarda sessões
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/error",
                                "/user",
                                "/user/**",
                                "/socialnetwork",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html").permitAll()
                        .requestMatchers(
                                HttpMethod.GET,
                                "/error",
                                "/user",
                                "/user/**",
                                "/ping",
                                "/ping/**",
                                "/socialnetwork",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html").permitAll()
                        .requestMatchers(
                                HttpMethod.POST,
                                "/error",
                                "/user",
                                "/user/**",
                                "/socialnetwork",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html").permitAll()
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/error",
                                "/user",
                                "/user/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html").permitAll() // Libera o GET para a vitrine pública
                        .requestMatchers("/api/auth/**").permitAll() // Libera a rota de login para a administradora
                        .anyRequest().authenticated() // Exige autenticação via JWT para qualquer outra ação (POST, PUT, DELETE)
                )
                .build();
    }
}