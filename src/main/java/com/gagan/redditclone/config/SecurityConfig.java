/**
 * @author Gagandeep Singh
 * @email singh.gagandeep3911@gmail.com
 * @create date 2020-09-12 21:48:33
 * @modify date 2020-09-12 21:48:33
 * @desc [description]
 */
package com.gagan.redditclone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  /**
   * csrf is disabled in stateless APIs else it causes undesired issues
   * Used with sessions and cookies
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
    // Anyone can access /api/auth
    .authorizeRequests()
    .antMatchers("/api/auth/**")
    .permitAll()
    // All requests must be authorized
    .anyRequest()
    .authenticated();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
