package br.com.Tramas3030.breakpoint.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig {

  @Autowired
  private SecurityFilter securityFilter;

  private static final String[] SWAGGER_LIST = {
      "/v3/api-docs/**",
      "/v3/api-docs",
      "/swagger-ui/**",
      "/swagger-ui.html",
      "/swagger-resources/**",
      "/webjars/**"
  };

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> {
          auth.requestMatchers("/user/").permitAll()
              .requestMatchers("/auth/user").permitAll()
              .requestMatchers(SWAGGER_LIST).permitAll();
          auth.anyRequest().authenticated();
        }).addFilterBefore(securityFilter, BasicAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}