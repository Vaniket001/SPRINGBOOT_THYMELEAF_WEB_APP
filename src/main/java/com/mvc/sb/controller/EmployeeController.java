package com.mvc.sb.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mvc.sb.model.Employee;
import com.mvc.sb.payloads.EmployeeDto;
import com.mvc.sb.service.EmployeeService;

@Controller
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@GetMapping(value={"/","/employees"})
	public String viewHomePage(Model model) {
		
//		model.addAttribute("listOfEmpolyeesDto",employeeService.getAllEmployees());
//		return "index";
		return findPaginated(1,"firstName","asc",model);
	}
	
	@GetMapping("/showNewEmployeeForm")
	public String showNewEmployeeForm(Model model) {
		
		EmployeeDto EmployeeDto=new EmployeeDto();
		model.addAttribute("employeeDto",EmployeeDto);
		return "new_employee";
	}
	
	@PostMapping("/saveEmployee")
	public String saveEmployee(@ModelAttribute("employeeDto") EmployeeDto employeeDto) {
		this.employeeService.saveEmployee(employeeDto);
		return "redirect:/";
	}
	
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable("id") long eid,Model model) {
		model.addAttribute("employeeDto",this.employeeService.getEmployeeById(eid));
		return "update_employee";
	}
	
	@PostMapping("/updateEmployee/{id}")
	public String updateEmployee(@ModelAttribute("employeeDto") EmployeeDto employeeDto,
			@PathVariable("id") long eid) {
		this.employeeService.updateEmployee(employeeDto, eid);
		return "redirect:/";
	}
	
	@GetMapping("deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable("id") long eid) {
		this.employeeService.deleteEmployee(eid);
		return "redirect:/employees";
	}
	
	@GetMapping("/deleteEmployees")
	public String deleteAllEmployees() {
		this.employeeService.deleteEmployees();
		return "delete_success";
	}
	
	
	// /page/1/sortField=name&sortDirection=asc
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable("pageNo") int pageNo,
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize=5;
		Page<Employee> page=this.employeeService.findPaginated(pageNo, pageSize,sortField,sortDir);
		List<Employee> listOfEmployees=page.getContent();
		
	    List<EmployeeDto>listOfEmpolyeesDto=listOfEmployees.stream()
	    		.map(employee->this.modelMapper.map(employee, EmployeeDto.class))
		        .collect(Collectors.toList());
		
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("listOfEmpolyeesDto", listOfEmpolyeesDto);
		
		model.addAttribute("sortField",sortField);
		model.addAttribute("sortDir",sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		return "index";
	}
}
