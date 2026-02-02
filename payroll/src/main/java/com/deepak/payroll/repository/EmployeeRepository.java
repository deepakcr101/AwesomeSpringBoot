package com.deepak.payroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.deepak.payroll.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}