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
	// - besically this class is used to create the beans related to spring security.
	// 1st bean: PasswordEncoder : is used to encode and password and spring security use this bean for password validation prupose.
	// 2nd bean: SecurityFilterChain bean: it is used to auhonticate some specified urls using HttpSecurity pre-defined class.
	// 3rd bean: AuthenticationManager bean: AuthenticationManager is a central interface that handles the authentication process. It is responsible for processing authentication requests and determining whether the credentials provided are valid.

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
    	  System.out.println("SecurityFilterChain executes...");
    	  
    /*	//1. Form-based Authentication
       http.csrf().disable()
	         .authorizeHttpRequests()
	         .requestMatchers("/api/v1/users/register", "/api/v1/users/t", "/api/v1/users/t1","/api/v1/users/login").permitAll() // Allow public access to these endpoints
	         .requestMatchers(HttpMethod.GET, "/api/v1/users/").authenticated() // Require authentication for this endpoint
	         .anyRequest().authenticated() // All other requests require authentication
	         .and()
	         .formLogin() // Enable form-based authentication
	         .permitAll() // Allow all to see the login page
	         .and()
	         .sessionManagement()
	         .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED); // Allow sessions if needed
*/
    	  
    	  // 2. Basic Authentication: we can pass the username and password in authorization header.. 
    	/*  http.csrf().disable()
          .authorizeRequests()
          .requestMatchers("/api/v1/users/register", "/api/v1/users/t", "/api/v1/users/t1","/api/v1/users/login").permitAll() 
          .anyRequest().authenticated() // Protect all endpoints
          .and()
          .httpBasic(); // Enable Basic Auth.. // we can access the authonticated urls by Basic Auth in header...
    	  */
    	  
    	  // combination of both: Basic Authentication + Form-based Authentication
    	  http.csrf().disable()
    	    .authorizeRequests()
    	        .requestMatchers("/api/v1/users/register", "/api/v1/users/login").permitAll() // Allow access to login and register
    	        .requestMatchers("/api/**").authenticated() // Protect API endpoints
    	        .anyRequest().authenticated() // Protect all other endpoints
    	    .and()
    	    .formLogin()               // Form-based Authentication
    	       // .loginPage("/login") // Custom login page
    	       // .defaultSuccessUrl("/home") // Redirect after successful login
    	        .permitAll() // Allow everyone to see the login page
    	    .and()
    	    .httpBasic(); // Optional, if you want to keep it    //  Basic Authentication

    	  
    	  
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


/*
Notes:Below is a comprehensive overview of the primary methods in `HttpSecurity`, along with their commonly used sub-methods. Each section includes the method's purpose and example usages.

1. csrf()
Configures Cross-Site Request Forgery (CSRF) protection.

- Sub-methods:
  - `disable()`: Disables CSRF protection.
		http.csrf().disable();

  - `csrfTokenRepository(CsrfTokenRepository)`: Sets a custom CSRF token repository.
		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

2. authorizeRequests(): 
- Starts the configuration for request-based authorization.
- Sub-methods:
  - `antMatchers(String... patterns)`: Specifies URL patterns and their associated access rules.
    http.authorizeRequests().antMatchers("/public/").permitAll();

  - `anyRequest()`: Matches any request not matched by previous `antMatchers()`.
    http.authorizeRequests().anyRequest().authenticated();

  - `hasRole(String role)`: Restricts access based on user roles.
    http.authorizeRequests().antMatchers("/admin/").hasRole("ADMIN");

  - `permitAll()`: Allows unrestricted access to specified paths.
    http.authorizeRequests().antMatchers("/public/").permitAll();

  - `denyAll()`: Denies access to specified paths.
    http.authorizeRequests().antMatchers("/admin/").denyAll();

3. formLogin()
- Configures form-based authentication.
- Sub-methods:
  - `loginPage(String loginPage)`: Specifies a custom login page.
    http.formLogin().loginPage("/login").permitAll();

  - `defaultSuccessUrl(String url, boolean alwaysUse)`: Sets the URL to redirect to after successful login.
    http.formLogin().defaultSuccessUrl("/home", true);

  - `failureUrl(String url)`: Specifies the URL to redirect to on authentication failure.
    http.formLogin().failureUrl("/login?error=true");

  - `usernameParameter(String usernameParameter)`: Customizes the username parameter name.
    http.formLogin().usernameParameter("email");

  - `passwordParameter(String passwordParameter)`: Customizes the password parameter name.
    http.formLogin().passwordParameter("pass");

4. logout()
- Configures logout functionality.
- Sub-methods:
  - `logoutUrl(String logoutUrl)`: Specifies the logout URL.
    http.logout().logoutUrl("/logout");

  - `logoutSuccessUrl(String logoutSuccessUrl)`: Sets the URL to redirect to after logout.
    http.logout().logoutSuccessUrl("/login?logout=true");

  - `invalidateHttpSession()`: Invalidates the HTTP session on logout.
    http.logout().invalidateHttpSession(true);

  - `deleteCookies(String... cookieNames)`: Deletes specified cookies upon logout.
    http.logout().deleteCookies("JSESSIONID");

5. httpBasic()
- Enables HTTP Basic authentication.
- Sub-methods:
  - `authenticationEntryPoint(AuthenticationEntryPoint)`: Customizes the entry point for unauthenticated requests.
    http.httpBasic().authenticationEntryPoint(new CustomEntryPoint());

6. sessionManagement()
- Configures session management.
- Sub-methods:
  - `maximumSessions(int maxSessions)`: Limits the number of sessions per user.
    http.sessionManagement().maximumSessions(1);

  - `sessionFixation().newSession()`: Changes the session ID on login to prevent session fixation attacks.
    http.sessionManagement().sessionFixation().newSession();

  - `sessionCreationPolicy(SessionCreationPolicy)`: Defines the session creation policy.
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

7. exceptionHandling()
- Configures exception handling for authentication and access-denied events.
- Sub-methods:
  - `accessDeniedPage(String accessDeniedUrl)`: Sets a custom access denied page.
    http.exceptionHandling().accessDeniedPage("/403");

  - `authenticationEntryPoint(AuthenticationEntryPoint)`: Customizes the entry point for unauthorized access.
    http.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());

8. requiresChannel()
- Specifies channel security (HTTP vs. HTTPS).
- Sub-methods:
  - `anyRequest()`: Matches any request.
    http.requiresChannel().anyRequest().requiresSecure(); // Enforces HTTPS

9. headers()
- Configures HTTP response headers.
- Sub-methods:
  - `frameOptions()`: Configures X-Frame-Options header.
    http.headers().frameOptions().sameOrigin(); // Allow framing from same origin

  - `httpStrictTransportSecurity()`: Configures HTTP Strict Transport Security (HSTS).
    http.headers().httpStrictTransportSecurity().disable(); // Disable HSTS

10. rememberMe()
- Configures "remember me" functionality for persistent login.
- Sub-methods:
  - `key(String key)`: Specifies a unique key for remember-me functionality.
    http.rememberMe().key("uniqueAndSecret");

  - `tokenValiditySeconds(int seconds)`: Sets the validity period for the remember-me token.
    http.rememberMe().tokenValiditySeconds(86400); // 1 day

Example Combined Configuration

Here’s an example of how you might use some of these methods together in a Spring Security configuration:

```java
@Override
protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf()
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .and()
        .authorizeRequests()
            .antMatchers("/public/").permitAll()
            .antMatchers("/login", "/register").permitAll()
            .anyRequest().authenticated()
        .and()
        .formLogin()
            .loginPage("/login").permitAll()
            .defaultSuccessUrl("/home", true)
            .failureUrl("/login?error=true")
        .and()
        .logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login?logout=true")
        .and()
        .sessionManagement()
            .maximumSessions(1);
}
```

Ex: for combination of both: Basic Authentication + Form-based Authentication. this will work in both postman and browser.
    	  http.csrf().disable()
    	    .authorizeRequests()
    	        .requestMatchers("/api/v1/users/register", "/api/v1/users/login").permitAll() // Allow access to login and register
    	        .requestMatchers("/api/**").authenticated() // Protect API endpoints
    	        .anyRequest().authenticated() // Protect all other endpoints
    	    .and()
    	    .formLogin()               // ***using this we can do Form-based Authentication
    	       // .loginPage("/login") // Custom login page   //if not spcify this then it will consider the default login page provided by security
    	       // .defaultSuccessUrl("/home") // Redirect after successful login
    	        .permitAll() // Allow everyone to see the login page
    	    .and()
    	    .httpBasic(); // Optional, if you want to keep it   //  // ***using this we can do Basic Authentication



Conclusion

This comprehensive overview provides insight into the primary methods of `HttpSecurity` and their sub-methods, allowing you to configure various security aspects of your Spring application effectively. By leveraging these methods, you can tailor security to meet the needs of your specific application architecture.




*/










