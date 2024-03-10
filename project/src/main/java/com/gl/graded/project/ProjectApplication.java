package com.gl.graded.project;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.gl.graded.project.appmodel.AppUser;
import com.gl.graded.project.appmodel.AppUserService;

@SpringBootApplication
public class ProjectApplication implements CommandLineRunner{
	
	@Autowired
	AppUserService appUserService;

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		Set<String> authAdmin = new HashSet<>();
		authAdmin.add("Admin");
		
		//create Object for encoding 
		PasswordEncoder en=new BCryptPasswordEncoder();
		
		
		
		AppUser appAdmin = new AppUser(1, "Admin", "Admin", en.encode("PasswordofAdmin"), authAdmin);
		appUserService.add(appAdmin);
		
	}

}
