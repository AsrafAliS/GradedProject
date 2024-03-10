package com.gl.graded.project.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

// This Class Created to Provide service for buisness logic operations

@Service
public class EmployeeService {
	

	@Autowired
	EmployeeRepository repo;

	// Method for to create Employee
	public void create(Employee employee) {
		repo.save(employee);
	}

	// Method for to Read
	public List<Employee> read() {
		List<Employee> myList = repo.findAll();
		return myList;
	}


	// Method for to Update 
	public void update(Employee employee) {
		repo.save(employee);
	}

	// Method for to delete
	public void delete(Employee employee) {
		repo.delete(employee);
	}
	
	
	// Method For to Search By Name 
		public List<Employee> filterByFirstName(String columnName, String searchKey) {
			//Create a dummy object based on the searchKey
			Employee dummy = new Employee();
			dummy.setFirstname(searchKey);

			ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher(columnName, ExampleMatcher.GenericPropertyMatchers.exact()).withIgnorePaths("id", "lastname", "email");
			
			
			Example<Employee> example = Example.of(dummy, exampleMatcher);
			
			return repo.findAll(example);
		}

	
	//Method to Sort Only
	public List<Employee> getBySortOnly(Direction direction, String columnName) {
		return repo.findAll(Sort.by(direction, columnName));
	}
	
	// Method to Get specific Id
		public Employee getClassById(int id) {		
			if (repo.findById(id).isEmpty()) {
				return null;
			}
			else {
				return repo.findById(id).get();
			}
		}
	
	
	
}
