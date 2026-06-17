package com.JohnBieniek.Java26Demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JohnBieniek.Java26Demo.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}