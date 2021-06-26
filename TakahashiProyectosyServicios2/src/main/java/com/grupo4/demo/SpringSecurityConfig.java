package com.grupo4.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;

import com.grupo4.demo.models.entity.service.JpaUserDetailsService;


@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
		
	@Autowired
	private JpaUserDetailsService userdetails;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/","/css/**","/js/**","/img/**","/webfonts/**","/login/peticion").permitAll()
		.anyRequest().authenticated()
		.and()
		.formLogin().loginPage("/")
		.permitAll()
		.and()
		.logout().permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/error_403");
	}
	
	
	@Autowired
	public void configurerGLobal(AuthenticationManagerBuilder builder) throws Exception {
		/*PasswordEncoder enconder = passwordEncoder();
		UserBuilder users= User.builder().passwordEncoder(enconder::encode);
		
		builder.inMemoryAuthentication().withUser(users.username("admin").password("123456").roles("ADMIN","USER"));*/
		
		builder.userDetailsService(userdetails).passwordEncoder(passwordEncoder());
	}
}
