package com.mvc.sb.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.mvc.sb.model.Employee;
import com.mvc.sb.payloads.EmployeeDto;

public interface EmployeeService {

	List<EmployeeDto> getAllEmployees();
	void saveEmployee(EmployeeDto employeeDto);
	EmployeeDto getEmployeeById(long id);
	void updateEmployee(EmployeeDto employeeDto, long id);
	void deleteEmployee(long id);
	void deleteEmployees();
	Page<Employee> findPaginated(int pageNo, int pageSize,String sortField, String sortDirection);
}
