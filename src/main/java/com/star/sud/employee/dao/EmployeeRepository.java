/**
 * 
 */
package com.star.sud.employee.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.star.sud.employee.model.TEmployee;

/**
 * @author sudarshan
 *
 */
public interface EmployeeRepository extends JpaRepository<TEmployee, Integer> {

	@Query("from TEmployee o where o.empEmail= ?1")
	public TEmployee employeesByEmail(String name);

}
