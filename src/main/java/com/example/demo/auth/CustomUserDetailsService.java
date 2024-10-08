package com.example.demo.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.util.Assert;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entities.AppUser;
import com.example.demo.repo.AppUserRepo;


//step-2: create customUserDetails and impliment the UserDetailsService interface, 
	// which will override the loadUserByUsername(String username) - this method will give us username entered by client 
	// then we will fetch the user data of this username from database and fill into userDetails class.
	// then return the UserDetails object. 
@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	AppUserRepo appUserRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("username: "+username);
		AppUser usr = appUserRepo.findByName(username);
		System.out.println("UserDetails are: "+usr);
		if (usr == null) {
            throw new UsernameNotFoundException("User not found - step-2");
        }
		
		UserBuilder userBuilder = org.springframework.security.core.userdetails.User.withUsername(usr.getName())
																					.password(usr.getPswd())
																					.roles("USER");		
		
		return userBuilder.build();
		
		/*  even we can do by using below line also..
		return new User(String username, String password, boolean enabled, boolean accountNonExpired,
				boolean credentialsNonExpired, boolean accountNonLocked,
				Collection<? extends GrantedAuthority> authorities);
				*/
	}

	
	
	/* # UserBuilder: 
	  - it is class, that helps in constructing user details for authentication purposes. 
	  - UserBuilder simplifies user creation in Spring Security, making it easier to configure in-memory authentication.
	  - this class provides some methods:
		  1. below 3 methods are used to set the username, passwd and roles.
		  
		   public UserBuilder username(String username) {
				Assert.notNull(username, "username cannot be null");
				this.username = username;
				return this;
			}	
			 
			public UserBuilder password(String password) {
				Assert.notNull(password, "password cannot be null");
				this.password = password;
				return this;
			}
			
			public UserBuilder roles(String... roles) {
				List<GrantedAuthority> authorities = new ArrayList<>(roles.length);
				for (String role : roles) {
					Assert.isTrue(!role.startsWith("ROLE_"),
							() -> role + " cannot start with ROLE_ (it is automatically added)");
					authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
				}
				return authorities(authorities);
			}
			
			2. below method will return the userdetails object.
				public UserDetails build() {
					String encodedPassword = this.passwordEncoder.apply(this.password);
					return new User(this.username, encodedPassword, !this.disabled, !this.accountExpired,
							!this.credentialsExpired, !this.accountLocked, this.authorities);
				}
	
				Note: User constructor is like below:
					public User(String username, String password, boolean enabled, boolean accountNonExpired,
							boolean credentialsNonExpired, boolean accountNonLocked,
							Collection<? extends GrantedAuthority> authorities) {
						Assert.isTrue(username != null && !"".equals(username) && password != null,
								"Cannot pass null or empty values to constructor");
						this.username = username;
						this.password = password;
						this.enabled = enabled;
						this.accountNonExpired = accountNonExpired;
						this.credentialsNonExpired = credentialsNonExpired;
						this.accountNonLocked = accountNonLocked;
						this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
					}
	
		
		# UserDetailsService: 
		- Definition: UserDetailsService is an interface in Spring Security that helps load user information from a database or other data sources.
		- UserDetailsService is required because it provides the mechanism for Spring Security to verify whether the user trying to log in is valid or not. It serves as a bridge between your application’s user data and Spring Security's authentication process.
		- The interface defines a single method:
			UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
		- Why Spring security Need It?: 
			- User Attempts to Log In: When a user submits their username and password, Spring Security needs to verify if those credentials are valid.
			- Calling UserDetailsService: Spring Security calls the loadUserByUsername method of your UserDetailsService implementation to retrieve user details from your data source (like a database).
			- Validating Credentials: Your implementation fetches the user's information, including their password and roles. Spring Security then checks if the provided password matches the one stored for that user.
			- Authentication Result: If the user is found and the credentials match, Spring Security considers the user authenticated. If not, it will reject the login attempt, typically with an error message.
	 */
}
