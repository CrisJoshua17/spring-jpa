package com.joshua.springeclipse.curso.app;
	
	import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
	import org.springframework.security.core.userdetails.User;
	import org.springframework.security.core.userdetails.UserDetailsService;
	import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
	import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
	import org.springframework.security.web.SecurityFilterChain;

import com.joshua.springeclipse.curso.app.models.services.JpaUserDetailsService;
	
	@EnableMethodSecurity(securedEnabled = true)
	@Configuration
	public class SpringSecurityConfig {
	
		@Autowired
		private LogginSuccesHandler logginSucces;
		
		 @Autowired
		    private DataSource dataSource;
		
	    @Bean
	    private static BCryptPasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	    
	    @Autowired
	    private LogginFailHandler logginFail;
	    
	    @Autowired
	     private JpaUserDetailsService userDetailsService;
	   
	    
	    
	    
	    @Bean
	    SecurityFilterChain filterChain(HttpSecurity http) throws Exception { 
	        http.authorizeHttpRequests(authz -> authz
	                .requestMatchers("/", "/css/**", "/js/**", "/images/**", "/listar","/loggin","/locale","listar**")
	                .permitAll()
	                .anyRequest().authenticated())
	             .formLogin(login -> login
	                     .loginPage("/loggin")
	                     .defaultSuccessUrl("/listar", true).successHandler(logginSucces)
	                     .failureHandler(logginFail)
	                     .permitAll())
	            .logout(logout -> logout
	                    .logoutUrl("/logout")
	                    .logoutSuccessUrl("/loggin?logout=true")
	                    .permitAll()).exceptionHandling((exceptionHandling) ->
	                exceptionHandling
	                    .accessDeniedPage("/error_403"));
	        return http.build();
	    }
	    
	
	    
	    
	    @Autowired
	    public void userDetailsService(AuthenticationManagerBuilder build) throws Exception {
	    build.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	    }

	    

	
	}
