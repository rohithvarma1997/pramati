package com.example.demo.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

	//To configure a custom username and password 
	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		List<UserDetails> users = new ArrayList<>();
		users.add(User.withDefaultPasswordEncoder().username("rohith").password("123456").roles("USER").build());
		return new InMemoryUserDetailsManager(users);
	}

	//To give access to h2 without authentication to make update and delete operations
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		.antMatchers("/h2-console/**")
		.permitAll()
		.and()
		.authorizeRequests()
        .antMatchers("/**")
        .authenticated()
        .anyRequest()
        .permitAll() 
        .and()
        .formLogin()
        .and()
        .httpBasic();
		
		http.csrf().disable();

		http.headers().frameOptions().disable();
	}

}
