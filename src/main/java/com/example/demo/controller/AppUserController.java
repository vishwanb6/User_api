package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AppUserDto;
import com.example.demo.dto.LoginRequest;
import com.example.demo.entities.AppUser;
import com.example.demo.service.AppUserService;

@RestController
@RequestMapping("/api/v1/users")
public class AppUserController {

	@RequestMapping("/t")
	public String test() {
	    System.out.println("test method called");
	    return "working fine";
	}
	@PostMapping("/t1")
    public String createUser1() {
    	System.out.println("post method");
        return "post is working";
    }
		
	 @Autowired
    private AppUserService userService;

    @GetMapping("/")
    public List<AppUser> getAllUsers() {
    	System.out.println("getAllUsers controller...");
        return userService.getAllUsers();
    }
    

    @PostMapping("/register")
    public AppUser createUser(@RequestBody AppUserDto userDTO) {
    	System.out.println("post method");
        return userService.createUser(userDTO);
    }

    // below is optional 
   /* @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("Login request: " + loginRequest);
        try {
            String response = userService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Login failed: " + e.getMessage());
        }
    }
    */
    
    
    
    @GetMapping("/{id}")
    public ResponseEntity<AppUser> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppUser> updateUser(@PathVariable Long id, @RequestBody AppUserDto userDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
	
	

}
