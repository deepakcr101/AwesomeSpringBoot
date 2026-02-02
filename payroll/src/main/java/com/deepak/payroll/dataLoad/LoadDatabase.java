package com.deepak.payroll.dataLoad;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.deepak.payroll.model.Employee;
import com.deepak.payroll.repository.EmployeeRepository;

@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(EmployeeRepository repository) {

    return args -> {
      log.info("Preloading " + repository.save(new Employee("Ramesh Sharma", "Software Tester")));
      log.info("Preloading " + repository.save(new Employee("Bikky Kumar", "AI Engineer")));
    };
  }
}