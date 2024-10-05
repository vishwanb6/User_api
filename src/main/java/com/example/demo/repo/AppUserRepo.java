package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.AppUser;

public interface AppUserRepo extends JpaRepository<AppUser, Long> {

	AppUser findByName(String name);
}
