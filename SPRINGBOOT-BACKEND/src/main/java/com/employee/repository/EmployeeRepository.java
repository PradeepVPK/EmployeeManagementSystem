package com.employee.repository;

import org.hibernate.type.StringNVarcharType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employee.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	Employee findByFirstNameAndEmailId(String firstName,String emailId);
}
