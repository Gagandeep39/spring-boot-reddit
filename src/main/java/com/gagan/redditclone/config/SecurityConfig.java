/**
 * @author Gagandeep Singh
 * @email singh.gagandeep3911@gmail.com
 * @create date 2020-09-12 21:48:33
 * @modify date 2020-09-12 21:48:33
 * @desc [description]
 */
package com.gagan.redditclone.config;

import com.gagan.redditclone.security.JwtAuthenticationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.AllArgsConstructor;

@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserDetailsService userDetailsService;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  /**
   * csrf is disabled in stateless APIs else it causes undesired issues Used with
   * sessions and cookies
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
        // Anyone can access /api/auth
        .authorizeRequests().antMatchers("/api/auth/**").permitAll()
        // All requests must be authorized
        .anyRequest().authenticated();
    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * In @Bean(BeanIds.AUTHENTICATION_MANAGER) NeanIds part is optional
   */
  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

}
