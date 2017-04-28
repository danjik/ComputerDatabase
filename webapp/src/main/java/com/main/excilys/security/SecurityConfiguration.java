package com.main.excilys.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication().withUser("bill").password("abc123").roles("USER");
    auth.inMemoryAuthentication().withUser("admin").password("root123").roles("ADMIN");
    auth.inMemoryAuthentication().withUser("dba").password("root123").roles("ADMIN", "DBA");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.authorizeRequests().antMatchers("/", "/dashboard").permitAll()
        .antMatchers("/addComputer", "/editComputer", "/deleteComputer")
        .access("hasRole('ADMIN') or hasRole('USER')").and().formLogin().loginPage("/login")
        .defaultSuccessUrl("/dashboard").and().logout().permitAll().and().exceptionHandling()
        .accessDeniedPage("/dashboard");

  }

}
