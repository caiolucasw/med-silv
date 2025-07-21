package com.medsilveira.api.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.medsilveira.api.filters.AutorizacaoFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  @Autowired
  private AutorizacaoFilter autorizacaoFilter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    // Aqui você pode configurar as regras de segurança, como autenticação e
    // autorização
    // Retorne a configuração do filtro de segurança
    return http
        .csrf(t -> t.disable()) // Desabilita a proteção CSRF (Cross-Site Request Forgery)
        .sessionManagement(t -> t.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(t -> {
          t.requestMatchers("/auth/login").permitAll();
          t.requestMatchers("/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**").permitAll();
          t.anyRequest().authenticated();
        })
        .addFilterBefore(autorizacaoFilter, UsernamePasswordAuthenticationFilter.class)
        .build(); // Define a política de sessão como stateless
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    // Retorna o AuthenticationManager configurado
    return configuration.getAuthenticationManager();

  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    // Retorna um PasswordEncoder para codificar senhas
    return new BCryptPasswordEncoder();
  }
}
