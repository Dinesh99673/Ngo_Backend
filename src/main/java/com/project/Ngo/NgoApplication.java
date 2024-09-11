package com.project.Ngo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;

@SpringBootApplication
public class NgoApplication {
	public static void main(String[] args) {
		SpringApplication.run(NgoApplication.class, args);
	}
}
