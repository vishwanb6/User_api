package com.example.demo.service;

import java.util.List;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AppUserDto;
import com.example.demo.dto.LoginRequest;
import com.example.demo.entities.AppUser;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repo.AppUserRepo;

@Service
public class AppUserService {

	@Autowired
	PasswordEncoder pswdEncoder;
	
	 @Autowired
    private AppUserRepo userRepository;
	 
	/* @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;
    */

    public List<AppUser> getAllUsers() {
        return userRepository.findAll();
    }

    public AppUser getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public AppUser createUser(AppUserDto userDTO) {
    	AppUser user = new AppUser();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPswd( pswdEncoder.encode(userDTO.getPswd()) );
        
        // Set roles based on role IDs (fetch roles from the repository)
        return userRepository.save(user);
    }

    public AppUser updateUser(Long id, AppUserDto userDTO) {
    	AppUser user = getUserById(id);
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        // Update roles
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
    	AppUser user = getUserById(id);
        userRepository.delete(user);
    }

    
    
   //this method is optional // written for '/login' controller method 
/*	public String login(LoginRequest loginRequest) {
		// Use the fully qualified class name for Authentication
//        org.springframework.security.core.Authentication authentication = 
//            authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
//        );
//
//        if (authentication.isAuthenticated()) {
//            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
//            // Generate token or handle login success (for JWT, etc.)
//            return "Login successful!"; // Placeholder for actual token or response
//        } else {
//            throw new RuntimeException("Login failed!");
//        }
//		
		org.springframework.security.core.Authentication authentication = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
	        );

	        SecurityContextHolder.getContext().setAuthentication(authentication);

	        // Generate and return a JWT or session token (for simplicity, returning a string here)
	        return "GeneratedToken"; // Replace with actual token generation logic
        
        
	}*/
}
