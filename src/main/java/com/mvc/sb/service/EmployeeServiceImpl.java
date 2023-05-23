package com.mvc.sb.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mvc.sb.model.Employee;
import com.mvc.sb.payloads.EmployeeDto;
import com.mvc.sb.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<EmployeeDto> getAllEmployees() {
		List<Employee> listOfEmployee=this.employeeRepository.findAll();
		List<EmployeeDto> listOfEmployeeDtos= listOfEmployee.stream()
				.map(employee->modelMapper.map(employee, EmployeeDto.class))
				.collect(Collectors.toList());
		return listOfEmployeeDtos;
	}

	@Override
	public void saveEmployee(EmployeeDto employeeDto) {
		Employee employee=this.modelMapper.map(employeeDto, Employee.class);
		this.employeeRepository.save(employee);
		
	}

	@Override
	public EmployeeDto getEmployeeById(long id) {
		Optional<Employee> optional=this.employeeRepository.findById(id);
		Employee employee=null;
		if(optional.isPresent()) {
			employee=optional.get();
		}else {
			throw new RuntimeException("Employee not found for id " + id);
		}
		return this.modelMapper.map(employee, EmployeeDto.class);
	}

	@Override
	public void updateEmployee(EmployeeDto employeeDto, long id) {
           Employee employee=this.employeeRepository.findById(id).get();
           employee.setFirstName(employeeDto.getFirstName());
           employee.setLastName(employeeDto.getLastName());
           employee.setEmail(employeeDto.getEmail());
           this.employeeRepository.save(employee);
	}

	@Override
	public void deleteEmployee(long id) {
		this.employeeRepository.deleteById(id);
	}

	@Override
	public void deleteEmployees() {
		this.employeeRepository.deleteAll();
	}

	@Override
	public Page<Employee> findPaginated(int pageNo, int pageSize,String sortField, String sortDirection) {
		
		Sort sort= sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? 
				Sort.by(sortField).ascending() : Sort.by(sortField).descending(); 
		
//		Pageable pageable=PageRequest.of(pageNo-1, pageSize);
		Pageable pageable=PageRequest.of(pageNo-1, pageSize,sort);
	    Page<Employee> employeesPage=this.employeeRepository.findAll(pageable);
	    
		return employeesPage;
	}
}
