package com.nanomt88.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
class SpringBootDemoApplicationTests {

	@Test
	void contextLoads() throws SQLException {

		Connection conn = java.sql.DriverManager.getConnection("", "name", "password");
	}

}
