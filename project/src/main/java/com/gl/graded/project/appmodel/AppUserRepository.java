package com.gl.graded.project.appmodel;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
	
	//creating custom methods using JPA
	Optional<AppUser> findByName(String name);
	
	
}
