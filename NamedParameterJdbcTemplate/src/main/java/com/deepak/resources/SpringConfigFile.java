package com.deepak.resources;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class SpringConfigFile {

	@Bean
	public DriverManagerDataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/student_db");
		dataSource.setUsername("root");
		dataSource.setPassword("Root@1234");
		return dataSource;
	}

	@Bean
	public NamedParameterJdbcTemplate npJdbcTemplate() {
		NamedParameterJdbcTemplate npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource());
		return npJdbcTemplate;
	}
}
