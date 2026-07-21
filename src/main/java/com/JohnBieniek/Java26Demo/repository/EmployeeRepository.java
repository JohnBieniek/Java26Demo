package com.JohnBieniek.Java26Demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JohnBieniek.Java26Demo.model.organization.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    java.util.List<Employee> findByDeletedAtIsNullOrderByNameAsc();

    java.util.List<Employee> findByTeamIdInAndDeletedAtIsNullOrderByNameAsc(java.util.Collection<Long> teamIds);
}
