package com.gl.graded.project.model;

import org.springframework.data.jpa.repository.JpaRepository;


//Reporsitory To use Jpa for db
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
