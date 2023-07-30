package com.employee.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.exception.ResourceNotFoundException;
import com.employee.model.Employee;
import com.employee.repository.EmployeeRepository;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@GetMapping("/login")
	public ResponseEntity<Employee> login( @RequestHeader("firstName") String firstName, @RequestHeader("emailId") String email) {
	   
		Employee employeeLogin = employeeRepository.findByFirstNameAndEmailId(firstName, email);
	    
	    return ResponseEntity.ok(employeeLogin);
	}
	
	@PostMapping("/login")
	public Employee registerEmployee(@RequestBody Employee employee){
		return employeeRepository.save(employee);
	}

	
	@GetMapping("/employees")
	
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
	}
	
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}
	
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeId(@PathVariable Long id) {
		Employee employee=employeeRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Employee Not Exist With ID:"+ id));
		
		return ResponseEntity.ok(employee);
	}
	
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,@RequestBody Employee employeeDetails){
		Employee employee=employeeRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Employee Not Exist With ID:"+ id));
		
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmailId(employeeDetails.getEmailId());
		
		Employee updatedEmployee=employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}
	
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
		Employee employee=employeeRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Employee Not Exist With ID:"+ id));
		employeeRepository.delete(employee);
		Map<String, Boolean> response=new HashMap<>();
		response.put("Deleted", Boolean.TRUE);
		
	return ResponseEntity.ok(response);
			}
}
