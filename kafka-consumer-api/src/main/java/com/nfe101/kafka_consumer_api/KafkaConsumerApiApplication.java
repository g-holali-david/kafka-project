package com.nfe101.kafka_consumer_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;

import java.sql.Connection;
import java.sql.DriverManager;

@SpringBootApplication
public class KafkaConsumerApiApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(KafkaConsumerApiApplication.class);
		ConfigurableEnvironment env = new StandardEnvironment();

		// Retrieve MySQL properties from environment variables or application
		// properties
		String mysqlUrl = env.getProperty("spring.datasource.url", "jdbc:mysql://localhost:3306/taxi_db");
		String mysqlUser = env.getProperty("spring.datasource.username");
		String mysqlPassword = env.getProperty("spring.datasource.password");

		try (Connection conn = DriverManager.getConnection(mysqlUrl, mysqlUser, mysqlPassword)) {
			// MySQL connection successful
			System.out.println("Connected to MySQL. Activating 'mysql' profile.");
			env.setActiveProfiles("mysql");
		} catch (Exception e) {
			// MySQL connection failed, fallback to H2
			System.err.println("MySQL connection failed. Activating 'h2' profile.");
			env.setActiveProfiles("h2");
		}

		app.setEnvironment(env);
		app.run(args);
	}
}
