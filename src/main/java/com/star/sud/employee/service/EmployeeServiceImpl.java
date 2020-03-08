package com.star.sud.employee.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.star.sud.employee.dao.EmployeeRepository;
import com.star.sud.employee.dto.Employee;
import com.star.sud.employee.exception.EmployeeCreationException;
import com.star.sud.employee.exception.NoRecordsFoundException;
import com.star.sud.employee.exception.RequestParamException;
import com.star.sud.employee.model.TEmployee;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

	@Autowired
	private EmployeeRepository repository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.star.sud.employee.service.IEmployeeService#findAllEmployees()
	 */
	@Override
	public List<Employee> retrieveAllEmployees() {
		List<Employee> employees = new ArrayList<Employee>();
		List<TEmployee> tEmployees = repository.findAll();
		if (null == tEmployees || tEmployees.size() <= 0)
			throw new NoRecordsFoundException("No Employee Records Found!");

		for (TEmployee tEmployee : tEmployees) {
			Employee employee = new Employee();
			BeanUtils.copyProperties(tEmployee, employee);
			employees.add(employee);
		}

		return employees;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.star.sud.employee.service.IEmployeeService#getEmployeeById(int)
	 */
	@Override
	public Employee getEmployeeById(int empId) {
		Optional<TEmployee> tEmployee = repository.findById(empId);

		if (!tEmployee.isPresent())
			throw new NoRecordsFoundException("No records found for the id - " + empId);

		Employee employee = new Employee();
		BeanUtils.copyProperties(tEmployee.get(), employee);

		return employee;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.star.sud.employee.service.IEmployeeService#createEmployee(com.star.sud.
	 * employee.dto.Employee)
	 */
	@Override
	public ResponseEntity<Object> createEmployee(Employee employee) {

		if (null == employee)
			throw new EmployeeCreationException("input param is null or empty");

		TEmployee tEmployee = null;

		tEmployee = repository.employeesByEmail(employee.getEmpEmail());
		if (tEmployee != null)
			throw new EmployeeCreationException("Email entered is already exists");

		TEmployee entity = new TEmployee();
		BeanUtils.copyProperties(employee, entity);
		tEmployee = repository.save(entity);
		if (null == tEmployee)
			throw new EmployeeCreationException("Failed to Create Employee Record");

		URI localtion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(tEmployee.getEmpId()).toUri();

		return ResponseEntity.created(localtion).build();
	}

	/**
	 * @param empId
	 * @return
	 */
	public void deleteEmployee(Integer empId) {

		if (empId == null)
			throw new RequestParamException("request param is null or empty");

		Optional<TEmployee> tEmployee = repository.findById(empId);

		if (!tEmployee.isPresent())
			throw new NoRecordsFoundException("No records found for the id - " + empId);

		repository.delete(tEmployee.get());

	}

}
