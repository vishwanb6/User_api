package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Role;
import com.example.demo.service.RoleService;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

	   @Autowired
	    private RoleService roleService;

	    @GetMapping("/")
	    public List<Role> getAllRoles() {
	        return roleService.getAllRoles();
	    }

	    @PostMapping("/create")
	    public Role createRole(@RequestBody Role role) {
	        return roleService.createRole(role);
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
	        return ResponseEntity.ok(roleService.getRoleById(id));
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
	        roleService.deleteRole(id);
	        return ResponseEntity.noContent().build();
	    }
	    
}
