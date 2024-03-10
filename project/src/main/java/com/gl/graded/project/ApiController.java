package com.gl.graded.project;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gl.graded.project.appmodel.AppUser;
import com.gl.graded.project.appmodel.AppUserService;
import com.gl.graded.project.model.Employee;
import com.gl.graded.project.model.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;



@RestController
public class ApiController {
	
	@Autowired
	EmployeeService Servicelayer;
	
	@Autowired
	AppUserService UserService;
	
	@GetMapping("api/getAllEmployee")
	public List<Employee> getAllEmployees() {
		return Servicelayer.read();
	}
	
	@GetMapping("/api/getEmployee")
	public Employee getemp(@RequestParam int id) {
		Employee emp1 = Servicelayer.getClassById(id);
		return emp1;
	}
	
	@PostMapping("api/addUserRole")
	public String addingrole(@RequestParam int id, @RequestParam String username, @RequestParam String password, Authentication authentication, SecurityContextHolder auth) {
		
		String acceptedRole = "admin"; 
		boolean roleFound = false;

		//the person who logged in last
		System.out.println("Current login by: " + authentication.getName());

		//To find the role 
		@SuppressWarnings("static-access")
		Collection<?extends GrantedAuthority> grantedRoles = auth.getContext().getAuthentication().getAuthorities();

		for (int i=0; i<grantedRoles.size(); i++) {
			String role = grantedRoles.toArray()[i].toString();
			System.out.println("My Role: " + role);

			if (role.equalsIgnoreCase(acceptedRole)) {
				roleFound = true;
			}
		}
		if (roleFound) {
			Set<String> authUser = new HashSet<>();
			authUser.add("User");
			
			//create encode object
			PasswordEncoder en=new BCryptPasswordEncoder();

			AppUser appUser = new AppUser(id, username, username, en.encode(password), authUser);
			UserService.add(appUser);
			return "User added successfully";
		}
		return " Sorry ! Cant Access";
	}
	
	@PostMapping("api/addNewEmployee")
	public String addemp(@RequestParam int id, @RequestParam String firstname, @RequestParam String lastname, @RequestParam String email, Authentication authentication, SecurityContextHolder auth) {

		String acceptedRole = "admin";
		boolean roleFound = false;

		//the person who logged in last
		System.out.println("Current login by: " + authentication.getName());

		//To find the role
		@SuppressWarnings("static-access")
		Collection<?extends GrantedAuthority> grantedRoles = auth.getContext().getAuthentication().getAuthorities();
		

		for (int i=0; i<grantedRoles.size(); i++) {
			String role = grantedRoles.toArray()[i].toString();
			System.out.println("My Role: " + role);

			if (role.equalsIgnoreCase(acceptedRole)) {
				roleFound = true;
			}
		}
		if (roleFound) {
			if (Servicelayer.getClassById(id) != null) {
				return "Duplicate Id";
			}
			Employee emp1 = new Employee(id, firstname, lastname, email);
			Servicelayer.create(emp1);
			return "Employee Added";
		}
		return "cant Access!";


	}


	
	@DeleteMapping("api/deleteEmployee")
	public String deleteEmployee(@RequestParam int id, Authentication authentication, SecurityContextHolder auth) {
		
		String acceptedRole = "admin"; 
		boolean roleFound = false;

		//the person who logged in last
		System.out.println("Current login by: " + authentication.getName());


		//To find the role
		@SuppressWarnings("static-access")
		Collection<?extends GrantedAuthority> grantedRoles = auth.getContext().getAuthentication().getAuthorities();
		

		for (int i=0; i<grantedRoles.size(); i++) {
			String role = grantedRoles.toArray()[i].toString();
			System.out.println("My Role: " + role);

			if (role.equalsIgnoreCase(acceptedRole)) {
				roleFound = true;
			}
		}
		if (roleFound) {
			Employee emp1 = new Employee(id, "", "", "");
			Servicelayer.delete(emp1);
			return "Employee Deleted";
		}
		return "Cant Access";
	}
	
	@PutMapping("api/updateEmployee")
	public String updateEmployee(@RequestParam int id, @RequestParam String firstname, @RequestParam String lastname, @RequestParam String email, Authentication authentication, SecurityContextHolder auth) {

		String acceptedRole = "admin"; // 
		boolean roleFound = false;

		//the person who logged in last
		System.out.println("Current login by: " + authentication.getName());

		//To find the role
		@SuppressWarnings("static-access")
		Collection<?extends GrantedAuthority> grantedRoles = auth.getContext().getAuthentication().getAuthorities();
		

		for (int i=0; i<grantedRoles.size(); i++) {
			String role = grantedRoles.toArray()[i].toString();
			System.out.println("My Role: " + role);

			if (role.equalsIgnoreCase(acceptedRole)) {
				roleFound = true;
			}
		}
		if (roleFound) {
			Employee emp1 = new Employee(id, firstname, lastname, email);
			Servicelayer.update(emp1);
			return "Employee Updated";
		}
		return "Cant Access";
	}
	
	@Operation(summary = "Filtering by firstname by method")
	@GetMapping("api/filterByFirstName")
	public List<Employee> filterBySubject(@RequestParam (defaultValue = "firstname") String columnName,@RequestParam String searchKey) {
		return Servicelayer.filterByFirstName(columnName, searchKey);
	}
	
	@Operation(summary = "Send  1 for ascending direction, others for descending.")
	@GetMapping("api/sorting")
	public List<Employee> getBySorting(@RequestParam int direction,@RequestParam String columnName) {
		
		if (direction == 1) {
			List<Employee> lib = Servicelayer.getBySortOnly(Direction.ASC, columnName);
			return lib;
		}
		else {
			List<Employee> lib = Servicelayer.getBySortOnly(Direction.DESC, columnName);
			return lib;
		}
		
	}
	
	

}
