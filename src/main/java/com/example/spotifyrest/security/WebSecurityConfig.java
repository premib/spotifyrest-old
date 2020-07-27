package com.example.spotifyrest.security;

import com.example.spotifyrest.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private JWTProvider jwtProvider;

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.csrf().disable();
//    http.cors().disable();
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.authorizeRequests()
            .antMatchers("/v1/auth/**").permitAll()
            .antMatchers("/admin/**").permitAll()
            .antMatchers("/v1/user/**").hasRole("USER")
        .anyRequest().permitAll();

    // Add JWT Filter
    http.apply(new JWTSecurityConfigurerAdapter(jwtProvider));

  }

  @Bean
  public PasswordEncoder passwordEncoder() {

    return new BCryptPasswordEncoder(12);
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

}
