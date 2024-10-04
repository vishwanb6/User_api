package com.example.demo.dto;

import java.util.Set;

public class AppUserDto {

	private Long id;
    private String name;
    private String email;
    private Set<Long> roleIds;
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Set<Long> getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(Set<Long> roleIds) {
		this.roleIds = roleIds;
	}
	@Override
	public String toString() {
		return "AppUserDto [id=" + id + ", name=" + name + ", email=" + email + ", roleIds=" + roleIds + "]";
	}
    
    
}
