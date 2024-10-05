package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AppUserDto;
import com.example.demo.entities.AppUser;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repo.AppUserRepo;

@Service
public class AppUserService {

	@Autowired
	PasswordEncoder pswdEncoder;
	
	 @Autowired
    private AppUserRepo userRepository;

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
}
