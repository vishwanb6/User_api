package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Role;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repo.RoleRepo;

@Service
public class RoleService {

	 @Autowired
	    private RoleRepo roleRepository;

	    public List<Role> getAllRoles() {
	        return roleRepository.findAll();
	    }

	    public Role getRoleById(Long id) {
	        return roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role not found"));
	    }

	    public Role createRole(Role role) {
	        return roleRepository.save(role);
	    }

	    public void deleteRole(Long id) {
	        Role role = getRoleById(id);
	        roleRepository.delete(role);
	    }
	    
}
