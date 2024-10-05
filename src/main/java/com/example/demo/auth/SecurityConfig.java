package com.example.demo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//step-3: create configuration class for spring security, use @Confiuration and @EnableWebSecurity on class level.
	// - besically this class is used to create the beans related to cpring security.
	// 1st bean: PasswordEncoder : is used to encode and password and spring security use this bean for password validation prupose.
	// 2nd bean: SecurityFilterChain bean: it is used to auhonticate some specified urls using HttpSecurity pre-defined class.
	// 3rd bean: UserDetailsService bean: Spring Security uses this bean during the login process to call the "loadUserByUsername" method.

@Configuration     // this is used for creating the beans
@EnableWebSecurity // @EnableWebSecurity is an annotation in Spring Security that activates the security features of the framework for your web application. features like:SecurityFilterChain,UserDetailsService-in memory auth  
public class SecurityConfig {

	 @Autowired
    private CustomUserDetailsService customUserDetails;
    
    @Bean
    public PasswordEncoder passwordEncodeMethod() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	/*  http.csrf().disable()
          .authorizeHttpRequests()
          .requestMatchers("/api/v1/users/register", "/api/v1/users/t", "/api/v1/users/t1").permitAll() // Allow access to both GET and POST
          .anyRequest().authenticated()
          .and()
          .formLogin().permitAll(); // Allow form login for other endpoints
    	  */
    	  
    	 http.csrf().disable()
	         .authorizeHttpRequests()
	         .requestMatchers("/api/v1/users/register", "/api/v1/users/t", "/api/v1/users/t1").permitAll() // Allow public access to these endpoints
	         .requestMatchers(HttpMethod.GET, "/api/v1/users/").authenticated() // Require authentication for this endpoint
	         .anyRequest().authenticated() // All other requests require authentication
	         .and()
	         .formLogin() // Enable form-based authentication
	         .permitAll() // Allow all to see the login page
	         .and()
	         .sessionManagement()
	         .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED); // Allow sessions if needed

    	  
    	  
        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
    	System.out.println("AuthenticationManager executes...");
    	AuthenticationManagerBuilder authenticationManagerBuilder = 
                http.getSharedObject(AuthenticationManagerBuilder.class);
        
        authenticationManagerBuilder
            .userDetailsService(customUserDetails)
            .passwordEncoder(passwordEncodeMethod()); // Set the password encoder
        
        return authenticationManagerBuilder.build();
    }
	
}






//	@Autowired
//public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//  auth.userDetailsService(customUserDetails).passwordEncoder(passwordEncoder());
//}

//@Bean
//public UserDetailsService userDetailsService() {
//   return new CustomUserDetailsService(); // Your custom implementation
//}



/*  // this bean is for in-memory authontication
@Bean
public UserDetailsService userDetailsService() {
	UserDetails user = User.withDefaultPasswordEncoder()
		.username("user")
		.password("password")
		.roles("USER")
		.build();
	UserDetails admin = User.withDefaultPasswordEncoder()
		.username("admin")
		.password("password")
		.roles("ADMIN", "USER")
		.build();
	return new InMemoryUserDetailsManager(user, admin);
}
*/


/*
@Bean
public WebSecurityCustomizer webSecurityCustomizer() {
	return (web) -> web.ignoring()
	// Spring Security should completely ignore URLs starting with /resources/
			.requestMatchers("/resources/**");
}
*/

