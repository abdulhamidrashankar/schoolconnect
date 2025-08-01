package com.schoolconnect.app.controller;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/database")
public class DBSetupController {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	StartupAdminUserInitializer startupAdminUserInitializer;
	
	public void disableReferentialIntegrity() throws SQLException {
		String dbProduct = jdbcTemplate.getDataSource().getConnection().getMetaData().getDatabaseProductName();

		if ("H2".equals(dbProduct)) {
			jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");
		}
	}

	public void enableReferentialIntegrity() throws SQLException {
		String dbProduct = jdbcTemplate.getDataSource().getConnection().getMetaData().getDatabaseProductName();

		if ("H2".equals(dbProduct)) {
			jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");
		}
	}

	@GetMapping("/prepare")
	public String prepare() {
		try {
			disableReferentialIntegrity();
			// Relative path from project root to parent directory
			Resource schema = new FileSystemResource("../../database/DDL.sql");
			Resource data = new FileSystemResource("../../database/DML.sql");

			ResourceDatabasePopulator schemaPopulator = new ResourceDatabasePopulator(schema);
			schemaPopulator.execute(dataSource);

			ResourceDatabasePopulator dataPopulator = new ResourceDatabasePopulator(data);
			dataPopulator.execute(dataSource);

			enableReferentialIntegrity();

			return "Database setup is ready with dummy data";
		} catch (Exception e) {
			e.printStackTrace();
			return "Failed: " + e.getMessage();
		}
	}

	@GetMapping("/reset")
	public String reset() {
		try {
			disableReferentialIntegrity();
			// Relative path from project root to parent directory
			Resource reset = new FileSystemResource("../../database/RESET.sql");

			ResourceDatabasePopulator schemaPopulator = new ResourceDatabasePopulator(reset);
			schemaPopulator.execute(dataSource);

			enableReferentialIntegrity();
			
			return "Database is rest, please create table and insert data";
		} catch (Exception e) {
			e.printStackTrace();
			return "Failed: " + e.getMessage();
		}
	}
}
