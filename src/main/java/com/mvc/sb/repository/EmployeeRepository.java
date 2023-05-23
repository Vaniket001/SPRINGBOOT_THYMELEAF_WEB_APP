package com.mvc.sb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mvc.sb.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
