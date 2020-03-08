package com.star.sud.employee.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.star.sud.employee.dto.Employee;
import com.star.sud.employee.service.EmployeeServiceImpl;

@RestController
public class EmployeeController {

	@Autowired
	EmployeeServiceImpl service;

	@GetMapping(value = "/employees")
	public List<Employee> retrieveAllEmployees() {
		return service.retrieveAllEmployees();
	}

	@GetMapping(value = "/employees/{empId}")
	public Employee getEmployee(@PathVariable("empId") int empId) {
		return service.getEmployeeById(empId);
	}

	@PostMapping(value = "/employees")
	public @ResponseBody ResponseEntity<Object> createEmployee(@Valid @RequestBody Employee employee) {
		return service.createEmployee(employee);
	}

	@DeleteMapping(value = "/employees/{empId}")
	public void deleteEmployee(@PathVariable("empId") Integer empId) {
		service.deleteEmployee(empId);
	}

}
